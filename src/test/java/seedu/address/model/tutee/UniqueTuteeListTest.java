package seedu.address.model.tutee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PRACTICAL;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutees.ALICE;
import static seedu.address.testutil.TypicalTutees.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tutee.exceptions.DuplicateTuteeException;
import seedu.address.model.tutee.exceptions.TuteeNotFoundException;
import seedu.address.testutil.TuteeBuilder;

public class UniqueTuteeListTest {

    private final UniqueTuteeList uniqueTuteeList = new UniqueTuteeList();

    @Test
    public void contains_nullTutee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTuteeList.contains(null));
    }

    @Test
    public void contains_tuteeNotInList_returnsFalse() {
        assertFalse(uniqueTuteeList.contains(ALICE));
    }

    @Test
    public void contains_tuteeInList_returnsTrue() {
        uniqueTuteeList.add(ALICE);
        assertTrue(uniqueTuteeList.contains(ALICE));
    }

    @Test
    public void contains_tuteeWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTuteeList.add(ALICE);
        Tutee editedAlice = new TuteeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_PRACTICAL)
                .build();
        assertTrue(uniqueTuteeList.contains(editedAlice));
    }

    @Test
    public void add_nullTutee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTuteeList.add(null));
    }

    @Test
    public void add_duplicateTutee_throwsDuplicateTuteeException() {
        uniqueTuteeList.add(ALICE);
        assertThrows(DuplicateTuteeException.class, () -> uniqueTuteeList.add(ALICE));
    }

    @Test
    public void setTutee_nullTargetTutee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTuteeList.setTutee(null, ALICE));
    }

    @Test
    public void setTutee_nullEditedTutee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTuteeList.setTutee(ALICE, null));
    }

    @Test
    public void setTutee_targetTuteeNotInList_throwsTuteeNotFoundException() {
        assertThrows(TuteeNotFoundException.class, () -> uniqueTuteeList.setTutee(ALICE, ALICE));
    }

    @Test
    public void setTutee_editedTuteeIsSameTutee_success() {
        uniqueTuteeList.add(ALICE);
        uniqueTuteeList.setTutee(ALICE, ALICE);
        UniqueTuteeList expectedUniqueTuteeList = new UniqueTuteeList();
        expectedUniqueTuteeList.add(ALICE);
        assertEquals(expectedUniqueTuteeList, uniqueTuteeList);
    }

    @Test
    public void setTutee_editedTuteeHasSameIdentity_success() {
        uniqueTuteeList.add(ALICE);
        Tutee editedAlice = new TuteeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_PRACTICAL)
                .build();
        uniqueTuteeList.setTutee(ALICE, editedAlice);
        UniqueTuteeList expectedUniqueTuteeList = new UniqueTuteeList();
        expectedUniqueTuteeList.add(editedAlice);
        assertEquals(expectedUniqueTuteeList, uniqueTuteeList);
    }

    @Test
    public void setTutee_editedTuteeHasDifferentIdentity_success() {
        uniqueTuteeList.add(ALICE);
        uniqueTuteeList.setTutee(ALICE, BOB);
        UniqueTuteeList expectedUniqueTuteeList = new UniqueTuteeList();
        expectedUniqueTuteeList.add(BOB);
        assertEquals(expectedUniqueTuteeList, uniqueTuteeList);
    }

    @Test
    public void setTutee_editedTuteeHasNonUniqueIdentity_throwsDuplicateTuteeException() {
        uniqueTuteeList.add(ALICE);
        uniqueTuteeList.add(BOB);
        assertThrows(DuplicateTuteeException.class, () -> uniqueTuteeList.setTutee(ALICE, BOB));
    }

    @Test
    public void remove_nulTutee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTuteeList.remove(null));
    }

    @Test
    public void remove_tuteeDoesNotExist_throwsTuteeNotFoundException() {
        assertThrows(TuteeNotFoundException.class, () -> uniqueTuteeList.remove(ALICE));
    }

    @Test
    public void remove_existingTutee_removesTutee() {
        uniqueTuteeList.add(ALICE);
        uniqueTuteeList.remove(ALICE);
        UniqueTuteeList expectedUniqueTuteeList = new UniqueTuteeList();
        assertEquals(expectedUniqueTuteeList, uniqueTuteeList);
    }

    @Test
    public void setTutees_nullUniqueTuteeList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTuteeList.setTutees((UniqueTuteeList) null));
    }

    @Test
    public void setTutees_uniqueTuteeList_replacesOwnListWithProvidedUniqueTuteeList() {
        uniqueTuteeList.add(ALICE);
        UniqueTuteeList expectedUniqueTuteeList = new UniqueTuteeList();
        expectedUniqueTuteeList.add(BOB);
        uniqueTuteeList.setTutees(expectedUniqueTuteeList);
        assertEquals(expectedUniqueTuteeList, uniqueTuteeList);
    }

    @Test
    public void setTutees_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTuteeList.setTutees((List<Tutee>) null));
    }

    @Test
    public void setTutees_list_replacesOwnListWithProvidedList() {
        uniqueTuteeList.add(ALICE);
        List<Tutee> tuteeList = Collections.singletonList(BOB);
        uniqueTuteeList.setTutees(tuteeList);
        UniqueTuteeList expectedUniqueTuteeList = new UniqueTuteeList();
        expectedUniqueTuteeList.add(BOB);
        assertEquals(expectedUniqueTuteeList, uniqueTuteeList);
    }

    @Test
    public void setTutees_listWithDuplicateTutees_throwsDuplicateTuteeException() {
        List<Tutee> listWithDuplicateTutees = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateTuteeException.class, () -> uniqueTuteeList.setTutees(listWithDuplicateTutees));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTuteeList.asUnmodifiableObservableList().remove(0));
    }
}
