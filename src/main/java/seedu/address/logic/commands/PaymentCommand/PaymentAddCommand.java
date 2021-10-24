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
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;

public class PaymentAddCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String MESSAGE_USAGE = "Obtains tutee identified "
            + "by the index number used in the displayed tutee list and lesson identified in tutee's lesson list. "
            + "Fees of the indexed lesson are then added to the payment value owed by tutee.\n"
            + "Required Parameters: TUTEE_INDEX (must be a positive integer), "
            + "LESSON_INDEX (must be a positive integer)\n"
            + "Example: payment 1 " + PREFIX_LESSON + "1\n\n";

    public static final String ADD_TUTEE_PAYMENT_SUCCESS = "Payment details of %1$s:\n%s\n\n"
            + MESSAGE_USAGE + ALL_INSTRUCTIONS;

    private final Index targetIndex;
    private final String paymentValue;

    public PaymentAddCommand(Index targetIndex, String paymentValue) {
        super(targetIndex);
        this.targetIndex = targetIndex;
        this.paymentValue = paymentValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentAddCommand // instanceof handles nulls
                && targetIndex.equals(((PaymentAddCommand) other).targetIndex)
                && paymentValue.equals((((PaymentAddCommand) other).paymentValue))); // state check
    }
}
