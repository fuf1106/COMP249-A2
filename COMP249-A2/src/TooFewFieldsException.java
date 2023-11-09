/**
 * Names(s) and ID's: Diana Edvi (40198139) AND Fuad Awad (40195634)
 * COMP249
 * Assignment # 2
 * Due Date: November 11, 2023
 */

// -----------------------------------------------------
// Assignment 2
// Question: (Parts 1)
// Written by: Diana Edvi (40198139) AND Fuad Awad (40195634)
// -----------------------------------------------------
/**
 * The TooFewFieldsException class represents an exception that is thrown when there are too few fields or elements
 * than required in a book entry
 */
public class TooFewFieldsException extends Exception {
    /**
     * Constructs a new default TooFewFieldsException.
     */
    public TooFewFieldsException() {
        super();
    }
    /**
     * Constructs a new parameterized TooFewFieldsException with a custom message.
     *
     * @param message a description of the exception
     */
    public TooFewFieldsException(String message) {
        super(message);
    }
}
