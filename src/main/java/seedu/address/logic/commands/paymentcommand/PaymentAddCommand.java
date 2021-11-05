package seedu.address.logic.commands.paymentcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Tutee;

public class PaymentAddCommand extends PaymentCommand {

    public static final String COMMAND_WORD = "payment";

    public static final String BASIC_USAGE = COMMAND_WORD + " TUTEE_INDEX " + PREFIX_LESSON + "LESSON_INDEX\n";

    public static final String MESSAGE_USAGE = "Obtains tutee identified "
            + "by the index number used in the displayed tutee list and lesson identified in tutee's lesson list. "
            + "Fees of the indexed lesson are then added to the payment value owed by tutee.\n"
            + "Required Parameters: TUTEE_INDEX (must be a positive integer), "
            + "LESSON_INDEX (must be a positive integer)\n"
            + "Example: payment 1 " + PREFIX_LESSON + "1\n\n";

    public static final String UPDATE_TUTEE_PAYMENT_SUCCESS = "Updated Payment details of %s:\n%s";

    public static final String MESSAGE_LESSON_INDEX_OUT_OF_BOUNDS = "Lesson index provided is invalid.";

    public static final String MESSAGE_AMOUNT_EXCEED_MAXIMUM = "Adding this lesson's fees is not allowed."
            + "\n(Payment value will exceed $100,000)";

    private final Index targetIndex;
    private final Index lessonIndex;

    /**
     * Creates a command to add cost of the lesson to existing payment value of tutee.
     *
     * @param targetIndex Index of the tutee desired
     * @param lessonIndex Index of the lesson for its cost to be added to existing payment value
     */
    public PaymentAddCommand(Index targetIndex, Index lessonIndex) {
        super(targetIndex);
        this.targetIndex = targetIndex;
        this.lessonIndex = lessonIndex;
    }

    /**
     * Obtains the lesson fees and adds it to existing payment value.
     *
     * @param lessonIndex Index of lesson in tutee's lesson list
     * @param tutee Current tutee
     * @return New payment value owed by tutee
     * @throws CommandException If an error occurs during command execution.
     */
    public static String addLessonCostToValue(Index lessonIndex, Tutee tutee) throws CommandException {

        Payment existingPayment = tutee.getPayment();
        String existingPaymentValue = existingPayment.getValue();

        List<Lesson> lessonList = tutee.getLessons();

        if (lessonIndex.getZeroBased() >= lessonList.size()) {
            throw new CommandException(MESSAGE_LESSON_INDEX_OUT_OF_BOUNDS);
        }

        // Gets the indexed lesson in tutee's lesson list
        Lesson lessonRetrieved = lessonList.get(lessonIndex.getZeroBased());
        Double lessonCost = lessonRetrieved.getCost();
        Double updatedPaymentVal = Double.parseDouble(existingPaymentValue) + lessonCost;
        String updatedPaymentAsString = String.format("%.2f", updatedPaymentVal);

        if (!Payment.isValidPaymentAmount(updatedPaymentAsString)) {
            throw new CommandException(MESSAGE_AMOUNT_EXCEED_MAXIMUM);
        }

        return updatedPaymentAsString;
    }


    /**
     * Adds the fees of a lesson to tutee's existing fees.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Gets the tutee at desired index
        requireNonNull(model);
        List<Tutee> lastShownList = model.getFilteredTuteeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
        }

        Tutee tuteeToGet = lastShownList.get(targetIndex.getZeroBased());
        Payment existingPayment = tuteeToGet.getPayment();
        LocalDate existingPayByDate = existingPayment.getPayByDate();

        String updatedPaymentAsString = addLessonCostToValue(lessonIndex, tuteeToGet);

        Tutee editedTutee = createEditedPaymentDetailsTutee(tuteeToGet, updatedPaymentAsString, existingPayByDate,
                null);

        model.setTutee(tuteeToGet, editedTutee);
        model.updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);
        Payment newPaymentDetails = editedTutee.getPayment();

        return new CommandResult(String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, editedTutee.getName(), newPaymentDetails));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaymentAddCommand // instanceof handles nulls
                && targetIndex.equals(((PaymentAddCommand) other).targetIndex)
                && lessonIndex.equals((((PaymentAddCommand) other).lessonIndex))); // state check
    }
}
