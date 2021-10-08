package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Lesson in the application.
 */
public class Lesson {

    private Subject subject;
    private Time time;

    /**
     * Constructs a {@code Lesson}.
     *
     * @param subject The subject of the lesson.
     * @param time The time of the lesson.
     */
    public Lesson(Subject subject, Time time) {
        requireAllNonNull(subject, time);
        this.subject = subject;
        this.time = time;
    }

    public Lesson() {
    }

    public Subject getSubject() {
        return subject;
    }

    public Time getTime() {
        return time;
    }

    /**
     * Returns true if both lessons have the same subject and time.
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
        return otherLesson.getSubject().equals(getSubject())
                && otherLesson.getTime().equals(getTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(subject, time);
    }

    @Override
    public String toString() {
        return "[" + subject + " " + time + "]";
    }

}
