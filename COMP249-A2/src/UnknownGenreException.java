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
 * The UnknownGenreException class represents an exception that is thrown when an unknown genre
 * is encountered.
 */
public class UnknownGenreException extends Exception {
    /**
     * Constructs a new default UnknownGenreException.
     */
    public UnknownGenreException() {
        super();
    }

    /**
     * Constructs a new parameterized UnknownGenreException with a custom message.
     *
     * @param message a description of the exception
     */
    public UnknownGenreException(String message) {
        super(message);
    }
}

