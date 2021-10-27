package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutees.CARL;
import static seedu.address.testutil.TypicalTutees.DANIEL;
import static seedu.address.testutil.TypicalTutees.ELLE;
import static seedu.address.testutil.TypicalTutees.FIONA;
import static seedu.address.testutil.TypicalTutees.GEORGE;
import static seedu.address.testutil.TypicalTutees.getTypicalTutees;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Time;
import seedu.address.model.tutee.Tutee;

public class ScheduleTest {

    private static final Lesson FIRST_LESSON = new Lesson(
            new Subject("Geography"),
            new Time(DayOfWeek.MONDAY, LocalTime.of(6, 0), LocalTime.of(7, 0)),
            40);

    private static final Lesson SECOND_LESSON = new Lesson(
            new Subject("History"),
            new Time(DayOfWeek.TUESDAY, LocalTime.of(6, 0), LocalTime.of(7, 0)),
            42.75);

    private static final Lesson CLASHING_LESSON = new Lesson(
            new Subject("Literature"),
            new Time(DayOfWeek.MONDAY, LocalTime.of(5, 30), LocalTime.of(6, 30)),
            41.50);

    private Schedule schedule;

    @BeforeEach
    public void setUp() throws ScheduleClashException {
        schedule = new Schedule(getTypicalTutees());
    }

    @Test
    public void add_noClashingLesson_success() throws ScheduleClashException {
        TreeMap<Lesson, String> expectedMap = schedule.getSortedLessonsMap();

        String carlName = CARL.getName().toString();
        String danielName = DANIEL.getName().toString();

        schedule.add(FIRST_LESSON, carlName);
        schedule.add(SECOND_LESSON, danielName);

        expectedMap.put(FIRST_LESSON, carlName);
        expectedMap.put(SECOND_LESSON, danielName);

        assertEquals(expectedMap, schedule.getSortedLessonsMap());
    }

    @Test
    public void add_clashingLesson_throwsScheduleClashException() throws ScheduleClashException {
        String elleName = ELLE.getName().toString();
        schedule.add(FIRST_LESSON, elleName);
        assertThrows(ScheduleClashException.class, () -> schedule.add(CLASHING_LESSON, elleName));
    }

    @Test
    public void remove_lessonAndNameInSchedule_returnsTrue() throws ScheduleClashException {
        String fionaName = FIONA.getName().toString();
        String georgeName = GEORGE.getName().toString();

        schedule.add(FIRST_LESSON, fionaName);
        schedule.add(SECOND_LESSON, georgeName);

        assertTrue(schedule.remove(FIRST_LESSON, fionaName));
        assertTrue(schedule.remove(SECOND_LESSON, georgeName));
    }

    @Test
    public void remove_lessonAndNameNotInSchedule_returnsFalse() throws ScheduleClashException {
        String fionaName = FIONA.getName().toString();
        String georgeName = GEORGE.getName().toString();

        // these lessons are not part of Fiona's or George's lessons
        assertFalse(schedule.remove(FIRST_LESSON, fionaName));
        assertFalse(schedule.remove(SECOND_LESSON, georgeName));

        schedule.add(FIRST_LESSON, fionaName);

        // the lesson to be removed should be under Fiona's name,
        // but is instead under George's name
        assertFalse(schedule.remove(FIRST_LESSON, georgeName));
    }

    @Test
    public void equals() throws ScheduleClashException {
        // same values -> returns true
        Schedule scheduleCopy = new Schedule(getTypicalTutees());
        assertTrue(schedule.equals(scheduleCopy));

        // same object -> returns true
        assertTrue(schedule.equals(schedule));

        // null -> returns false
        assertFalse(schedule.equals(null));

        // different type -> returns false
        assertFalse(schedule.equals(5));

        // different schedule -> returns false
        List<Tutee> emptyTuteeList = new ArrayList<>();
        Schedule emptySchedule = new Schedule(emptyTuteeList);
        assertFalse(schedule.equals(emptySchedule));
    }

}
