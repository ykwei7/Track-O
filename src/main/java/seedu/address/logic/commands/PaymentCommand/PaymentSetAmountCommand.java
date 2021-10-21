package seedu.address.logic.commands.PaymentCommand;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Tutee;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;

public class PaymentSetAmountCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String COMMAND_DETAILS = "Sets the lump sum to the amount specified after "
            + PREFIX_PAYMENT_AMOUNT
            + " as new lump sum for specified tutee. ";

    public static final String MESSAGE_USAGE = COMMAND_DETAILS + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PAYMENT_AMOUNT + "150\n\n";

    public static final String UPDATE_TUTEE_PAYMENT_SUCCESS = "Updated Payment details of %s:\n%s";

    private final Index targetIndex;
    private final String value;

    public PaymentSetAmountCommand(Index targetIndex, String value) {
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
        model.setTutee(tuteeToGet, editedTutee);
        model.updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);
        Payment paymentDetails = editedTutee.getPayment();

        return new CommandResult(String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, editedTutee.getName(), paymentDetails));
    }
}
