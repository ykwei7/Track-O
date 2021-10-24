package seedu.address.logic.commands.PaymentCommand;

import seedu.address.commons.core.index.Index;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_DATE;

public class PaymentSetDateCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String MESSAGE_USAGE = "Update the date to pay by for tutee identified "
            + "by the index number used in the displayed tutee list to new specified date.\n"
            + "Required Parameters: TUTEE_INDEX (must be a positive integer), "
            + "PAY_BY_DATE (in the format of dd-mm-yyyy)\n"
            + "Example: payment 1 " + PREFIX_PAYMENT_DATE + "15-10-2021\n\n";

    private final Index targetIndex;
    private final LocalDate payByDate;
    public PaymentSetDateCommand(Index targetIndex, LocalDate payByDate) {
        super(targetIndex);
        requireNonNull(payByDate);
        this.targetIndex = targetIndex;
        this.payByDate = payByDate;
    }
}
