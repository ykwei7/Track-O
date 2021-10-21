package seedu.address.logic.commands.PaymentCommand;

import seedu.address.commons.core.index.Index;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_DATE;

public class PaymentSetDateCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String COMMAND_DETAILS = "Sets the date specified after "
            + PREFIX_PAYMENT_DATE
            + " as the date to make payment by for specified tutee. ";

    public static final String MESSAGE_USAGE = COMMAND_DETAILS + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PAYMENT_DATE + "15-10-2021\n\n";

    private final Index targetIndex;

    public PaymentSetDateCommand(Index targetIndex) {
        super(targetIndex);
        this.targetIndex = targetIndex;
    }
}
