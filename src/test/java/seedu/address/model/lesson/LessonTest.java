package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class LessonTest {

    @Test
    public void isValidHourlyRate() {
        // null hourly rate
        assertThrows(NullPointerException.class, () -> Lesson.isValidHourlyRate(null));

        // invalid hourly rates
        assertFalse(Lesson.isValidHourlyRate("")); // empty string
        assertFalse(Lesson.isValidHourlyRate(" ")); // spaces only
        assertFalse(Lesson.isValidHourlyRate("015")); // starts with 0
        assertFalse(Lesson.isValidHourlyRate("15a")); // contains non-numeric characters

        assertFalse(Lesson.isValidHourlyRate("15.")); // no decimal places
        assertFalse(Lesson.isValidHourlyRate("15.1")); // only 1 decimal place
        assertFalse(Lesson.isValidHourlyRate("15.133")); // exceeds 2 decimal places

        // last decimal place does not end with 0 or 5
        assertFalse(Lesson.isValidHourlyRate("155.09"));
        assertFalse(Lesson.isValidHourlyRate("123.44"));
        assertFalse(Lesson.isValidHourlyRate("143.87"));

        // valid hourly rates
        assertTrue(Lesson.isValidHourlyRate("3"));
        assertTrue(Lesson.isValidHourlyRate("40"));
        assertTrue(Lesson.isValidHourlyRate("4.05"));
        assertTrue(Lesson.isValidHourlyRate("400.70"));
    }

    @Test
    public void equals() {
        Subject subject = new Subject("English");
        Subject differentSubject = new Subject("German");

        Time time = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(18, 0));
        Time overlapTime = new Time(DayOfWeek.FRIDAY, LocalTime.of(11, 45), LocalTime.of(20, 0));
        Time differentTime = new Time(DayOfWeek.FRIDAY, LocalTime.of(7, 45), LocalTime.of(8, 45));

        double cost = 45.0;

        Lesson lesson = new Lesson(subject, time, cost);
        Lesson overlappedLesson = new Lesson(differentSubject, overlapTime, cost);
        Lesson differentLesson = new Lesson(differentSubject, differentTime, cost);

        // same object -> returns true
        assertTrue(lesson.equals(lesson));

        // same values -> returns true
        Lesson lessonCopy = new Lesson(subject, time, cost);
        assertTrue(lesson.equals(lessonCopy));

        // overlapping lesson time -> returns true
        assertTrue(lesson.equals(overlappedLesson));

        // different types -> returns false
        assertFalse(lesson.equals(1));

        // null -> returns false
        assertFalse(lesson.equals(null));

        // different lesson -> returns false
        assertFalse(lesson.equals(differentLesson));
    }

    @Test
    public void compareTo() {
        Subject subject = new Subject("English");
        Time time = new Time(DayOfWeek.WEDNESDAY, LocalTime.NOON, LocalTime.of(18, 0));
        double cost = 45.0;
        Lesson lesson = new Lesson(subject, time, cost);

        // comparing with later day -> returns a negative integer
        Time timeWithLaterDay = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(18, 0));
        Lesson lessonWithLaterDay = new Lesson(subject, timeWithLaterDay, cost);

        assertTrue(lesson.compareTo(lessonWithLaterDay) < 0);

        // comparing with earlier day -> returns a positive integer
        Time timeWithEarlierDay = new Time(DayOfWeek.MONDAY, LocalTime.NOON, LocalTime.of(18, 0));
        Lesson lessonWithEarlierDay = new Lesson(subject, timeWithEarlierDay, cost);

        assertTrue(lesson.compareTo(lessonWithEarlierDay) > 0);

        // comparing with same day but overlapping time -> returns 0 (i.e. both are equal)
        Time timeWithOverlap = new Time(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0));
        Lesson lessonWithOverlap = new Lesson(subject, timeWithOverlap, cost);

        assertEquals(0, lesson.compareTo(lessonWithOverlap));

        // comparing with same day but later time -> returns a negative integer
        Time laterTime = new Time(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(21, 0));
        Lesson lessonWithLaterTime = new Lesson(subject, laterTime, cost);

        assertTrue(lesson.compareTo(lessonWithLaterTime) < 0);

        // comparing with same day but earlier time -> returns a positive integer
        Time earlierTime = new Time(DayOfWeek.WEDNESDAY, LocalTime.of(7, 30), LocalTime.of(10, 0));
        Lesson lessonWithEarlierTime = new Lesson(subject, earlierTime, cost);

        assertTrue(lesson.compareTo(lessonWithEarlierTime) > 0);
    }
}
