package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
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
import seedu.address.model.TrackO;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.tutee.Remark;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.TuteeBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private static final String REMARK_STUB = "Some remark";

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public RemarkCommandTest() throws ScheduleClashException {
    }

    @Test
    public void execute_addRemarkUnfilteredList_success() throws ScheduleClashException {
        Tutee firstTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Tutee editedTutee = new TuteeBuilder(firstTutee).withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_TUTEE, new Remark(editedTutee.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedTutee);

        Model expectedModel = new ModelManager(new TrackO(model.getTrackO()), new UserPrefs());
        expectedModel.setTutee(firstTutee, editedTutee);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addRemarkUnfilteredListExistingRemark_success() throws ScheduleClashException {
        Tutee firstTutee = model.getFilteredTuteeList().get(INDEX_SECOND_TUTEE.getZeroBased());
        Tutee editedTutee = new TuteeBuilder(firstTutee).build();
        String existingRemark = firstTutee.getRemark().value + "\n";
        Tutee furtherEditedTutee = new TuteeBuilder(firstTutee).withRemark(existingRemark + REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_SECOND_TUTEE, new Remark(REMARK_STUB));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, furtherEditedTutee);

        Model expectedModel = new ModelManager(new TrackO(model.getTrackO()), new UserPrefs());
        expectedModel.setTutee(firstTutee, editedTutee);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws ScheduleClashException {
        showTuteeAtIndex(model, INDEX_FIRST_TUTEE);

        Tutee firstTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Tutee editedTutee = new TuteeBuilder(model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased()))
                .withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_TUTEE, new Remark(editedTutee.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedTutee);

        Model expectedModel = new ModelManager(new TrackO(model.getTrackO()), new UserPrefs());
        expectedModel.setTutee(firstTutee, editedTutee);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTuteeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTuteeList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of Track-O
     */
    @Test
    public void execute_invalidTuteeIndexFilteredList_failure() {
        showTuteeAtIndex(model, INDEX_FIRST_TUTEE);
        Index outOfBoundIndex = INDEX_SECOND_TUTEE;
        // ensures that outOfBoundIndex is still in bounds of Track-O list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTrackO().getTuteeList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_TUTEE,
                new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_TUTEE,
                new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_TUTEE,
                new Remark(VALID_REMARK_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_TUTEE,
                new Remark(VALID_REMARK_BOB))));
    }

}
