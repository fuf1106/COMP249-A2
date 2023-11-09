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
 * The BadIsbn13Exception class represents an exception that is thrown when an ISBN-13
 * (International Standard Book Number) is considered invalid.
 */
public class BadIsbn13Exception extends Exception {
    /**
     * Constructs a new default BadIsbn13Exception.
     */
    public BadIsbn13Exception() {
        super();
    }
    /**
     * Constructs a new parameterized BadIsbn13Exception.
     *
     * @param message a description of the exception
     */
    public BadIsbn13Exception(String message) {
        super(message);
    }
}
