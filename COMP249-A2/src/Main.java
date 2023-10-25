import java.io.*;
import java.util.Scanner;

public class Main {

    //declare String of file names for files used in multiple parts
    static String[] genreCSV = {"Cartoon_Comics.csv","Hobbies_Collectibles.csv","Movies_TV_Books.csv","Music_Radio_Books.csv","Nostalgia_Eclectic_Books.csv", "Old_Time_Radio_Books.csv","Sports_Books_Memorabilia.csv","Trains_Planes_Automobiles.csv","syntax_error_file.txt"};

    public static void main(String[] args) {
        do_part1();
    }
    public static void do_part1() {
        //declare scanner object outside the try block so that object persists
        //declaring the file name in a String for simplicity's sake

        Scanner in = null;
        String part1F1 = "src/part1_input_file_names.txt";
        File file = new File(part1F1);

        //wrap scanner object declaration in a try block to catch FileNotFound exceptions
        try {
            in = new Scanner(file);
        }
        catch(FileNotFoundException e) {
            System.out.println("Problem opening file.");
            System.exit(0);
        }

        //read the first int to know the number of files
        int fileCount = in.nextInt();
        in.nextLine(); //move the Scanner to the next line of the file otherwise the first entry will be blank
        String[] files = new String[fileCount]; //declare the array size to match the file count size

        //go through every line in the file and get the file name String from each line and print the file name to make sure it's correct
        for(int i = 0; i < fileCount; i++) {
            files[i] = in.nextLine();
            System.out.println(files[i]);
        }

        in.close(); //close the Scanner as soon as we are done

        //open the list of output files
        PrintWriter[] outputWriter = new PrintWriter[genreCSV.length];
        try {
            for (int i = 0; i < genreCSV.length; i++) {
                outputWriter[i] = new PrintWriter(new FileOutputStream(genreCSV[i]));
            }
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //outer for loop used for processing all the books[year].csv files
        for(int i = 0; i < fileCount; i++) {
            file = new File("src/" + files[i]);
            try {
                if(!file.exists()) {
                    throw new IOException();
                }
                in = new Scanner(file);
            }
            catch(IOException e) {
                System.out.println("File: " + files[i] + " does not exist. \nMoving on to the next file...");
                continue;
            }
            //while loop for reading the lines of each input file
            while(in.hasNextLine()) {
                String line = in.nextLine();
                String[] fields = line.split(",");

                //code for fixing the array of field when there are quotes in the string
                if(line.charAt(0) == '"') {
                    for(int j = 0; j < fields.length; j++) {
                        //condition that identifies the index of the array where the closing quotes are found (end of the quoted section)
                        if((fields[j].charAt(fields[j].length() - 1) == '"')) {
                            //update the array and reassebmle the quoted section: copy the original array into a temp variable
                            String[] temp = fields;

                            //update the size of the array of fields to the length it should be if the commas in the quoted section did not split it further
                            fields = new String[temp.length - j];

                            //initialize the first string
                            fields[0] = "";

                            //looping through each string in the original array
                            for(int k = 0; k < temp.length; k++) {
                                //identifying the original strings that are part of the quoted section (the ones that belong together)
                                if(k <= j) {
                                    //add the string that belongs together (quoted section) to the first index of the new fields array
                                    fields[0] += temp[k];
                                }
                                else {
                                    //place the rest of the strings where they should be in the new array
                                    fields[k - j] = temp[k];
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}