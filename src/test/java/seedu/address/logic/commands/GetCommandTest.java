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

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code GetCommand}.
 */
public class GetCommandTest {

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public GetCommandTest() throws ScheduleClashException {
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws ScheduleClashException {
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        GetCommand getCommand = new GetCommand(INDEX_FIRST_TUTEE);

        String expectedMessage = String.format(GetCommand.MESSAGE_GET_TUTEE_SUCCESS, retrievedTutee);

        ModelManager expectedModel = new ModelManager(model.getTrackO(), new UserPrefs());

        assertCommandSuccess(getCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTuteeList().size() + 1);
        GetCommand getCommand = new GetCommand(outOfBoundIndex);

        assertCommandFailure(getCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        GetCommand getFirstCommand = new GetCommand(INDEX_FIRST_TUTEE);
        GetCommand getSecondCommand = new GetCommand(INDEX_SECOND_TUTEE);

        // same object -> returns true
        assertTrue(getFirstCommand.equals(getFirstCommand));

        // same values -> returns true
        GetCommand getFirstCommandCopy = new GetCommand(INDEX_FIRST_TUTEE);
        assertTrue(getFirstCommand.equals(getFirstCommandCopy));

        // different types -> returns false
        assertFalse(getFirstCommand.equals(1));

        // null -> returns false
        assertFalse(getFirstCommand.equals(null));

        // different tutee -> returns false
        assertFalse(getFirstCommand.equals(getSecondCommand));
    }

}
