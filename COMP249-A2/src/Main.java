import java.io.*;
import java.util.Scanner;

public class Main {
    //declare String of file names for files used in multiple parts
    private static final String[] genreCSV = {"Cartoon_Comics.csv", "Hobbies_Collectibles.csv", "Movies_TV_Books.csv", "Music_Radio_Books.csv", "Nostalgia_Eclectic_Books.csv", "Old_Time_Radio_Books.csv", "Sports_Books_Memorabilia.csv", "Trains_Planes_Automobiles.csv", "syntax_error_file.txt", "semantic_error_file.txt"};
    private static final String[] genreSER = {"Cartoon_Comics.csv.ser", "Hobbies_Collectibles.csv.ser", "Movies_TV_Books.csv.ser", "Music_Radio_Books.csv.ser", "Nostalgia_Eclectic_Books.csv.ser", "Old_Time_Radio_Books.csv.ser", "Sports_Books_Memorabilia.csv.ser", "Trains_Planes_Automobiles.csv.ser"};
    private static int[] bookCount = new int[genreSER.length];
    private static int[] finalBookCount = new int[genreSER.length];

    public static void main(String[] args) {
        do_part1();
        do_part2();
        do_part3();
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
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Problem opening file.");
            System.exit(0);
        }

        //read the first int to know the number of files
        int fileCount = in.nextInt();
        in.nextLine(); //move the Scanner to the next line of the file otherwise the first entry will be blank
        String[] files = new String[fileCount]; //declare the array size to match the file count size

        //go through every line in the file and get the file name String from each line and print the file name to make sure it's correct
        for (int i = 0; i < fileCount; i++) {
            files[i] = in.nextLine();
        }

        in.close(); //close the Scanner as soon as we are done

        //open the list of output files
        PrintWriter[] outputWriter = new PrintWriter[genreCSV.length - 1];
        try {
            for (int i = 0; i < genreCSV.length - 1; i++) {
                outputWriter[i] = new PrintWriter(new FileOutputStream("COMP249-A2/src/part1_output_files/" + genreCSV[i]));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //outer for loop used for processing all the books[year].csv files
        for (int i = 0; i < fileCount; i++) {
            file = new File("COMP249-A2/src/" + files[i]);
            try {
                if (!file.exists()) {
                    throw new FileNotFoundException("File: " + files[i] + " does not exist. \nMoving on to the next file...");
                }
                in = new Scanner(file);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                continue;
            }
            //while loop for reading the lines of each input file
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] fields = null;
                String[] temp = null;
                //Checks if line starts with " and splits accordingly
                if (line.charAt(0) == '\"') {
                    String title_field = "\"" + line.substring(1, line.indexOf("\"", 1)) + "\""; //the first field
                    String other_fields = line.substring(title_field.length() + 1);
                    temp = other_fields.split(",", -1);
                    fields = new String[6];
                    fields[0] = title_field;

                    //checks if number of fields is greater than 6 and if yes throws exception
                    if (temp.length > 5) {
                        try {
                            throw new TooManyFieldsException("Syntax error in file: " + files[i] + "\n====================\n" + line + "\nToo many fields\n");
                        } catch (TooManyFieldsException e) {
                            outputWriter[8].println(e.getMessage()); //send to syntax_error_file.txt
                            continue;
                        }
                    }
                    //checks if number of fields is less than 6 and if yes throws exception
                    else if (temp.length < 5) {
                        try {
                            throw new TooFewFieldsException("Syntax error in file: " + files[i] + "\n====================\n" + line + "\nToo few fields\n");
                        } catch (TooFewFieldsException e) {
                            outputWriter[8].println(e.getMessage());
                            continue;
                        }
                    }

                    //sort each field into fields
                    for (int j = 0; j < temp.length; j++) {
                        fields[j + 1] = temp[j];
                    }
                } else {
                    fields = line.split(",", -1);

                    //checks if number of fields is greater than 6 and if yes throws exception
                    if (fields.length > 6) {
                        try {
                            throw new TooManyFieldsException("Syntax error in file: " + files[i] + "\n====================\n" + line + "\nToo many fields\n");
                        } catch (TooManyFieldsException e) {
                            outputWriter[8].println(e.getMessage()); //send to syntax_error_file.txt
                            continue;
                        }
                    }
                    //checks if number of fields is less than 6 and if yes throws exception
                    else if (fields.length < 6) {
                        try {
                            throw new TooFewFieldsException("Syntax error in file: " + files[i] + "\n====================\n" + line + "\nToo few fields\n");
                        } catch (TooFewFieldsException e) {
                            outputWriter[8].println(e.getMessage());
                            continue;
                        }
                    }
                }

                String[] fieldNames = new String[6];
                fieldNames[0] = "title";
                fieldNames[1] = "authors";
                fieldNames[2] = "price";
                fieldNames[3] = "isbn";
                fieldNames[4] = "genre";
                fieldNames[5] = "year";

                //checks for missing fields
                try {
                    for (int j = 0; j < fields.length; j++) {
                        if (fields[j].equals("")) {
                            throw new MissingFieldException("syntax error in file: " + files[i] + "\n====================\nError: missing " + fieldNames[j] + "\nRecord: " + line + "\n");
                        }
                    }
                } catch (MissingFieldException e) {
                    outputWriter[8].println(e.getMessage()); //send to syntax_error_file.txt
                    continue;
                    //System.out.println(e.getMessage());
                }

                //Checks the genre of every line and outputs it to the appropriate file, then increments the bookCount for the appropriate CSV file by 1
                //Checks for valid genre
                String genre = fields[4];
                if (genre.equals("CCB")) {
                    outputWriter[0].println(line);
                    bookCount[0]++;
                } else if (genre.equals("HCB")) {
                    outputWriter[1].println(line);
                    bookCount[1]++;
                } else if (genre.equals("MTV")) {
                    outputWriter[2].println(line);
                    bookCount[2]++;
                } else if (genre.equals("MRB")) {
                    outputWriter[3].println(line);
                    bookCount[3]++;
                } else if (genre.equals("NEB")) {
                    outputWriter[4].println(line);
                    bookCount[4]++;
                } else if (genre.equals("OTR")) {
                    outputWriter[5].println(line);
                    bookCount[5]++;
                } else if (genre.equals("SSM")) {
                    outputWriter[6].println(line);
                    bookCount[6]++;
                } else if (genre.equals("TPA")) {
                    outputWriter[7].println(line);
                    bookCount[7]++;
                } else if (genre.equals("")) {
                } else {
                    outputWriter[8].println("Error: invalid genre \n====================\n" + line + "\n");
                }

                //had to close and open the outputstream again because reasons
                outputWriter[8].close();

                try {
                    outputWriter[8] = new PrintWriter(new FileOutputStream("COMP249-A2/src/part1_output_files/" + genreCSV[8], true));
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        //close outputWriter for each file when done using it (this might need to be moved up at some point)
        for (int i = 0; i < genreSER.length; i++) {
            outputWriter[i].close();
        }
    }

    //note to self, you should count every line in each file and make sure each one is accounted for

    public static void do_part2() {
        String[] genre_file_names = {"Cartoon_Comics.csv", "Hobbies_Collectibles.csv", "Movies_TV_Books.csv", "Music_Radio_Books.csv", "Nostalgia_Eclectic_Books.csv", "Old_Time_Radio_Books.csv", "Sports_Books_Memorabilia.csv", "Trains_Planes_Automobiles.csv"};

        String inputFilepath = "COMP249-A2/src/part1_output_files/";
        String outputFilepath = "COMP249-A2/src/part2_output_files/";

        PrintWriter[] outputWriter = new PrintWriter[genreCSV.length];
        PrintWriter semErrorWriter = null;

        File[] input_files = new File[genre_file_names.length];
        FileInputStream[] input_streams = new FileInputStream[genre_file_names.length];
        BufferedReader[] bufferedReaders = new BufferedReader[genre_file_names.length];

        ObjectOutputStream[] output = new ObjectOutputStream[genre_file_names.length];

        try {
            semErrorWriter = new PrintWriter(new FileOutputStream("COMP249-A2/src/part2_output_files/semantic_error_file.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        //wrap scanner object declaration in a try block to catch FileNotFound exceptions
        try {
            //For every file
            for (int i = 0; i < genre_file_names.length; i++) {
                finalBookCount[i] = 0;

                input_files[i] = new File(inputFilepath + genre_file_names[i]);
                input_streams[i] = new FileInputStream(input_files[i]);
                bufferedReaders[i] = new BufferedReader(new FileReader(inputFilepath + genre_file_names[i]));

                Book[] books = new Book[bookCount[i]];
                String[] fields;

                String line = "";

                // For every line
                while ((line = bufferedReaders[i].readLine()) != null) {
                    String[] temp = null;

                    //Checks if line starts with " and splits accordingly
                    if (line.charAt(0) == '\"') {
                        String title_field = "\"" + line.substring(1, line.indexOf("\"", 1)) + "\""; //the first field
                        String other_fields = line.substring(title_field.length() + 1);
                        temp = other_fields.split(",", -1);
                        fields = new String[6];
                        fields[0] = title_field;

                        //sort each field into fields
                        for (int j = 0; j < temp.length; j++) {
                            fields[j + 1] = temp[j];
                        }
                    } else {
                        fields = line.split(",", -1);
                    }

                    //check for semantic errors, starting with checking for price error
                    try {
                        if (fields.length > 2) {
                            double price = Double.parseDouble(fields[2]);
                            if (price < 0) {
                                throw new BadPriceException("semantic error in file: " + genre_file_names[i] + "\n====================\nError: bad year\nRecord: " + line + "\n");
                            }
                        }
                        //check for isbn errors
                        int sum = 0;
                        if (fields[3].length() == 10) {
                            for (int j = 0; j < 10; j++) {
                                sum += ((int) fields[3].charAt(j)) * (10 - j);
                            }
                            if (sum % 11 != 0) {
                                throw new BadIsbn10Exception("semantic error in file: " + genre_file_names[i] + "\n====================\nError: bad ISBN-10\nRecord: " + line + "\n");
                            }
                        } else if (fields[3].length() == 13) {
                            for (int j = 0; j < 13; j++) {
                                if (j % 2 != 0) {
                                    sum += (3 * ((int) fields[3].charAt(j)));
                                } else {
                                    sum += ((int) fields[3].charAt(j));
                                }
                            }
                            if (sum % 10 != 0) {
                                throw new BadIsbn13Exception("semantic error in file: " + genre_file_names[i] + "\n====================\nError: bad ISBN-13\nRecord: " + line + "\n");
                            }
                        }
                        //check for year error
                        if (fields.length > 5) {
                            int year = Integer.parseInt(fields[5]);
                            if (year < 1995 || year > 2010) {
                                throw new BadYearException("semantic error in file: " + genre_file_names[i] + "\n====================\nError: bad year\nRecord: " + line + "\n");
                            }
                        }
                    } catch (BadPriceException e) {
                        semErrorWriter.println(e.getMessage()); //send error to the semantic error file
                        continue;
                    } catch (BadIsbn10Exception e) {
                        semErrorWriter.println(e.getMessage());
                        continue;
                    } catch (BadIsbn13Exception e) {
                        semErrorWriter.println(e.getMessage());
                        continue;
                    } catch (BadYearException e) {
                        semErrorWriter.println(e.getMessage());
                        continue;
                    }
                    books[finalBookCount[i]++] = new Book(fields[0], fields[1], Double.parseDouble(fields[2]), fields[3], fields[4], Integer.parseInt(fields[5]));
                }
                Book[] finalBook = new Book[finalBookCount[i]];
                for (int j = 0; j < finalBookCount[i]; j++) {
                    finalBook[j] = books[j];
                }
                output[i] = new ObjectOutputStream(new FileOutputStream(outputFilepath + genreSER[i]));
                output[i].writeObject(finalBook);
                output[i].close();
            }
            semErrorWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Problem opening file.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public static void do_part3() {
        Scanner in = new Scanner(System.in);
        ObjectInputStream[] input = new ObjectInputStream[genreSER.length];
        Book[][] bookArrays = new Book[genreSER.length][];

        for (int i = 0; i < genreSER.length; i++) { //deserialization process
            try {
                input[i] = new ObjectInputStream(new FileInputStream("COMP249-A2/part2_output_files/" + genreSER[i]));
                bookArrays[i] = (Book[]) input[i].readObject();
                input[i].close();
            }
            catch (FileNotFoundException e) {
                System.out.println("Problem deserializing file " + genreSER[i] + ": " + e.getMessage());
            }
            catch (ClassNotFoundException e) {
                System.out.println("Problem deserializing file " + genreSER[i] + ": " + e.getMessage());
            }
            catch (IOException e) {
                System.out.println("Problem deserializing file " + genreSER[i] + ": " + e.getMessage());
            }
        }

        int ArrIndex = 0;
        int currentIndex = 0;

        while (true) { //main menu initialization
            System.out.println("-------------------------------------");
            System.out.println("Main Menu");
            System.out.println("-------------------------------------");
            System.out.println("V: View the selected file: " + genreSER[currentIndex]);
            System.out.println("S: Select a file to view");
            System.out.println("X: Exit");
            System.out.print("Enter your choice -> ");
            String choice = in.next();
            if (choice.equalsIgnoreCase("v")) {
                viewBooks(in, bookArrays[currentIndex], currentIndex);
            }
            else if (choice.equalsIgnoreCase("s")) {
                currentIndex = selectFile(in);
                currentIndex = 0;
            }
            else if (choice.equalsIgnoreCase("x")) {
                break;
            }
        }

    }

    public static void viewBooks(Scanner in, Book[] books, int currentIndex) { //for option v

    }

    public static void displayBook(Book book) { //submenu for option s

    }

    public static int selectFile(Scanner in) { //for option s
        return 0;
    }


}