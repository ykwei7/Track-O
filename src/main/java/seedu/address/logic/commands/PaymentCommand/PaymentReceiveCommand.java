package seedu.address.logic.commands.PaymentCommand;

import seedu.address.commons.core.index.Index;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_RECEIVED_DATE;

public class PaymentReceiveCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String COMMAND_DETAILS = "Sets the lump sum to zero"
            + " and has an optional date specified after " + PREFIX_PAYMENT_RECEIVED_DATE
            + " that sets the date specified tutee has to make payment by. ";

    public static final String MESSAGE_USAGE = COMMAND_DETAILS + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PAYMENT_RECEIVED_DATE + "15-10-2021\n\n";

    private final Index targetIndex;

    public PaymentReceiveCommand(Index targetIndex) {
        super(targetIndex);
        this.targetIndex = targetIndex;
    }
}
