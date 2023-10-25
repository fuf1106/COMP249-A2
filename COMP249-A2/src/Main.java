import java.io.*;
import java.util.Scanner;

public class Main {

    //declare String of file names for files used in multiple parts
    private static final String[] genreCSV = {"Cartoon_Comics.csv","Hobbies_Collectibles.csv","Movies_TV_Books.csv","Music_Radio_Books.csv","Nostalgia_Eclectic_Books.csv", "Old_Time_Radio_Books.csv","Sports_Books_Memorabilia.csv","Trains_Planes_Automobiles.csv","syntax_error_file.txt"};
    public static void main(String[] args) {do_part1();
    }
    public static void do_part1() {
        //declare scanner object outside the try block so that object persists
        //declaring the file name in a String for simplicity's sake

        Scanner in = null;
        String part1F1 = "COMP249-A2/src/part1_input_file_names.txt";
        File file = new File(part1F1);

        //wrap scanner object declaration in a try block to catch FileNotFound exceptions
        try {
            in = new Scanner(file);
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
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
                outputWriter[i] = new PrintWriter(new FileOutputStream("COMP249-A2/src/part1_output_files/" + genreCSV[i]));
            }
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //outer for loop used for processing all the books[year].csv files
        for(int i = 0; i < fileCount; i++) {
            file = new File("COMP249-A2/src/" + files[i]);
            try {
                if(!file.exists()) {
                    throw new FileNotFoundException("File: " + files[i] + " does not exist. \nMoving on to the next file...");
                }
                in = new Scanner(file);
            }
            catch(FileNotFoundException e) {
                System.out.println(e.getMessage());
                continue;
            }
            //while loop for reading the lines of each input file
            while(in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line); //Testing to check if code works
                String[] fields = null;
                //Checks if line starts with " and splits accordingly
                if(line.charAt(0) == '\"') {
                    String s = line.substring(1, line.indexOf("\"", 1)); //the first field
                    String[] s2 = line.substring((line.indexOf("\"", 1))).split(","); //the other fields
                    fields = new String[s2.length];
                    fields[0] = s;

                    //sort each field from s2 into fields
                    for (int j = 1; j < s2.length; j++){
                        fields[j] = s2[j];
                    }

                    //checks if number of fields is greater than 6 and if yes throws exception
                    if (fields.length > 6){
                        try {
                            throw new TooManyFieldsException("Record: " + line);
                        }
                        catch (TooManyFieldsException e){
                            outputWriter[8].println(e.getMessage()); //send to syntax_error_file.txt
                            continue;
                        }
                    }
                    //checks if number of fields is less than 6 and if yes throws exception
                    else if (fields.length < 6){
                        try {
                            throw new TooFewFieldsException("Record: " + line);
                        }
                        catch (TooFewFieldsException e){
                            outputWriter[8].println(e.getMessage());
                            continue;
                        }
                    }
                }

                else {
                    //split the line nornally if does not start with "
                    fields = line.split(",");

                    //checks if number of fields is greater than 6 and if yes throws exception
                    if (fields.length > 6){
                        try {
                            throw new TooManyFieldsException("Record: " + line);
                        }
                        catch (TooManyFieldsException e){
                            outputWriter[8].println(e.getMessage());
                            continue;
                        }
                    }

                    //checks if number of fields is less than 6 and if yes throws exception
                    else if (fields.length < 6){
                        try {
                            throw new TooFewFieldsException("Record: " + line);
                        }
                        catch (TooFewFieldsException e){
                            outputWriter[8].println(e.getMessage());
                            continue;
                        }
                    }

                }
                //Checks the genre of every line and outputs it to the appropriate file
                String genre = fields[4];
                if (genre.equals("CCB")){
                    outputWriter[0].println(line);
                }
                else if (genre.equals("HCB")){
                    outputWriter[1].println(line);
                }
                else if (genre.equals("MTV")){
                    outputWriter[2].println(line);
                }
                else if (genre.equals("MRB")){
                    outputWriter[3].println(line);
                }
                else if (genre.equals("NEB")){
                    outputWriter[4].println(line);
                }
                else if (genre.equals("OTR")){
                    outputWriter[5].println(line);
                }
                else if (genre.equals("SSM")){
                    outputWriter[6].println(line);
                }
                else if (genre.equals("TPA")){
                    outputWriter[7].println(line);
                }
                //incorrect genre? where send? part 2 maybe
            }
        }
        //close outputwriter when done using it (this might need to be moved up at some point)
        for (int i = 0; i < genreCSV.length; i++){
            outputWriter[i].close();
        }
        //TODO: Part 1
        //Need to create a new file and output every CSV file to it
        //Create variables that hold the amt of books in each CSV file
        //Finish up sytax error file output
    }
}