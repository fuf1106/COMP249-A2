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
 * The BadPriceException class represents an exception that is thrown when a price value
 * is considered invalid.
 */
public class BadPriceException extends Exception {
    /**
     * Constructs a new default BadPriceException.
     */
    public BadPriceException() {
        super();
    }
    /**
     * Constructs a new parameterized BadPriceException.
     *
     * @param message a description of the exception
     */
    public BadPriceException(String message) {
        super(message);
    }
}
