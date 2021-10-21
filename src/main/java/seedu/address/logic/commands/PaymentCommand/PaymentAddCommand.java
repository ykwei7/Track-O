package seedu.address.logic.commands.PaymentCommand;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tutee.Tutee;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;

public class PaymentAddCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String COMMAND_DETAILS = "Adds the cost of the lesson specified after "
            + PREFIX_LESSON
            + " to the lump sum of specified tutee. ";

    public static final String MESSAGE_USAGE = COMMAND_DETAILS + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LESSON + "1\n\n";

    public static final String ADD_TUTEE_PAYMENT_SUCCESS = "Payment details of %1$s:\n%s\n\n"
            + MESSAGE_USAGE + ALL_INSTRUCTIONS;

    private final Index targetIndex;
    private final String value;
    public PaymentAddCommand(Index targetIndex, String value) {
        super(targetIndex);
        this.targetIndex = targetIndex;
        this.value = value;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutee> lastShownList = model.getFilteredTuteeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
        }

        Tutee tuteeToGet = lastShownList.get(targetIndex.getZeroBased());

        Tutee editedTutee = editedPaymentDetailsTutee(tuteeToGet, value, tuteeToGet.getPayment().getPayByDate());

        return new CommandResult(String.format(ADD_TUTEE_PAYMENT_SUCCESS, editedTutee));
    }
}
