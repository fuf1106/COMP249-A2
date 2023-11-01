import java.io.*;
import java.util.Scanner;

public class Main {
    //declare String of file names for files used in multiple parts
    private static final String[] genreCSV = {"Cartoon_Comics.csv","Hobbies_Collectibles.csv","Movies_TV_Books.csv","Music_Radio_Books.csv","Nostalgia_Eclectic_Books.csv", "Old_Time_Radio_Books.csv","Sports_Books_Memorabilia.csv","Trains_Planes_Automobiles.csv","syntax_error_file.txt", "semantic_error_file.txt"};
    private static final String[] genreSER = {"Cartoon_Comics.csv.ser","Hobbies_Collectibles.csv.ser","Movies_TV_Books.csv.ser","Music_Radio_Books.csv.ser","Nostalgia_Eclectic_Books.csv.ser", "Old_Time_Radio_Books.csv.ser","Sports_Books_Memorabilia.csv.ser","Trains_Planes_Automobiles.csv.ser"};
    private static int[] bookCount = new int[genreSER.length];
    public static void main(String[] args) {
        do_part1();
        do_part2();
        Book test = new Book("g","g",10,"g", "h", 9);
        System.out.println(test.toString());
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
        PrintWriter[] outputWriter = new PrintWriter[genreCSV.length - 1];
        try {
            for (int i = 0; i < genreCSV.length - 1; i++) {
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
                String[] temp = null;
                //Checks if line starts with " and splits accordingly
                if (line.charAt(0) == '\"') {
                    String title_field = "\"" + line.substring(1, line.indexOf("\"", 1)) + "\""; //the first field
                    String other_fields = line.substring(title_field.length() + 1 );
                    System.out.println(other_fields);
                    temp = other_fields.split(",", -1);
                    System.out.println(temp.length);
                    fields = new String[6];
                    fields[0] = title_field;

                    //checks if number of fields is greater than 6 and if yes throws exception
                    if (temp.length > 5) {
                        try {
                            throw new TooManyFieldsException("Syntax error in file: " + files[i] + "\n====================\n" + line + "\nToo many fields\n");
                        }
                        catch (TooManyFieldsException e){
                            outputWriter[8].println(e.getMessage()); //send to syntax_error_file.txt
                            continue;
                        }
                    }
                    //checks if number of fields is less than 6 and if yes throws exception
                    else if (temp.length < 5){
                        try {
                            throw new TooFewFieldsException("Syntax error in file: " + files[i] + "\n====================\n" + line + "\nToo few fields\n");
                        }
                        catch (TooFewFieldsException e){
                            outputWriter[8].println(e.getMessage());
                            continue;
                        }
                    }

                    //sory each field into fields
                    for (int j = 0; j < temp.length; j++){
                        fields[j + 1] = temp[j];
                    }

                    for (int j = 0; j < fields.length; j++){
                        System.out.println(fields[j]);
                    }
                    System.out.println("done");
                }

                else {
                    fields = line.split(",", -1);

                    //checks if number of fields is greater than 6 and if yes throws exception
                    if (fields.length > 6) {
                        try {
                            throw new TooManyFieldsException("Syntax error in file: " + files[i] + "\n====================\n" + line + "\nToo many fields\n");
                        }
                        catch (TooManyFieldsException e){
                            outputWriter[8].println(e.getMessage()); //send to syntax_error_file.txt
                            continue;
                        }
                    }
                    //checks if number of fields is less than 6 and if yes throws exception
                    else if (fields.length < 6){
                        try {
                            throw new TooFewFieldsException("Syntax error in file: " + files[i] + "\n====================\n" + line + "\nToo few fields\n");
                        }
                        catch (TooFewFieldsException e){
                            outputWriter[8].println(e.getMessage());
                            continue;
                        }
                    }
                }

                //Checks the genre of every line and outputs it to the appropriate file, then increments the bookCount for the appropriate CSV file by 1
                //Checks for valid genre
                String genre = fields[4];
                if (genre.equals("CCB")){
                    outputWriter[0].println(line);
                    bookCount[0]++;
                }
                else if (genre.equals("HCB")){
                    outputWriter[1].println(line);
                    bookCount[1]++;
                }
                else if (genre.equals("MTV")){
                    outputWriter[2].println(line);
                    bookCount[2]++;
                }
                else if (genre.equals("MRB")){
                    outputWriter[3].println(line);
                    bookCount[3]++;
                }
                else if (genre.equals("NEB")){
                    outputWriter[4].println(line);
                    bookCount[4]++;
                }
                else if (genre.equals("OTR")){
                    outputWriter[5].println(line);
                    bookCount[5]++;
                }
                else if (genre.equals("SSM")){
                    outputWriter[6].println(line);
                    bookCount[6]++;
                }
                else if (genre.equals("TPA")){
                    outputWriter[7].println(line);
                    bookCount[7]++;
                }
                else if (genre.equals("")){
                }
                else {
                    outputWriter[8].println("Error: invalid genre \n====================\n" + line + "\n");
                }
                String[] fieldNames = new String[6];
                fieldNames[0] = "title";
                fieldNames[1] = "authors";
                fieldNames[2] = "price";
                fieldNames[3] = "isbn";
                fieldNames[4] = "genre";
                fieldNames[5] = "year";
                System.out.println(fields.length);
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH" + fields[5]);


                //had to close and open the outputstream again because reasons
                outputWriter[8].close();

                try {
                    outputWriter[8] = new PrintWriter(new FileOutputStream("COMP249-A2/src/part1_output_files/" + genreCSV[8], true));
                }
                catch(FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }

                //checks for missing fields
                for (int j = 0; j < fields.length; j++) {
                    try{
                        if (fields[j].equals("")) {
                            throw new MissingFieldException("syntax error in file: " + files[i] + "\n====================\nError: missing " + fieldNames[j] + "\nRecord: " + line + "\n");
                        }
                    }
                    catch (MissingFieldException e){
                        outputWriter[8].println(e.getMessage()); //send to syntax_error_file.txt
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        System.out.println("\n====================\n");
        //display the number of books in each CSV file then close outputWriter when done using it (this might need to be moved up at some point)
        for (int i = 0; i < genreSER.length; i++){
            System.out.println("There are " + bookCount[i] + " books in the file " + genreCSV[i]);
            outputWriter[i].close();
        }

        //TODO: Part 1
        //Finish up syntax error file output. Ask teacher which should be prioritized first with regards to error output
    }

    public static void do_part2(){

        //this is not writing errors to the semantic error file and i've given up trying to fix it, just does not work

        Scanner in = null;
        String part2FilePath = "COMP249-A2/src/part2_output_files/";
        File files = new File(part2FilePath);

        //wrap scanner object declaration in a try block to catch FileNotFound exceptions
        try {
            in = new Scanner(files);
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Problem opening file.");
            System.exit(0);
        }

        //read the first int to know the number of files
        int fileCount = in.nextInt();
        in.nextLine(); //move the Scanner to the next line of the file otherwise the first entry will be blank
        String[] file2 = new String[fileCount]; //declare the array size to match the file count size

        //go through every line in the file and get the file name String from each line and print the file name to make sure it's correct
        for(int i = 0; i < fileCount; i++) {
            file2[i] = in.nextLine();
            System.out.println(file2[i]);
        }

        in.close(); //close the Scanner as soon as we are done

        //object declaration for inputting/outputting 2 files in part 2
        ObjectOutputStream binaryOut;
        BufferedReader textIn;
        String file;

        PrintWriter[] semErrorWriter = new PrintWriter[genreCSV.length];
        try {
            semErrorWriter = new PrintWriter[]{new PrintWriter(new FileOutputStream("COMP249-A2/src/part2_output_files/" + genreCSV[genreCSV.length - 1]))};
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        for(int i = 0; i < genreSER.length; i++) {
            //try to open the input and output files
            try {
                file ="COMP249-A2/src/part2_output_files/" + genreCSV[i];
                textIn = new BufferedReader(new FileReader(file));
                binaryOut = new ObjectOutputStream(new FileOutputStream(genreSER[i]));
            }
            catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                continue;
            }
            String text;
            Book[] books = new Book[bookCount[i]];
            int finalBookCount = 0;
            try {
                while ((text = textIn.readLine()) != null) {
                    String line = textIn.readLine();
                    String[] fields = null;
                    //Checks if line starts with " and splits accordingly
                    if(text.charAt(0) == '\"') {
                        String s = text.substring(1, text.indexOf("\"", 1)); //the first field
                        String[] s2 = text.substring((text.indexOf("\"", 1))).split(","); //the other fields
                        fields = new String[s2.length];
                        fields[0] = s;

                        //sort each field from s2 into fields
                        for (int j = 1; j < s2.length; j++) {
                            fields[j] = s2[j - 1];
                        }
                    }
                    else {
                        //split the line normally if it does not start with "
                        fields = text.split(",");
                    }

                    //check for semantic errors:
                    //check for price error

                    try {
                        if (fields.length > 2 && isNumeric(fields[2])) {
                            double price = Double.parseDouble(fields[2]);
                            if (price < 0) {
                                throw new BadPriceException("semantic error in file: " + file2[i] + "\n====================\nError: bad year\nRecord: " + line + "\n");
                            }
                        }

                        int sum = 0;
                        //check for isbn error
                        if (fields[3].length() == 10) {
                            for (int j = 0; j < 10; j++) {
                                sum += ((int) fields[3].charAt(j)) * (10 - j);
                            }
                            if (sum % 11 != 0) {
                                throw new BadIsbn10Exception("semantic error in file: " + file2[i] + "\n====================\nError: bad ISBN-10\nRecord: " + line + "\n");
                            }
                        }
                        else if (fields[3].length() == 13){
                            for (int j = 0; j < 13; j++) {
                                if (j % 2 != 0) {
                                    sum += (3 * ((int) fields[3].charAt(j)));
                                }
                                else {
                                    sum += ((int) fields[3].charAt(j));
                                }
                            }
                            if (sum % 10 != 0) {
                                throw new BadIsbn13Exception("semantic error in file: " + file2[i] + "\n====================\nError: bad ISBN-13\nRecord: " + line + "\n");
                            }
                        }
                        //check for year error
                        if (fields.length > 5 && isNumeric(fields[5])) {
                            int year = Integer.parseInt(fields[5]);
                            if (year < 1995 || year > 2010) {
                                throw new BadYearException("semantic error in file: " + file2[i] + "\n====================\nError: bad year\nRecord: " + line + "\n");
                            }
                        }
                    }

                    catch (BadPriceException | BadIsbn10Exception | BadIsbn13Exception | BadYearException e) {
                        semErrorWriter[9].println(e.getMessage());
                    }

                    //add valid book to book array
                    if (fields.length > 2 && isNumeric(fields[2])) {
                        books[finalBookCount] = new Book(fields[0], fields[1], Double.parseDouble(fields[2]), fields[3], fields[4], Integer.parseInt(fields[5]));
                    }
                    //increment counter tracking the final official book count for each book type
                    finalBookCount++;
                }

                //copy the book array into a correctly sized array for the final set of books
                Book[] finalList = new Book[finalBookCount];
                for (int j = 0; j < finalBookCount; j++) {
                    finalList[j] = books[j];
                }

                //copy the array of books into the binary file
                binaryOut.writeObject(finalList);
                binaryOut.close();

                //update book count record with the final count of books of each genre
                bookCount[i] = finalBookCount;

            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}