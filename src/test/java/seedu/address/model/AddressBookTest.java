package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutees.ALICE;
import static seedu.address.testutil.TypicalTutees.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tutee.Tutee;
import seedu.address.model.tutee.exceptions.DuplicateTuteeException;
import seedu.address.testutil.TuteeBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getTuteeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateTutees_throwsDuplicateTuteeException() {
        // Two tutees with the same identity fields
        Tutee editedAlice = new TuteeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Tutee> newTutees = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newTutees);

        assertThrows(DuplicateTuteeException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasTutee_nullTutee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasTutee(null));
    }

    @Test
    public void hasTutee_tuteeNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasTutee(ALICE));
    }

    @Test
    public void hasTutee_tuteeInAddressBook_returnsTrue() {
        addressBook.addTutee(ALICE);
        assertTrue(addressBook.hasTutee(ALICE));
    }

    @Test
    public void hasTutee_tuteeWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addTutee(ALICE);
        Tutee editedAlice = new TuteeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasTutee(editedAlice));
    }

    @Test
    public void getTuteeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getTuteeList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose tutees list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Tutee> tutees = FXCollections.observableArrayList();

        AddressBookStub(Collection<Tutee> tutees) {
            this.tutees.setAll(tutees);
        }

        @Override
        public ObservableList<Tutee> getTuteeList() {
            return tutees;
        }
    }

}
