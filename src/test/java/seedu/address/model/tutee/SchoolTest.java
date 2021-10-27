package seedu.address.model.tutee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SchoolTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new School(null));
    }

    @Test
    public void constructor_invalidSchool_throwsIllegalArgumentException() {
        String invalidSchool = "";
        assertThrows(IllegalArgumentException.class, () -> new School(invalidSchool));
    }

    @Test
    public void isValidSchool() {
        // null school
        assertThrows(NullPointerException.class, () -> School.isValidSchool(null));

        // invalid schools
        assertFalse(School.isValidSchool("")); // empty string
        assertFalse(School.isValidSchool(" ")); // spaces only

        // valid schools
        assertTrue(School.isValidSchool("Anglo-Chinese School (Primary)"));
        assertTrue(School.isValidSchool("-")); // one character
        assertTrue(School.isValidSchool("Convent of the Holy Infant Jesus - Our Lady Queen of Peace")); // long address
    }
}
