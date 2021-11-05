package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tutee.Remark;
import seedu.address.model.tutee.Tutee;

/**
 * Changes the remark of an existing tutee in the tutee list.
 */
public class ClearRemarkCommand extends Command {

    public static final String COMMAND_WORD = "clearremark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all remarks from the tutee "
            + "identified by the index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Remark cleared from tutee: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Message has already been cleared!";

    private static final String EMPTY_REMARK = "-";

    private final Index targetIndex;

    /**
     * @param targetIndex of the tutee in the filtered tutee list to edit the remark
     */
    public ClearRemarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutee> lastShownList = model.getFilteredTuteeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
        }

        Tutee tuteeToEdit = lastShownList.get(targetIndex.getZeroBased());

        Tutee editedTutee = new Tutee(tuteeToEdit.getName(), tuteeToEdit.getPhone(), tuteeToEdit.getSchool(),
                    tuteeToEdit.getLevel(), tuteeToEdit.getAddress(), tuteeToEdit.getPayment(),
                    new Remark(EMPTY_REMARK), tuteeToEdit.getTags(), tuteeToEdit.getLessons());

        model.setTutee(tuteeToEdit, editedTutee);
        model.updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);

        if (tuteeToEdit.getRemark().value.equals(EMPTY_REMARK)) {
            throw new CommandException(String.format(MESSAGE_NOT_EDITED, editedTutee));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, editedTutee));
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearRemarkCommand // instanceof handles nulls
                && targetIndex.equals(((ClearRemarkCommand) other).targetIndex)); // state check
    }
}
