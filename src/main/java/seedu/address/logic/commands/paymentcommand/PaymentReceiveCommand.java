package seedu.address.logic.commands.paymentcommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_RECEIVED_DATE;

import java.time.LocalDate;

import seedu.address.commons.core.index.Index;

public class PaymentReceiveCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String MESSAGE_USAGE = "Sets payment value owed by the tutee identified "
            + "by the index number used in the displayed tutee list to 0 and "
            + "has an optional date field to update the date to make next payment by.\n"
            + "Required Parameters: TUTEE_INDEX (must be a positive integer)\n"
            + "Optional Parameters: PAY_BY_DATE (in the format of dd-mm-yyyy)\n"
            + "Example: payment 1 " + PREFIX_PAYMENT_RECEIVED_DATE + "15-10-2021\n\n";

    private final Index targetIndex;
    private final LocalDate payByDate;

    /**
     * Creates a command to set the payment value owed by tutee to 0 and
     * has an optional field to set next date to make payment by.
     *
     * @param targetIndex Index of the tutee
     * @param payByDate Date to make next payment by
     */
    public PaymentReceiveCommand(Index targetIndex, LocalDate payByDate) {
        super(targetIndex);
        this.targetIndex = targetIndex;
        this.payByDate = payByDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentReceiveCommand // instanceof handles nulls
                && targetIndex.equals(((PaymentReceiveCommand) other).targetIndex)
                && payByDate.equals((((PaymentReceiveCommand) other).payByDate))); // state check
    }
}
