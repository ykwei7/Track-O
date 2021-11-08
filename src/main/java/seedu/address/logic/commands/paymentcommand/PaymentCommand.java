package seedu.address.logic.commands.paymentcommand;

import static java.util.Objects.requireNonNull;

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
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Tutee;

/**
 * Showcases the payment details of an existing tutee in Track-O and provides additional information on how to use
 * other features of the payment class.
 */
public class PaymentCommand extends Command {

    public static final String COMMAND_WORD = "payment";

    // Default usage of payment allows you to view payment details of current tutee
    public static final String MESSAGE_DEFAULT_USAGE = COMMAND_WORD
            + ": View payment details of the tutee identified by the index number used in the displayed tutee list.\n"
            + "Required Parameters: TUTEE_INDEX (must be a positive integer)\n" + "Example: payment 1\n\n";

    // Basic usage on the extensions for payment command
    public static final String MESSAGE_BASIC_USAGE_ALL = PaymentAddCommand.BASIC_USAGE
                    + PaymentSetAmountCommand.BASIC_USAGE
                    + PaymentSetDateCommand.BASIC_USAGE
                    + PaymentReceiveCommand.BASIC_USAGE
                    + "\n"
                    + "For more details on payment commands: payment";

    // Elaborated usage on the extensions for payment command
    public static final String MESSAGE_USAGE_ALL = "Payment command has the following functionalities and"
            + " is to only include up to 1 parameter:\n\n"
            + MESSAGE_DEFAULT_USAGE
            + PaymentAddCommand.MESSAGE_USAGE
            + PaymentSetAmountCommand.MESSAGE_USAGE
            + PaymentSetDateCommand.MESSAGE_USAGE
            + PaymentReceiveCommand.MESSAGE_USAGE;

    // Separator to showcase basic extensions on payment command
    public static final String SEPARATOR_TITLE = "Command usages to manage the payment details of tutee:\n";

    // Final payment details formatting including basic payment extension command usage
    public static final String MESSAGE_VIEW_TUTEE_PAYMENT_SUCCESS = "Payment details of %s:\n%s%s\n"
            + SEPARATOR_TITLE
            + MESSAGE_BASIC_USAGE_ALL;

    public static final String UPDATE_TUTEE_PAYMENT_SUCCESS = "Updated Payment details of %s:\n%s";

    private final Index targetIndex;

    public PaymentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Gets payment details and lesson fee details
     * @param tutee Updated payment details of tutee
     * @return
     */
    public static String getPaymentDetailsMessage(Tutee tutee) {
        Name name = tutee.getName();
        Payment payment = tutee.getPayment();
        List<Lesson> lessons = tutee.getLessons();
        String lessonsToString = Lesson.lessonListToString(lessons);
        String paymentDetails = String.format(MESSAGE_VIEW_TUTEE_PAYMENT_SUCCESS, name,
                payment, lessonsToString);

        return paymentDetails;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutee> lastShownList = model.getFilteredTuteeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
        }

        Tutee tuteeToGet = lastShownList.get(targetIndex.getZeroBased());

        String tuteePaymentDetails = getPaymentDetailsMessage(tuteeToGet);
        return new CommandResult(tuteePaymentDetails);
    }

    /**
     * Uses the information of an existing tutee and creates a new tutee with the updated payment details and
     * updates the last paid date of tutee (only when the receive command is used). In cases where other commands are
     * used, lastPaidDate will be null.
     *
     * @param tuteeToEdit Existing tutee
     * @param payment Payment amount to set
     * @param payByDate Date that tutee is to pay amount by
     * @param lastPaidDate Date that tutee paid, to be only initialized when receive command is used
     * @return
     */
    public static Tutee createEditedPaymentDetailsTutee(Tutee tuteeToEdit, String payment, LocalDate payByDate,
                                                        String lastPaidDate) {
        assert tuteeToEdit != null;

        Name updatedName = tuteeToEdit.getName();
        Phone updatedPhone = tuteeToEdit.getPhone();
        School updatedSchool = tuteeToEdit.getSchool();
        Level updatedLevel = tuteeToEdit.getLevel();
        Address updatedAddress = tuteeToEdit.getAddress();
        Payment existingPayment = tuteeToEdit.getPayment();
        Payment updatedPayment = new Payment(payment, payByDate);
        if (lastPaidDate != null) {
            updatedPayment.paymentHistory.add(lastPaidDate);
        } else {
            List<String> existingPaymentHist = existingPayment.paymentHistory;
            updatedPayment.copyPaymentHistory(existingPaymentHist);
        }
        Remark updatedRemark = tuteeToEdit.getRemark(); // edit command does not allow editing remarks
        Set<Tag> updatedTags = tuteeToEdit.getTags();
        List<Lesson> updatedLessons = tuteeToEdit.getLessons(); // edit command does not allow editing lessons

        return new Tutee(updatedName, updatedPhone, updatedSchool, updatedLevel, updatedAddress,
                updatedPayment, updatedRemark, updatedTags, updatedLessons);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentCommand // instanceof handles nulls
                && targetIndex.equals(((PaymentCommand) other).targetIndex)); // state check
    }
}
