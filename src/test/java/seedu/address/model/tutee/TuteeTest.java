package seedu.address.model.tutee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutees.ALICE;
import static seedu.address.testutil.TypicalTutees.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TuteeBuilder;

public class TuteeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Tutee tutee = new TuteeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> tutee.getTags().remove(0));
    }

    @Test
    public void displayEducationLevelInFull() {
        // display value of p1 to Primary 1
        assertEquals("Primary 1", ALICE.getLevel().stringRepresentation);

        // display value of p2 to Primary 2
        assertEquals("Secondary 2", BOB.getLevel().stringRepresentation);
    }

    @Test
    public void isSameTutee() {
        // same object -> returns true
        assertTrue(ALICE.isSameTutee(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameTutee(null));

        // same name, all other attributes different -> returns true
        Tutee editedAlice = new TuteeBuilder(ALICE).withPhone(VALID_PHONE_BOB).withLevel(VALID_LEVEL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameTutee(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new TuteeBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameTutee(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Tutee editedBob = new TuteeBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameTutee(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new TuteeBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameTutee(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Tutee aliceCopy = new TuteeBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different tutee -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Tutee editedAlice = new TuteeBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new TuteeBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new TuteeBuilder(ALICE).withLevel(VALID_LEVEL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new TuteeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TuteeBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
