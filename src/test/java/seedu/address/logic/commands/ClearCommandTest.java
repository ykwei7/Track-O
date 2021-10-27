package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackO;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.ScheduleClashException;

public class ClearCommandTest {

    @Test
    public void execute_emptyTrackO_success() throws ScheduleClashException {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTrackO_success() throws ScheduleClashException {
        Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTrackO(), new UserPrefs());
        expectedModel.setTrackO(new TrackO());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
