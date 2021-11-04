package seedu.address.logic.commands.paymentcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_DATE;
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


public class PaymentSetDateCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String BASIC_USAGE = COMMAND_WORD + " TUTEE_INDEX " + PREFIX_PAYMENT_DATE + "PAYMENT_DATE\n";

    public static final String MESSAGE_USAGE = "Update the date to pay by for tutee identified "
            + "by the index number used in the displayed tutee list to new specified date.\n"
            + "Required Parameters: TUTEE_INDEX (must be a positive integer), "
            + "PAY_BY_DATE (in the format of dd-mm-yyyy)\n"
            + "Example: payment 1 " + PREFIX_PAYMENT_DATE + "15-10-2021\n\n";

    public static final String MESSAGE_NO_CHANGE_IN_PAYMENT_DATE = "Payment date owed by tutee "
            + "is the same as existing payment date";

    private final Index targetIndex;
    private final LocalDate newPayByDate;

    /**
     * Creates a command to modify the date to make next payment by
     * of desired tutee.
     *
     * @param targetIndex Index of the tutee
     * @param newPayByDate Date to make next payment by
     */
    public PaymentSetDateCommand(Index targetIndex, LocalDate newPayByDate) {
        super(targetIndex);
        requireNonNull(newPayByDate);
        this.targetIndex = targetIndex;
        this.newPayByDate = newPayByDate;
    }

    /**
     * Creates a duplicate tutee with the updated pay-by-date to replace the existing tutee
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

        // If existing pay by value is same as input date value
        if (newPayByDate.equals(existingPayByDate)) {
            throw new CommandException(MESSAGE_NO_CHANGE_IN_PAYMENT_DATE);
        }

        Tutee editedTutee = createEditedPaymentDetailsTutee(tuteeToGet, existingPaymentValue, newPayByDate, null);

        model.setTutee(tuteeToGet, editedTutee);
        model.updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);
        Payment newPaymentDetails = editedTutee.getPayment();

        return new CommandResult(String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, editedTutee.getName(), newPaymentDetails));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentSetDateCommand // instanceof handles nulls
                && targetIndex.equals(((PaymentSetDateCommand) other).targetIndex)
                && newPayByDate.equals((((PaymentSetDateCommand) other).newPayByDate))); // state check
    }
}
