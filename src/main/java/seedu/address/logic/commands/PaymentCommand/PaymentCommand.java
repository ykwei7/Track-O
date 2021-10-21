package seedu.address.logic.commands.PaymentCommand;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_RECEIVED_DATE;

import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
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
 * Manages the payment details of an existing tutee in Track-O.
 */
public class PaymentCommand extends Command {

    public static final String COMMAND_WORD = "payment";

    public static final String MESSAGE_INDEX_USAGE = COMMAND_WORD + ": View payment details of the tutee identified "
            + "by the index number used in the displayed tutee list. "
            + "Required Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_USAGE = "Manage payment details of the tutee identified "
            + "by the index number used in the displayed tutee list. "
            + "Required Parameters: INDEX (must be a positive integer) "
            + "and strictly one of the following parameters:\n\n"
            + PREFIX_LESSON + "LESSON_INDEX\n"
            + PREFIX_PAYMENT_AMOUNT + "PAYMENT_AMOUNT\n"
            + PREFIX_PAYMENT_DATE + "PAYMENT_DATE\n"
            + PREFIX_PAYMENT_RECEIVED_DATE + "[DATE_RECEIVED]\n\n";

    public static final String ALL_INSTRUCTIONS = PaymentAddCommand.MESSAGE_USAGE
            + PaymentSetAmountCommand.MESSAGE_USAGE
            + PaymentSetDateCommand.MESSAGE_USAGE
            + PaymentReceiveCommand.MESSAGE_USAGE
            + "\n";

    public static final String SEPARATOR = "----------------HOW TO USE PAYMENT----------------\n";
    public static final String MESSAGE_VIEW_TUTEE_PAYMENT_SUCCESS = "Payment details of %s:\n%s\n\n"
            + SEPARATOR
            + MESSAGE_USAGE
            + ALL_INSTRUCTIONS;


    private final Index targetIndex;

    public PaymentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutee> lastShownList = model.getFilteredTuteeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
        }

        Tutee tuteeToGet = lastShownList.get(targetIndex.getZeroBased());

        //Edit this portion to link payment details instead of tutee
        String tuteePaymentDetails = String.format(MESSAGE_VIEW_TUTEE_PAYMENT_SUCCESS, tuteeToGet.getName()
                , tuteeToGet.getPayment());
        return new CommandResult(tuteePaymentDetails);
    }

    /**
     * Creates and returns a {@code Tutee} with the details of {@code tuteeToEdit}
     * edited with {@code editTuteeDescriptor}.
     */
    public Tutee editedPaymentDetailsTutee(Tutee tuteeToEdit, String payment, LocalDate payByDate) {
        assert tuteeToEdit != null;

        Name updatedName = tuteeToEdit.getName();
        Phone updatedPhone = tuteeToEdit.getPhone();
        Level updatedLevel = tuteeToEdit.getLevel();
        Address updatedAddress = tuteeToEdit.getAddress();
        Payment updatedPayment = new Payment(payment, payByDate);
        Remark updatedRemark = tuteeToEdit.getRemark(); // edit command does not allow editing remarks
        Set<Tag> updatedTags = tuteeToEdit.getTags();
        Set<Lesson> updatedLessons = tuteeToEdit.getLessons(); // edit command does not allow editing lessons

        return new Tutee(updatedName, updatedPhone, updatedLevel, updatedAddress,
                updatedPayment, updatedRemark, updatedTags, updatedLessons);
    }
}
