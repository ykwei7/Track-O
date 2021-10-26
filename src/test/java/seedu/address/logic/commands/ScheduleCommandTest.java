package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.ScheduleClashException;

public class ScheduleCommandTest {

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public ScheduleCommandTest() throws ScheduleClashException {
    }

    @Test
    public void execute_schedule_success() throws ScheduleClashException {
        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SUCCESS, model.getSchedule());
        Model expectedModel = new ModelManager(model.getTrackO(), new UserPrefs());
        assertCommandSuccess(new ScheduleCommand(), model, expectedMessage, expectedModel);
    }
}
