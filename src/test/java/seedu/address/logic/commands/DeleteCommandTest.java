package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTuteeAtIndex;
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

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public DeleteCommandTest() throws ScheduleClashException {
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws ScheduleClashException {
        Tutee tuteeToDelete = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_TUTEE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TUTEE_SUCCESS, tuteeToDelete);

        ModelManager expectedModel = new ModelManager(model.getTrackO(), new UserPrefs());
        expectedModel.deleteTutee(tuteeToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTuteeList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws ScheduleClashException {
        showTuteeAtIndex(model, INDEX_FIRST_TUTEE);

        Tutee tuteeToDelete = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_TUTEE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TUTEE_SUCCESS, tuteeToDelete);

        Model expectedModel = new ModelManager(model.getTrackO(), new UserPrefs());
        expectedModel.deleteTutee(tuteeToDelete);
        showNoTutee(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTuteeAtIndex(model, INDEX_FIRST_TUTEE);

        Index outOfBoundIndex = INDEX_SECOND_TUTEE;
        // ensures that outOfBoundIndex is still in bounds of Track-O list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTrackO().getTuteeList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_TUTEE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_TUTEE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_TUTEE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different tutee -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTutee(Model model) {
        model.updateFilteredTuteeList(p -> false);

        assertTrue(model.getFilteredTuteeList().isEmpty());
    }
}
