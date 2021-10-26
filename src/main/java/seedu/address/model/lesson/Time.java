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
public class Time implements Comparable<Time> {

    public static final double MINIMUM_DURATION = 0.5;

    public static final String MESSAGE_CONSTRAINTS_INVALID_DAY =
            "Day of week should be an integer in the range [1, 7]";

    public static final String MESSAGE_CONSTRAINTS_INVALID_LOCALTIME =
            "The start or end time should be in HH:MM format";

    public static final String MESSAGE_CONSTRAINTS_IMPROPER_TIME =
            "Start time should be before end time";

    public static final String MESSAGE_CONSTRAINTS_INVALID_DURATION =
            "Lesson duration must be at least " + MINIMUM_DURATION + " hours";

    private DayOfWeek dayOfOccurrence;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private double duration;

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

        duration = computeDuration(startTime, endTime);
        checkArgument(isValidDuration(duration, MINIMUM_DURATION), MESSAGE_CONSTRAINTS_INVALID_DURATION);

        this.dayOfOccurrence = dayOfOccurrence;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Default constructor to aid Jackson in deserializing the class.
     * Solution adapted from https://www.baeldung.com/jackson-exception#2-the-solution
     */
    public Time() {
    }

    /**
     * Computes the number of hours between start time and end time.
     *
     * @param startTime The start time.
     * @param endTime The end time.
     * @return A double representing the number of hours.
     */
    private double computeDuration(LocalTime startTime, LocalTime endTime) {
        long durationInMinutes = Duration.between(startTime, endTime).toMinutes();
        return durationInMinutes / 60.0;
    }

    /**
     * Returns true if the time interval between the given start time and the given end time
     * is at least the minimum duration.
     */
    public static boolean isValidDuration(double duration, double minDuration) {
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

    public double getDuration() {
        return duration;
    }

    /**
     * Returns true if both times have the same day of occurrence, and overlap with one another.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;
        return otherTime.getDayOfOccurrence().equals(getDayOfOccurrence())
                && isOverlap(otherTime);
    }

    /**
     * Checks if the two time intervals overlap. Precondition of start time before end time is enforced
     * by the constructor.
     * Solution adapted from https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
     */
    private boolean isOverlap(Time other) {
        return startTime.isBefore(other.getEndTime()) && other.getStartTime().isBefore(endTime);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(dayOfOccurrence);
    }

    @Override
    public String toString() {
        return getDayOfOccurrence().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) // output "Mon" instead of "Monday"
                + " " + getStartTime() + " to " + getEndTime();
    }

    @Override
    public int compareTo(Time other) {
        int compareDay = dayOfOccurrence.compareTo(other.getDayOfOccurrence());

        // different day
        if (compareDay != 0) {
            return compareDay;
        }

        if (isOverlap(other)) {
            return 0;
        }

        return startTime.compareTo(other.getStartTime());
    }

}
