package seedu.address.model.exceptions;

/**
 * Represents a schedule clash error that occurs when a lesson that clashes with existing lessons is added.
 */
public class ScheduleClashException extends Exception {
    public ScheduleClashException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ScheduleClashException} with the specified detail {@code message} and {@code cause}.
     */
    public ScheduleClashException(String message, Throwable cause) {
        super(message, cause);
    }
}
