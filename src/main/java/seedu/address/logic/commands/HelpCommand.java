package seedu.address.logic.commands;

import seedu.address.logic.commands.paymentcommand.PaymentCommand;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Here are the usages of the commands:\n\n"
            + AddCommand.MESSAGE_USAGE + "\n\n"
            + DeleteCommand.MESSAGE_USAGE + "\n\n"
            + EditCommand.MESSAGE_USAGE + "\n\n"
            + FindCommand.MESSAGE_USAGE + "\n\n"
            + ListCommand.MESSAGE_USAGE + "\n\n"
            + GetCommand.MESSAGE_USAGE + "\n\n"
            + RemarkCommand.MESSAGE_USAGE + "\n\n"
            + ClearRemarkCommand.MESSAGE_USAGE + "\n\n"
            + ScheduleCommand.MESSAGE_USAGE + "\n\n"
            + AddLessonCommand.MESSAGE_USAGE + "\n\n"
            + DeleteLessonCommand.MESSAGE_USAGE + "\n\n"
            + PaymentCommand.MESSAGE_USAGE_ALL
            + ClearCommand.MESSAGE_USAGE + "\n\n"
            + ExitCommand.MESSAGE_USAGE;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
