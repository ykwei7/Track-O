package seedu.address.logic.commands.paymentcommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;

import seedu.address.commons.core.index.Index;

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
    private final String lessonIndex;

    /**
     * Creates a command to add cost of the lesson to existing payment value of tutee.
     *
     * @param targetIndex Index of the tutee desired
     * @param lessonIndex Index of the lesson for its cost to be added to existing payment value
     */
    public PaymentAddCommand(Index targetIndex, String lessonIndex) {
        super(targetIndex);
        this.targetIndex = targetIndex;
        this.lessonIndex = lessonIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentAddCommand // instanceof handles nulls
                && targetIndex.equals(((PaymentAddCommand) other).targetIndex)
                && lessonIndex.equals((((PaymentAddCommand) other).lessonIndex))); // state check
    }
}
