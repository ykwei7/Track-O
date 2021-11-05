package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.TuteeBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ClearRemarkCommand}.
 */
public class ClearRemarkCommandTest {

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public ClearRemarkCommandTest() throws ScheduleClashException {
    }

    @Test
    public void execute_validIndexUnfilteredListNonEmptyRemark_success() throws ScheduleClashException {
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_SECOND_TUTEE.getZeroBased());
        Tutee clearedTutee = new TuteeBuilder(retrievedTutee).withRemark("-").build();
        ClearRemarkCommand clearRemarkCommand = new ClearRemarkCommand(INDEX_SECOND_TUTEE);

        String expectedMessage = String.format(ClearRemarkCommand.MESSAGE_SUCCESS, clearedTutee);

        ModelManager expectedModel = new ModelManager(model.getTrackO(), new UserPrefs());

        assertCommandSuccess(clearRemarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListEmptyRemark_throwsCommandException() {
        ClearRemarkCommand clearRemarkCommand = new ClearRemarkCommand(INDEX_FIRST_TUTEE);

        assertCommandFailure(clearRemarkCommand, model, ClearRemarkCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTuteeList().size() + 1);
        ClearRemarkCommand clearRemarkCommand = new ClearRemarkCommand(outOfBoundIndex);

        assertCommandFailure(clearRemarkCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ClearRemarkCommand clearRemarkFirstCommand = new ClearRemarkCommand(INDEX_FIRST_TUTEE);
        ClearRemarkCommand clearRemarkSecondCommand = new ClearRemarkCommand(INDEX_SECOND_TUTEE);

        // same object -> returns true
        assertTrue(clearRemarkFirstCommand.equals(clearRemarkFirstCommand));

        // same values -> returns true
        ClearRemarkCommand clearRemarkFirstCommandCopy = new ClearRemarkCommand(INDEX_FIRST_TUTEE);
        assertTrue(clearRemarkFirstCommand.equals(clearRemarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(clearRemarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(clearRemarkFirstCommand.equals(null));

        // different tutee -> returns false
        assertFalse(clearRemarkFirstCommand.equals(clearRemarkSecondCommand));
    }

}
