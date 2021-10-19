package seedu.address.model.lesson;

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
        Time differentTime = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(20, 0));

        // same object -> returns true
        assertTrue(time.equals(time));

        // same values -> returns true
        Time timeCopy = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(18, 0));
        assertTrue(time.equals(timeCopy));

        // different types -> returns false
        assertFalse(time.equals(1));

        // null -> returns false
        assertFalse(time.equals(null));

        // different time -> returns false
        assertFalse(time.equals(differentTime));
    }
}
