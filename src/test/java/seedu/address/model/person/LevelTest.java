package seedu.address.model.person;

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
        assertFalse(Level.isValidLevel("@2"));
        assertFalse(Level.isValidLevel("$5"));
    }
}
