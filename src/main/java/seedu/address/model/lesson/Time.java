package seedu.address.model.lesson;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents the Time of a particular Lesson.
 */
public class Time {

    public static final long MINIMUM_DURATION = 10;

    public static final String MESSAGE_CONSTRAINTS_INVALID_DAY =
            "Day of week should be included in full";

    public static final String MESSAGE_CONSTRAINTS_INVALID_LOCALTIME =
            "The start or end time should be in HH:MM format";

    public static final String MESSAGE_CONSTRAINTS_IMPROPER_TIME =
            "Start time should be before end time";

    public static final String MESSAGE_CONSTRAINTS_INVALID_DURATION =
            "Lesson duration must be at least " + MINIMUM_DURATION + " minutes";

    private DayOfWeek dayOfOccurrence;
    @JsonFormat(pattern="HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern="HH:mm")
    private LocalTime endTime;

    /**
     * Constructs a {@code Time}.
     *
     * @param dayOfOccurrence Which day the lesson occurs.
     * @param startTime The time the lesson starts.
     * @param endTime The time the lesson ends.
     */
    public Time(DayOfWeek dayOfOccurrence, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(dayOfOccurrence, startTime, endTime);
        checkArgument(startTime.isBefore(endTime), MESSAGE_CONSTRAINTS_IMPROPER_TIME);
        checkArgument(isValidDuration(startTime, endTime, MINIMUM_DURATION), MESSAGE_CONSTRAINTS_INVALID_DURATION);

        this.dayOfOccurrence = dayOfOccurrence;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Time() {
    }

    /**
     * Returns true if the time interval between the given start time and the given end time
     * is at least the minimum duration.
     */
    public static boolean isValidDuration(LocalTime startTime, LocalTime endTime, long minDuration) {
        long duration = Duration.between(startTime, endTime).toMinutes();
        return duration >= minDuration;
    }

    public DayOfWeek getDayOfOccurrence() {
        return dayOfOccurrence;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Returns true if both times have the same day of occurrence, start time and end time.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Time)) {
            return false;
        }

        Time otherLesson = (Time) other;
        return otherLesson.getDayOfOccurrence().equals(getDayOfOccurrence())
                && otherLesson.getStartTime().equals(getStartTime())
                && otherLesson.getEndTime().equals(getEndTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(dayOfOccurrence, startTime, endTime);
    }

    @Override
    public String toString() {
        return getDayOfOccurrence().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) // output "Mon" instead of "Monday"
                + " " + getStartTime() + " to " + getEndTime();
    }

}
