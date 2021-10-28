package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Lesson in the application.
 */
public class Lesson implements Comparable<Lesson> {

    public static final String MESSAGE_CONSTRAINTS =
            "Hourly rate should only contain numbers expressed strictly in "
                    + "either no decimal places or two decimal places with the last decimal place being 0 or 5, "
                    + "and it should not be blank.";
    /*
     * The first character has to a digit that is not zero.
     * Only 0 or 2 decimal places of a number is allowed.
     * For 2 decimal places, the last decimal place has to end in either 0 or 5.
     */
    public static final String VALIDATION_REGEX_HOURLY_RATE_NO_DECIMAL_PLACES = "^[1-9][\\d]*$";
    public static final String VALIDATION_REGEX_HOURLY_RATE_TWO_DECIMAL_PLACES = "^[1-9][\\d]*[.][0-9][0|5]$";

    private Subject subject;
    private Time time;
    private double hourlyRate;
    private double cost;

    /**
     * Constructs a {@code Lesson}.
     *
     * @param subject The subject of the lesson.
     * @param time The time of the lesson.
     * @param hourlyRate The hourly rate of the lesson.
     */
    public Lesson(Subject subject, Time time, double hourlyRate) {
        requireAllNonNull(subject, time);
        this.subject = subject;
        this.time = time;
        this.hourlyRate = hourlyRate;
        this.cost = computeCost(time, hourlyRate);
    }

    /**
     * Default constructor to aid Jackson in deserializing the class.
     * Solution adapted from https://www.baeldung.com/jackson-exception#2-the-solution
     */
    public Lesson() {
    }

    /**
     * Returns true if a given string is a valid hourly rate.
     */
    public static boolean isValidHourlyRate(String hourlyRate) {
        return hourlyRate.matches(VALIDATION_REGEX_HOURLY_RATE_NO_DECIMAL_PLACES)
                || hourlyRate.matches(VALIDATION_REGEX_HOURLY_RATE_TWO_DECIMAL_PLACES);
    }

    /**
     * Converts lesson list to a formatted String output for easier viewing.
     * @param lessonList List of lessons to obtain
     * @return
     */
    public static String lessonListToString(List<Lesson> lessonList) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\nLessons: \n");
        for (int i = 0; i < lessonList.size(); i++) {
            builder.append(i + 1)
                    .append(". ")
                    .append(lessonList.get(i));
        }
        return builder.toString();
    }

    public Subject getSubject() {
        return subject;
    }

    public Time getTime() {
        return time;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getCost() {
        return cost;
    }

    /**
     * Computes cost of lesson based on the product of duration and hourly rate.
     *
     * @param time The time of the lesson.
     * @param hourlyRate The hourly rate of the lesson.
     * @return The cost of the lesson as a double with 2 decimal places.
     */
    private double computeCost(Time time, double hourlyRate) {
        double cost = time.getDuration() * hourlyRate;

        // Solution below adapted from https://www.baeldung.com/java-bigdecimal-biginteger#rounding
        BigDecimal bd = new BigDecimal(cost);
        BigDecimal roundedBd = bd.setScale(2, RoundingMode.HALF_EVEN);

        return roundedBd.doubleValue();
    }

    /**
     * Returns true if both lessons have the same time, i.e. they overlap with one another.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return otherLesson.getTime().equals(getTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(time);
    }

    @Override
    public String toString() {
        return String.format("%s    %s \n(Hourly rate: $%.2f/h, Total cost: $%.2f)\n", subject, time, hourlyRate, cost);
    }

    /**
     * Returns the condensed string representation of the lesson, containing only subject and time.
     */
    public String toCondensedString() {
        return String.format("%s\n%s\n", subject, time);
    }

    @Override
    public int compareTo(Lesson other) {
        return time.compareTo(other.getTime());
    }

}
