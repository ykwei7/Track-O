package seedu.address.logic.commands.paymentcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Tutee;



/**
 * Updates the payment value of the tutee to the new value inputted by the user.
 *
 * Payment value refers to the amount that the tutee owes the user.
 */
public class PaymentSetAmountCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String BASIC_USAGE = COMMAND_WORD + " TUTEE_INDEX "
            + PREFIX_PAYMENT_AMOUNT + "PAYMENT_AMOUNT\n";

    public static final String MESSAGE_USAGE = "Update payment value owed by the tutee identified "
            + "by the index number used in the displayed tutee list to new specified value.\n"
            + "Required Parameters: TUTEE_INDEX (must be a positive integer), "
            + "PAYMENT_VALUE (must be a positive value up to 2 decimal places)\n"
            + "Example: payment 1 " + PREFIX_PAYMENT_AMOUNT + "150\n\n";

    public static final String MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE = "Payment value provided is the same"
            + " as the existing payment value of tutee.";

    private final Index targetIndex;
    private final String paymentValueToSet;

    /**
     * Creates a command to modify the existing payment value owed by desired tutee.
     *
     * @param targetIndex Index of the tutee
     * @param paymentValueToSet Payment value to set to
     */
    public PaymentSetAmountCommand(Index targetIndex, String paymentValueToSet) {
        super(targetIndex);
        this.targetIndex = targetIndex;
        this.paymentValueToSet = paymentValueToSet;
    }

    /**
     * Creates a duplicate tutee with the updated payment value to replace the existing tutee
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
        Payment existingPayment = tuteeToGet.getPayment();
        String existingPaymentValue = existingPayment.getValue();
        LocalDate existingPayByDate = existingPayment.getPayByDate();
        Tutee editedTutee = createEditedPaymentDetailsTutee(tuteeToGet, paymentValueToSet, existingPayByDate, null);

        // If existing value is same as input value
        if (paymentValueToSet.equals(existingPaymentValue)) {
            throw new CommandException(MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE);
        }

        model.setTutee(tuteeToGet, editedTutee);
        model.updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);
        Payment newPaymentDetails = editedTutee.getPayment();

        return new CommandResult(String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, editedTutee.getName(), newPaymentDetails));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentSetAmountCommand // instanceof handles nulls
                && targetIndex.equals(((PaymentSetAmountCommand) other).targetIndex)
                && paymentValueToSet.equals((((PaymentSetAmountCommand) other).paymentValueToSet))); // state check
    }
}
