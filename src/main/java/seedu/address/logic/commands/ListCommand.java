package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;

import seedu.address.model.Model;

/**
 * Lists all tutees in Track-O to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tutees";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the current list of tutees.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
