package seedu.address.logic.commands.PaymentCommand;

import seedu.address.commons.core.index.Index;

import java.time.LocalDate;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_RECEIVED_DATE;

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

    public PaymentReceiveCommand(Index targetIndex, LocalDate payByDate) {
        super(targetIndex);
        this.targetIndex = targetIndex;
        this.payByDate = payByDate;
    }
}
