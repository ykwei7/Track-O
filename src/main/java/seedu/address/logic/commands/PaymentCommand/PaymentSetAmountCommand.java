package seedu.address.logic.commands.PaymentCommand;

import seedu.address.commons.core.index.Index;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;

public class PaymentSetAmountCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String COMMAND_DETAILS = "Sets the lump sum to the amount specified after "
            + PREFIX_PAYMENT_AMOUNT
            + " as new lump sum for specified tutee. ";

    public static final String MESSAGE_USAGE = COMMAND_DETAILS + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PAYMENT_AMOUNT + "150\n\n";

    private final Index targetIndex;

    public PaymentSetAmountCommand(Index targetIndex) {
        super(targetIndex);
        this.targetIndex = targetIndex;
    }
}
