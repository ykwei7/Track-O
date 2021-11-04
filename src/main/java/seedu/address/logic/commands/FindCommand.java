package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.tutee.CollectivePredicate;

/**
 * Finds and lists all tutees in Track-O whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tutees who satisfies the criteria specified "
            + "and displays them as a list with index numbers. "
            + "You can include multiple keywords for name and subject,"
            + " and the tutees found must satisfy all the keywords.\n"
            + "Parameters: [l/LEVEL] [n/...NAME] [subject/...SUBJECT] [overdue/yes or no]\n"
            + "Example: " + COMMAND_WORD + " l/p5 subject/math chinese";

    public static final String MESSAGE_LEVEL_CONSTRAINT = "Find by level requires 1 level keyword to search for.";

    private final CollectivePredicate predicate;

    public FindCommand(CollectivePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTuteeList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TUTEES_LISTED_OVERVIEW, model.getFilteredTuteeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
