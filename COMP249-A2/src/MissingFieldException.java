/**
 * Names(s) and ID's: Diana Edvi (40198139) AND Fuad Awad (40195634)
 * COMP249
 * Assignment # 2
 * Due Date: November 11, 2023
 */

// -----------------------------------------------------
// Assignment 2
// Question: (Part 1)
// Written by: Diana Edvi (40198139) AND Fuad Awad (40195634)
// -----------------------------------------------------
/**
 * The MissingFieldException class represents an exception that is thrown when a required field is missing.
 */
public class MissingFieldException extends Exception{
    /**
     * Constructs a new default MissingFieldException.
     */
    public MissingFieldException() {
        super();
    }
    /**
     * Constructs a new parameterized MissingFieldException with a custom message.
     *
     * @param message a description of the exception
     */
    public MissingFieldException(String message) {
        super(message);
    }
}
