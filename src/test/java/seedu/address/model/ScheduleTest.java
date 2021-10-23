package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Time;

public class ScheduleTest {

    private static final Lesson FIRST_LESSON = new Lesson(
            new Subject("Geography"),
            new Time(DayOfWeek.MONDAY, LocalTime.NOON, LocalTime.of(18, 0)),
            40);

    private static final Lesson SECOND_LESSON = new Lesson(
            new Subject("History"),
            new Time(DayOfWeek.TUESDAY, LocalTime.NOON, LocalTime.of(18, 0)),
            42.75);

    private static final Lesson CLASHING_LESSON = new Lesson(
            new Subject("Literature"),
            new Time(DayOfWeek.MONDAY, LocalTime.of(11, 30), LocalTime.of(12, 30)),
            41.50);

    @BeforeEach
    public void setUp() {
        Schedule.clear();
    }

    @AfterAll
    public static void tearDown() {
        Schedule.clear();
    }

    @Test
    public void add_noClashingLesson_success() throws ScheduleClashException {
        Schedule.add(FIRST_LESSON, "John");
        Schedule.add(SECOND_LESSON, "Peter");

        TreeMap<Lesson, String> expectedMap = new TreeMap<>();
        expectedMap.put(FIRST_LESSON, "John");
        expectedMap.put(SECOND_LESSON, "Peter");

        assertEquals(expectedMap, Schedule.getSortedLessonsMap());
    }

    @Test
    public void add_clashingLesson_throwsScheduleClashException() throws ScheduleClashException {
        Schedule.add(FIRST_LESSON, "John");
        assertThrows(ScheduleClashException.class, () -> Schedule.add(CLASHING_LESSON, "Tom"));
    }

    @Test
    public void remove_lessonAndNameInSchedule_returnsTrue() throws ScheduleClashException {
        Schedule.add(FIRST_LESSON, "John");
        Schedule.add(SECOND_LESSON, "Peter");

        assertTrue(Schedule.remove(FIRST_LESSON, "John"));
        assertTrue(Schedule.remove(SECOND_LESSON, "Peter"));
    }

    @Test
    public void remove_lessonAndNameNotInSchedule_returnsFalse() throws ScheduleClashException {
        // empty schedule
        assertFalse(Schedule.remove(FIRST_LESSON, "John"));
        assertFalse(Schedule.remove(SECOND_LESSON, "Peter"));

        Schedule.add(FIRST_LESSON, "John");

        // Same lesson but different name
        assertFalse(Schedule.remove(FIRST_LESSON, "Mary"));
    }

}
