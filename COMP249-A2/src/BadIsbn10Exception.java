/**
 * Names(s) and ID's: Diana Edvi (40198139) AND Fuad Awad (40195634)
 * COMP249
 * Assignment # 2
 * Due Date: November 11, 2023
 */

// -----------------------------------------------------
// Assignment 2
// Question: (Part 2)
// Written by: Diana Edvi (40198139) AND Fuad Awad (40195634)
// -----------------------------------------------------

/**
 * The BadIsbn10Exception class represents an exception that is thrown when an ISBN-10
 * (International Standard Book Number) is considered invalid.
 */
public class BadIsbn10Exception extends Exception {
    /**
     * Constructs a new default BadIsbn10Exception
     */
    public BadIsbn10Exception() {
        super();
    }
    /**
     * Constructs a new paramaterized BadIsbn10Exception
     *
     * @param message a description of the exception
     */
    public BadIsbn10Exception(String message) {
        super(message);
    }
}
