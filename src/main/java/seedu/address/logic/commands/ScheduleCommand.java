package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.Schedule;

/**
 * Lists the user's schedule.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_SUCCESS = "Here is your schedule for the week: \n%1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows your schedule for the week.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Schedule schedule = model.getSchedule();
        return new CommandResult(String.format(MESSAGE_SUCCESS, schedule));
    }
}
