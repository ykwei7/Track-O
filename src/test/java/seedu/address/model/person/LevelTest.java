package seedu.address.model.person;

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
        assertEquals(Level.parse("p3"), "Primary 3");
        assertEquals(Level.parse("p5"), "Primary 5");

        // secondary level
        assertEquals(Level.parse("s3"), "Secondary 3");
        assertEquals(Level.parse("s4"), "Secondary 4");

        // jc level
        assertEquals(Level.parse("j1"), "JC 1");
        assertEquals(Level.parse("j2"), "JC 2");

        // invalid input
        assertEquals(Level.parse("a3"), "Should not happen due to regex validation");
    }
}
