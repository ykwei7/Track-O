package seedu.address.logic.commands.paymentcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_RECEIVED_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;
import static seedu.address.model.tutee.Payment.TODAY_DATE_AS_STRING;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Tutee;

public class PaymentReceiveCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String MESSAGE_USAGE = "Sets payment value owed by the tutee identified "
            + "by the index number used in the displayed tutee list to 0 and "
            + "has an optional date field to update the date to make next payment by. If no date is set, "
            + "the new date to make payment by is reset.\n"
            + "Required Parameters: TUTEE_INDEX (must be a positive integer)\n"
            + "Optional Parameters: PAY_BY_DATE (in the format of dd-mm-yyyy)\n"
            + "Example: payment 1 " + PREFIX_PAYMENT_RECEIVED_DATE + "15-10-2021\n\n";

    public static final String UPDATE_TUTEE_PAYMENT_SUCCESS = "Updated Payment details of %s:\n%s";

    public static final String MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE = "Payment value owed by tutee "
            + "is already 0 and date to make payment by had no change.";
    private final Index targetIndex;
    private final LocalDate newPayByDate;
    private final String zeroPaymentVal = "0.00";
    private final LocalDate nullPayByDate = null;


    /**
     * Creates a command to set the payment value owed by tutee to 0 and
     * has an optional field to set next date to make payment by.
     *
     * @param targetIndex Index of the tutee
     * @param newPayByDate Date to make next payment by
     */
    public PaymentReceiveCommand(Index targetIndex, LocalDate newPayByDate) {
        super(targetIndex);
        this.targetIndex = targetIndex;
        this.newPayByDate = newPayByDate;
    }

    /**
     * Creates a duplicate tutee to replace current tutee with the updated payment value set to 0
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
        Tutee editedTutee;

        if (zeroPaymentVal.equals(existingPaymentValue) && newPayByDate == null && existingPayByDate == null) {
            throw new CommandException(MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE);
        } else if (zeroPaymentVal.equals(existingPaymentValue) && newPayByDate == null) {
            editedTutee = createEditedPaymentDetailsTutee(tuteeToGet, zeroPaymentVal,
                    nullPayByDate, TODAY_DATE_AS_STRING);
        } else if (zeroPaymentVal.equals(existingPaymentValue) && newPayByDate.equals(existingPayByDate)) {
            throw new CommandException(MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE);
        } else if (newPayByDate == null && existingPayByDate != null) {
            editedTutee = createEditedPaymentDetailsTutee(tuteeToGet, zeroPaymentVal,
                    nullPayByDate, TODAY_DATE_AS_STRING);
        } else {
            editedTutee = createEditedPaymentDetailsTutee(tuteeToGet, zeroPaymentVal,
                    newPayByDate, TODAY_DATE_AS_STRING);
        }

        model.setTutee(tuteeToGet, editedTutee);
        model.updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);
        Payment newPaymentDetails = editedTutee.getPayment();

        return new CommandResult(String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, editedTutee.getName(), newPaymentDetails));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentReceiveCommand // instanceof handles nulls
                && targetIndex.equals(((PaymentReceiveCommand) other).targetIndex)
                && ((newPayByDate == null) || ((PaymentReceiveCommand) other).newPayByDate == null
                || (newPayByDate.equals((((PaymentReceiveCommand) other).newPayByDate))))); // state check
    }
}
