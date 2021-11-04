package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PRACTICAL;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutees.ALICE;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

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

public class TrackOTest {

    private final TrackO trackO = new TrackO();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), trackO.getTuteeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> trackO.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTrackO_replacesData() {
        TrackO newData = getTypicalTrackO();
        trackO.resetData(newData);
        assertEquals(newData, trackO);
    }

    @Test
    public void resetData_withDuplicateTutees_throwsDuplicateTuteeException() {
        // Two tutees with the same identity fields
        Tutee editedAlice = new TuteeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_PRACTICAL)
                .build();
        List<Tutee> newTutees = Arrays.asList(ALICE, editedAlice);
        TrackOStub newData = new TrackOStub(newTutees);

        assertThrows(DuplicateTuteeException.class, () -> trackO.resetData(newData));
    }

    @Test
    public void hasTutee_nullTutee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> trackO.hasTutee(null));
    }

    @Test
    public void hasTutee_tuteeNotInTrackO_returnsFalse() {
        assertFalse(trackO.hasTutee(ALICE));
    }

    @Test
    public void hasTutee_tuteeInTrackO_returnsTrue() {
        trackO.addTutee(ALICE);
        assertTrue(trackO.hasTutee(ALICE));
    }

    @Test
    public void hasTutee_tuteeWithSameIdentityFieldsInTrackO_returnsTrue() {
        trackO.addTutee(ALICE);
        Tutee editedAlice = new TuteeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_PRACTICAL)
                .build();
        assertTrue(trackO.hasTutee(editedAlice));
    }

    @Test
    public void getTuteeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> trackO.getTuteeList().remove(0));
    }

    /**
     * A stub ReadOnlyTrackO whose tutees list can violate interface constraints.
     */
    private static class TrackOStub implements ReadOnlyTrackO {
        private final ObservableList<Tutee> tutees = FXCollections.observableArrayList();

        TrackOStub(Collection<Tutee> tutees) {
            this.tutees.setAll(tutees);
        }

        @Override
        public ObservableList<Tutee> getTuteeList() {
            return tutees;
        }
    }

}
