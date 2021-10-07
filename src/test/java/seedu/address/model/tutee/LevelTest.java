package seedu.address.model.tutee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LevelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Level(null));
    }

    @Test
    public void constructor_invalidLevel_throwsIllegalArgumentException() {
        String invalidLevel = "";
        assertThrows(IllegalArgumentException.class, () -> new Level(invalidLevel));
    }

    @Test
    public void isValidLevel() {
        // null level
        assertThrows(NullPointerException.class, () -> Level.isValidLevel(null));

        // blank level
        assertFalse(Level.isValidLevel("")); // empty string
        assertFalse(Level.isValidLevel(" ")); // spaces only

        // invalid level
        assertFalse(Level.isValidLevel("p7"));
        assertFalse(Level.isValidLevel("w4"));
    }

    @Test
    public void parse() {
        // primary level
        assertEquals("Primary 3", Level.parse("p3"));
        assertEquals("Primary 5", Level.parse("p5"));

        // secondary level
        assertEquals("Secondary 3", Level.parse("s3"));
        assertEquals("Secondary 4", Level.parse("s4"));

        // jc level
        assertEquals("JC 1", Level.parse("j1"));
        assertEquals("JC 2", Level.parse("j2"));

        // invalid input
        assertEquals("Should not happen due to regex validation", Level.parse("a3"));
    }
}
