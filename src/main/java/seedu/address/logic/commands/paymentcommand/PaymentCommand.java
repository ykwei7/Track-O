package seedu.address.logic.commands.paymentcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_RECEIVED_DATE;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Address;
import seedu.address.model.tutee.Level;
import seedu.address.model.tutee.Name;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Phone;
import seedu.address.model.tutee.Remark;
import seedu.address.model.tutee.Tutee;

/**
 * Showcases the payment details of an existing tutee in Track-O and provides additional information on how to use
 * other features of the payment class.
 */
public class PaymentCommand extends Command {

    public static final String COMMAND_WORD = "payment";

    public static final String MESSAGE_DEFAULT_USAGE = COMMAND_WORD + ":\n"
            + "View payment details of the tutee identified "
            + "by the index number used in the displayed tutee list.\n"
            + "Required Parameters: TUTEE_INDEX (must be a positive integer)\n"
            + "Example: payment 1";

    public static final String ALL_INSTRUCTIONS = "Payment command has following functionalities and"
            + " is to only include up to 1 parameter.\n\n"
            + PaymentAddCommand.MESSAGE_USAGE
            + PaymentSetAmountCommand.MESSAGE_USAGE
            + PaymentSetDateCommand.MESSAGE_USAGE
            + PaymentReceiveCommand.MESSAGE_USAGE
            + "\n";

    public static final String MESSAGE_PAYMENT_MANAGEMENT_USAGE =
            COMMAND_WORD + " TUTEE_INDEX " + PREFIX_LESSON + "LESSON_INDEX\n"
            + COMMAND_WORD + " TUTEE_INDEX " + PREFIX_PAYMENT_AMOUNT + "PAYMENT_AMOUNT\n"
            + COMMAND_WORD + " TUTEE_INDEX " + PREFIX_PAYMENT_DATE + "PAYMENT_DATE\n"
            + COMMAND_WORD + " TUTEE_INDEX " + PREFIX_PAYMENT_RECEIVED_DATE + "[DATE_RECEIVED]\n\n"
            + "For more details on payment commands: payment";

    public static final String MESSAGE_USAGE_ALL = MESSAGE_DEFAULT_USAGE
            + "\n\n"
            + ALL_INSTRUCTIONS;

    public static final String SEPARATOR_TITLE = "Command usages to manage the payment details of tutee:\n";

    public static final String MESSAGE_VIEW_TUTEE_PAYMENT_SUCCESS = "Payment details of %s:\n%s\n\n"
            + SEPARATOR_TITLE
            + MESSAGE_PAYMENT_MANAGEMENT_USAGE;

    private final Index targetIndex;

    public PaymentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutee> lastShownList = model.getFilteredTuteeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
        }

        Tutee tuteeToGet = lastShownList.get(targetIndex.getZeroBased());

        //Edit this portion to link payment details instead of tutee
        String tuteePaymentDetails = String.format(MESSAGE_VIEW_TUTEE_PAYMENT_SUCCESS, tuteeToGet.getName(),
                tuteeToGet.getPayment());
        return new CommandResult(tuteePaymentDetails);
    }

    /**
     * Uses the information of an existing tutee and creates a new tutee with the updated payment details.
     *
     * @param tuteeToEdit Existing tutee
     * @param payment Payment amount to set
     * @param payByDate Date that tutee is to pay amount by
     * @return A new tutee object with the updated payment details
     */
    public static Tutee createEditedPaymentDetailsTutee(Tutee tuteeToEdit, String payment, LocalDate payByDate) {
        assert tuteeToEdit != null;

        Name updatedName = tuteeToEdit.getName();
        Phone updatedPhone = tuteeToEdit.getPhone();
        Level updatedLevel = tuteeToEdit.getLevel();
        Address updatedAddress = tuteeToEdit.getAddress();
        Payment existingPayment = tuteeToEdit.getPayment();
        Payment updatedPayment = new Payment(payment, payByDate);
        updatedPayment.copyPaymentHistory(existingPayment.paymentHistory);
        Remark updatedRemark = tuteeToEdit.getRemark(); // edit command does not allow editing remarks
        Set<Tag> updatedTags = tuteeToEdit.getTags();
        List<Lesson> updatedLessons = tuteeToEdit.getLessons(); // edit command does not allow editing lessons

        return new Tutee(updatedName, updatedPhone, updatedLevel, updatedAddress,
                updatedPayment, updatedRemark, updatedTags, updatedLessons);
    }

    /**
     * Uses the information of an existing tutee and creates a new tutee with the updated payment details.
     *
     * @param tuteeToEdit Existing tutee
     * @param payment Payment amount to set
     * @param payByDate Date that tutee is to pay amount by
     * @param lastPaidDate Date that tutee paid
     * @return
     */
    public static Tutee createEditedPaymentDetailsTutee(Tutee tuteeToEdit, String payment, LocalDate payByDate,
                                                        String lastPaidDate) {
        assert tuteeToEdit != null;

        Name updatedName = tuteeToEdit.getName();
        Phone updatedPhone = tuteeToEdit.getPhone();
        Level updatedLevel = tuteeToEdit.getLevel();
        Address updatedAddress = tuteeToEdit.getAddress();
        Payment updatedPayment = new Payment(payment, payByDate);
        updatedPayment.paymentHistory.add(lastPaidDate);
        Remark updatedRemark = tuteeToEdit.getRemark(); // edit command does not allow editing remarks
        Set<Tag> updatedTags = tuteeToEdit.getTags();
        List<Lesson> updatedLessons = tuteeToEdit.getLessons(); // edit command does not allow editing lessons

        return new Tutee(updatedName, updatedPhone, updatedLevel, updatedAddress,
                updatedPayment, updatedRemark, updatedTags, updatedLessons);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentCommand // instanceof handles nulls
                && targetIndex.equals(((PaymentCommand) other).targetIndex)); // state check
    }
}
