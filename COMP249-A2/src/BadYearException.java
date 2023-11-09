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
 * The BadYearException class represents an exception that is thrown when a year value
 * is considered invalid.
 */
public class BadYearException extends Exception {
    /**
     * Constructs a new default BadYearException.
     */
    public BadYearException() {
        super();
    }
    /**
     * Constructs a new parameterized BadYearException.
     *
     * @param message a description of the exception
     */
    public BadYearException(String message) {
        super(message);
    }
}
