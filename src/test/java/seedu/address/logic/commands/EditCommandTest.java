package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PRACTICAL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTuteeAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditTuteeDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackO;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.EditTuteeDescriptorBuilder;
import seedu.address.testutil.TuteeBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public EditCommandTest() throws ScheduleClashException {
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws ScheduleClashException {
        Tutee editedTutee = new TuteeBuilder().build();
        EditTuteeDescriptor descriptor = new EditTuteeDescriptorBuilder(editedTutee).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TUTEE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TUTEE_SUCCESS, editedTutee);

        Model expectedModel = new ModelManager(new TrackO(model.getTrackO()), new UserPrefs());
        expectedModel.setTutee(model.getFilteredTuteeList().get(0), editedTutee);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws ScheduleClashException {
        Index indexLastTutee = Index.fromOneBased(model.getFilteredTuteeList().size());
        Tutee lastTutee = model.getFilteredTuteeList().get(indexLastTutee.getZeroBased());

        TuteeBuilder tuteeInList = new TuteeBuilder(lastTutee);
        Tutee editedTutee = tuteeInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_PRACTICAL).build();

        EditTuteeDescriptor descriptor = new EditTuteeDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_PRACTICAL).build();
        EditCommand editCommand = new EditCommand(indexLastTutee, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TUTEE_SUCCESS, editedTutee);

        Model expectedModel = new ModelManager(new TrackO(model.getTrackO()), new UserPrefs());
        expectedModel.setTutee(lastTutee, editedTutee);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws ScheduleClashException {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TUTEE, new EditTuteeDescriptor());
        Tutee editedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TUTEE_SUCCESS, editedTutee);

        Model expectedModel = new ModelManager(new TrackO(model.getTrackO()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws ScheduleClashException {
        showTuteeAtIndex(model, INDEX_FIRST_TUTEE);

        Tutee tuteeInFilteredList = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Tutee editedTutee = new TuteeBuilder(tuteeInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TUTEE,
                new EditTuteeDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TUTEE_SUCCESS, editedTutee);

        Model expectedModel = new ModelManager(new TrackO(model.getTrackO()), new UserPrefs());
        expectedModel.setTutee(model.getFilteredTuteeList().get(0), editedTutee);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTuteeUnfilteredList_failure() {
        Tutee firstTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        EditTuteeDescriptor descriptor = new EditTuteeDescriptorBuilder(firstTutee).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_TUTEE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TUTEE);
    }

    @Test
    public void execute_duplicateTuteeFilteredList_failure() {
        showTuteeAtIndex(model, INDEX_FIRST_TUTEE);

        // edit tutee in filtered list into a duplicate in Track-O
        Tutee tuteeInList = model.getTrackO().getTuteeList().get(INDEX_SECOND_TUTEE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TUTEE,
                new EditTuteeDescriptorBuilder(tuteeInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TUTEE);
    }

    @Test
    public void execute_invalidTuteeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTuteeList().size() + 1);
        EditCommand.EditTuteeDescriptor descriptor = new EditTuteeDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
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

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTuteeDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_TUTEE, DESC_AMY);

        // same values -> returns true
        EditTuteeDescriptor copyDescriptor = new EditTuteeDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_TUTEE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_TUTEE, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_TUTEE, DESC_BOB)));
    }

}
