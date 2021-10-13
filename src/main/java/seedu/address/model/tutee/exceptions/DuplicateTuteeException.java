package seedu.address.model.tutee.exceptions;

/**
 * Signals that the operation will result in duplicate Tutees (Tutees are considered duplicates if they have the same
 * identity).
 */
public class DuplicateTuteeException extends RuntimeException {
    public DuplicateTuteeException() {
        super("Operation would result in duplicate tutees");
    }
}
