package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class LessonTest {

    @Test
    public void equals() {
        Subject subject = new Subject("English");
        Subject differentSubject = new Subject("German");

        Time time = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(18, 0));
        Time differentTime = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(20, 0));

        Lesson lesson = new Lesson(subject, time);
        Lesson differentLesson = new Lesson(differentSubject, differentTime);

        // same object -> returns true
        assertTrue(lesson.equals(lesson));

        // same values -> returns true
        Lesson lessonCopy = new Lesson(subject, time);
        assertTrue(lesson.equals(lessonCopy));

        // different types -> returns false
        assertFalse(lesson.equals(1));

        // null -> returns false
        assertFalse(lesson.equals(null));

        // different lesson -> returns false
        assertFalse(lesson.equals(differentLesson));
    }
}
