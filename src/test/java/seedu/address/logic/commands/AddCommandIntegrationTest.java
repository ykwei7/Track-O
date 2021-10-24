package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.TuteeBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() throws ScheduleClashException {
        model = new ModelManager(getTypicalTrackO(), new UserPrefs());
    }

    @Test
    public void execute_newTutee_success() throws ScheduleClashException {
        Tutee validTutee = new TuteeBuilder().build();

        Model expectedModel = new ModelManager(model.getTrackO(), new UserPrefs());
        expectedModel.addTutee(validTutee);

        assertCommandSuccess(new AddCommand(validTutee), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validTutee), expectedModel);
    }

    @Test
    public void execute_duplicateTutee_throwsCommandException() {
        Tutee tuteeInList = model.getTrackO().getTuteeList().get(0);
        assertCommandFailure(new AddCommand(tuteeInList), model, AddCommand.MESSAGE_DUPLICATE_TUTEE);
    }

}
