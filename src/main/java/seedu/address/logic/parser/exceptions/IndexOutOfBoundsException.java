package seedu.address.logic.parser.exceptions;

/**
 * Represents a parse error encountered when index is out of range.
 */
public class IndexOutOfBoundsException extends Exception {

    public IndexOutOfBoundsException(String message) {
        super(message);
    }

    public IndexOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
