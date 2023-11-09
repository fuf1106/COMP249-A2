/**
 * Names(s) and ID's: Diana Edvi (40198139) AND Fuad Awad (40195634)
 * COMP249
 * Assignment # 2
 * Due Date: November 11, 2023
 */

// -----------------------------------------------------
// Assignment 2
// Question: (Parts 1, 2, and 3)
// Written by: Diana Edvi (40198139) AND Fuad Awad (40195634)
// This Java program is divided into three parts:
    // Part 1 reads input files and categorizes book records into separate CSV files, checking for syntax errors.
    // Part 2 validates book records for semantic errors and serializes valid records into binary files while creating a semantic error log.
    // Part 3 offers a user interface for browsing the serialized book records, allowing users to select and view records from different genres.
// -----------------------------------------------------

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * The Main class represents a Java program for processing and managing book records.
 * It consists of three parts: part 1, part 2, and part 3, each with its own set of functions.
 */
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
    /**
     * This method reads a list of input file names from a file, processes each file,
     * and categorizes book records into separate CSV files based on their genre.
     * It checks for syntax errors in the records (number of fields, missing fields, and invalid genres)
     * It also creates a syntax error file which logs any encountered errors.
     */
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

        //get the file name from each line
        for (int i = 0; i < fileCount; i++) {
            files[i] = in.nextLine();
        }
        //close the Scanner
        in.close();

        //open the list of output files
        PrintWriter[] outputWriter = new PrintWriter[genreCSV.length - 1];
        try {
            for (int i = 0; i < genreCSV.length - 1; i++) {
                outputWriter[i] = new PrintWriter(new FileOutputStream("COMP249-A2/src/part1_output_files/" + genreCSV[i]));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
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

                    //sort each field into fields array
                    for (int j = 0; j < temp.length; j++) {
                        fields[j + 1] = temp[j];
                    }
                    //splits string into fields array
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

                //initialize fieldNames array abd assign values to each index
                String[] fieldNames = new String[6];
                fieldNames[0] = "title";
                fieldNames[1] = "authors";
                fieldNames[2] = "price";
                fieldNames[3] = "isbn";
                fieldNames[4] = "genre";
                fieldNames[5] = "year";

                //close and open the outputstream again because there were too many lines at once
                outputWriter[8].close();

                try {
                    outputWriter[8] = new PrintWriter(new FileOutputStream("COMP249-A2/src/part1_output_files/" + genreCSV[8], true));
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }

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
                }

                //Checks the genre of every line and outputs it to the appropriate file, then increments the bookCount for the appropriate CSV file by 1
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

                //close and open the outputstream again because there were too many lines at once
                outputWriter[8].close();

                try {
                    outputWriter[8] = new PrintWriter(new FileOutputStream("COMP249-A2/src/part1_output_files/" + genreCSV[8], true));
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }

            }
        }
        //close outputWriter for each file when done using it
        for (int i = 0; i < genreSER.length; i++) {
            outputWriter[i].close();
        }
    }

    /**
     * This method reads and processes CSV files containing book records.
     * It checks for semantic errors in the records (price, ISBN-10, ISBN-13, and year)
     * It then serializes the validated records into binary files.
     * It also creates a semantic error file which logs any encountered errors.
     */
    public static void do_part2() {
        //Array of genre file names
        String[] genre_file_names = {"Cartoon_Comics.csv", "Hobbies_Collectibles.csv", "Movies_TV_Books.csv", "Music_Radio_Books.csv", "Nostalgia_Eclectic_Books.csv", "Old_Time_Radio_Books.csv", "Sports_Books_Memorabilia.csv", "Trains_Planes_Automobiles.csv"};

        // Input and output file paths for reading and writing book records
        String inputFilepath = "COMP249-A2/src/part1_output_files/";
        String outputFilepath = "COMP249-A2/src/part2_output_files/";

        // Array of PrintWriter objects for writing output files
        PrintWriter[] outputWriter = new PrintWriter[genreCSV.length];
        PrintWriter semErrorWriter = null;

        // Arrays for handling input files
        File[] input_files = new File[genre_file_names.length];
        FileInputStream[] input_streams = new FileInputStream[genre_file_names.length];
        BufferedReader[] bufferedReaders = new BufferedReader[genre_file_names.length];

        // Arrays for handling output files
        ObjectOutputStream[] output = new ObjectOutputStream[genre_file_names.length];

        try {
            // Initialize a PrintWriter for writing semantic error logs
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

                // Open and prepare input files for reading
                input_files[i] = new File(inputFilepath + genre_file_names[i]);
                input_streams[i] = new FileInputStream(input_files[i]);
                bufferedReaders[i] = new BufferedReader(new FileReader(inputFilepath + genre_file_names[i]));

                Book[] books = new Book[bookCount[i]];
                String[] fields;

                String line = "";

                // Read and process each line in the input file
                while ((line = bufferedReaders[i].readLine()) != null) {
                    String[] temp = null;

                    // Check if the line starts with a double quote (") and split accordingly
                    if (line.charAt(0) == '\"') {
                        String title_field = "\"" + line.substring(1, line.indexOf("\"", 1)) + "\""; //the first field
                        String other_fields = line.substring(title_field.length() + 1);
                        temp = other_fields.split(",", -1);
                        fields = new String[6];
                        fields[0] = title_field;

                        //sort each field into fields array
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
                    // Create Book objects and store them in the array
                    books[finalBookCount[i]++] = new Book(fields[0], fields[1], Double.parseDouble(fields[2]), fields[3], fields[4], Integer.parseInt(fields[5]));
                }
                // Create an array of final Book objects and write it to an output file
                Book[] finalBooks = new Book[finalBookCount[i]];
                for (int j = 0; j < finalBookCount[i]; j++) {
                    finalBooks[j] = books[j];
                }
                output[i] = new ObjectOutputStream(new FileOutputStream(outputFilepath + genreSER[i]));
                output[i].writeObject(finalBooks);
                output[i].close();
            }
            // Close the semantic error log
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

    /**
     * This method deserializes book records from files and
     *provides a user interface to view and select files.
     */
    public static void do_part3() {
        Scanner in = new Scanner(System.in);
        ObjectInputStream[] input = new ObjectInputStream[genreSER.length];
        Book[][] bookArrays = new Book[genreSER.length][];

        for (int i = 0; i < genreSER.length; i++) { //deserialization process
            try {
                // Open and deserialize each genre's data file
                input[i] = new ObjectInputStream(new FileInputStream( "COMP249-A2/src/part2_output_files/" + genreSER[i]));
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
            System.out.println("V: View the selected file: " + genreSER[currentIndex] + " (" + bookArrays[currentIndex].length + " records)");
            System.out.println("S: Select a file to view");
            System.out.println("X: Exit");
            System.out.print("Enter your choice -> ");
            String choice = in.next();
            if (choice.equalsIgnoreCase("v")) {
                // View books from the selected file
                viewBooks(in, bookArrays[currentIndex]);
            }
            else if (choice.equalsIgnoreCase("s")) {
                int temp = currentIndex;
                // Select a different file to view
                currentIndex = selectFile(in, genreSER, bookArrays);
                if (currentIndex == 9){
                    currentIndex = temp;
                }
            }
            else if (choice.equalsIgnoreCase("x")) {
                // Exit the program
                break;
            }
        }
        System.out.println("Thank you for participating in this program");
        System.exit(0);
    }

    /**
     * Displays a list of books and prompts the user to navigate through the book records.
     * Entering a positive integer n displays the current object as well as the following (n-1) objects
     * Entering a negative integer n displays the current object as well as the previous (n-1) objects
     *
     * @param in     A Scanner object for reading user input.
     * @param books  An array of Book objects representing the collection of books.
     */
    public static void viewBooks(Scanner in, Book[] books) { //for option v
        System.out.println("Enter your choice: ");
        int n = checkInt(in);
        int index = 0;

        // Loop for displaying books based on user's input
            while (index > 0 || index < books.length){
                if (n > 0){
                    for(int i = 0; i < n; i++){
                        if (index + i >= books.length){
                            System.out.println("EOF has been reached");
                            index = books.length - 1;
                            break;
                        }
                        System.out.println(books[index + i]); // Display a book
                    }
                    if (index < books.length - 1){
                        index += (n - 1); // Move the index forward
                    }
                    System.out.println();
                    System.out.println("Enter your choice: ");
                    n = checkInt(in); // Read another integer
                }
                else if (n < 0 ){
                    index = index - (Math.abs(n) - 1); // Move the index backward
                    for(int i = 0; i < Math.abs(n); i++){
                        if (index < 0){
                            System.out.println("BOF has been reached");
                            index = 0;
                            break;
                        }
                        System.out.println(books[index + i]); // Display a book
                    }
                    if (index < 0){
                        index = 0;
                    }
                    System.out.println("Enter your choice: ");
                    n = checkInt(in); // Read another integer
                }
                else {
                    break; // Exit the loop if n is 0
                }
            }
    }

    /**
     * Displays a list of files and prompts the user to select a file.
     *
     * @param number      A Scanner object for reading user input.
     * @param fileNames   An array of file names, each representing a collection of books based off of different genres.
     * @param bookArrays  A two-dimensional array containing book records, organized by file.
     * @return An integer representing the user's choice.
     */
    public static int selectFile(Scanner number, String[] fileNames, Book[][] bookArrays) { //for option s
        //display files
        for(int i = 0; i < genreSER.length; i++){
            System.out.println(" " + (i + 1) + " " + fileNames[i] + " (" + bookArrays[i].length + " records)");
        }
        System.out.println(" 9 Exit");
        System.out.print("Enter your choice: ");
        return checkInt(number); // Read and return the user's choice as an integer
    }
    /**
     * Reads an integer input from the provided Scanner object, handling potential input errors.
     *
     * This method prompts the user to enter an integer, and it repeatedly retries until a valid integer input
     * is provided.
     *
     * @param scanner The Scanner object used for reading user input.
     * @return The valid integer input provided by the user.
     */
    public static int checkInt(Scanner scanner){
        int number = 0;
        //keep looping until integer is input
        while (true){
            try {
                number = scanner.nextInt();
                return number;
            }
            catch (InputMismatchException e){
                System.out.println("Please input an integer");
                scanner.next(); // Clear invalid input from the scanner
            }
        }
    }
}