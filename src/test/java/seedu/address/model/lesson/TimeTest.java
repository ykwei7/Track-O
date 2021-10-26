package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    public void constructor_anyNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null, LocalTime.NOON, LocalTime.NOON));
        assertThrows(NullPointerException.class, () -> new Time(DayOfWeek.MONDAY, null, LocalTime.NOON));
        assertThrows(NullPointerException.class, () -> new Time(DayOfWeek.MONDAY, LocalTime.NOON, null));
        assertThrows(NullPointerException.class, () -> new Time(null, null, null)); // all null
    }

    @Test
    public void constructor_startTimeNotBeforeEndTime_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Time(DayOfWeek.TUESDAY, LocalTime.NOON, LocalTime.NOON));
        assertThrows(IllegalArgumentException.class, () ->
                new Time(DayOfWeek.TUESDAY, LocalTime.NOON, LocalTime.MIDNIGHT));
    }

    @Test
    public void constructor_invalidDuration_throwsIllegalArgumentException() {
        final double minDuration = Time.MINIMUM_DURATION;
        final double invalidDuration = minDuration - 1;
        final long invalidDurationInMinutes = (long) (invalidDuration * 60);

        assertThrows(IllegalArgumentException.class, () ->
                new Time(DayOfWeek.TUESDAY, LocalTime.NOON, LocalTime.NOON.plusMinutes(invalidDurationInMinutes)));
    }

    @Test
    public void equals() {
        Time time = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(18, 0));

        // same object -> returns true
        assertTrue(time.equals(time));

        // same values -> returns true
        Time timeCopy = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(18, 0));
        assertTrue(time.equals(timeCopy));

        // same day and overlapping start time -> returns true
        Time overlapStartTime = new Time(DayOfWeek.FRIDAY, LocalTime.of(11, 0), LocalTime.of(13, 0));
        assertTrue(time.equals(overlapStartTime));

        // same day and fully contained inside -> returns true
        Time fullOverlapTime = new Time(DayOfWeek.FRIDAY, LocalTime.of(12, 15), LocalTime.of(16, 30));
        assertTrue(time.equals(fullOverlapTime));

        // same day and overlapping end time -> returns true
        Time overlapEndTime = new Time(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0));
        assertTrue(time.equals(overlapEndTime));

        // different types -> returns false
        assertFalse(time.equals(1));

        // null -> returns false
        assertFalse(time.equals(null));

        // different day -> returns false
        Time differentDayTime = new Time(DayOfWeek.MONDAY, LocalTime.NOON, LocalTime.of(18, 0));
        assertFalse(time.equals(differentDayTime));

        // same day and back-to-back (no overlap) time -> returns false
        Time differentTime = new Time(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0));
        assertFalse(time.equals(differentTime));
    }

    @Test
    public void compareTo() {
        Time time = new Time(DayOfWeek.WEDNESDAY, LocalTime.NOON, LocalTime.of(18, 0));

        // comparing with later day -> returns a negative integer
        Time timeWithLaterDay = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(18, 0));
        assertTrue(time.compareTo(timeWithLaterDay) < 0);

        // comparing with earlier day -> returns a positive integer
        Time timeWithEarlierDay = new Time(DayOfWeek.MONDAY, LocalTime.NOON, LocalTime.of(18, 0));
        assertTrue(time.compareTo(timeWithEarlierDay) > 0);

        // comparing with same day but overlapping time -> returns 0 (i.e. both are equal)
        Time timeWithOverlap = new Time(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0));
        assertEquals(0, time.compareTo(timeWithOverlap));

        // comparing with same day but later time -> returns a negative integer
        Time laterTime = new Time(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(21, 0));
        assertTrue(time.compareTo(laterTime) < 0);

        // comparing with same day but earlier time -> returns a positive integer
        Time earlierTime = new Time(DayOfWeek.WEDNESDAY, LocalTime.of(7, 30), LocalTime.of(10, 0));
        assertTrue(time.compareTo(earlierTime) > 0);
    }
}
