package seedu.address.logic.commands.paymentcommandtest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.paymentcommand.PaymentAddCommand.MESSAGE_LESSON_INDEX_OUT_OF_BOUNDS;
import static seedu.address.logic.commands.paymentcommand.PaymentAddCommand.addLessonCostToValue;
import static seedu.address.logic.commands.paymentcommand.PaymentCommand.UPDATE_TUTEE_PAYMENT_SUCCESS;
import static seedu.address.logic.commands.paymentcommandtest.PaymentCommandTest.modifyPaymentOfTutee;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TUTEE;
import static seedu.address.testutil.TypicalTutees.LESSON;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.paymentcommand.PaymentAddCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.TuteeBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 *  * {@code PaymentAddCommand}.
 */
public class PaymentAddCommandTest {

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public PaymentAddCommandTest() throws ScheduleClashException {
    }

    @Test
    public void execute_invalidLessonIndex_throwsCommandException() {
        int validIndex = model.getFilteredTuteeList().size() - 1;
        Index index = Index.fromZeroBased(validIndex);
        Tutee retrievedTutee = model.getFilteredTuteeList().get(validIndex);
        ArrayList<Lesson> lessons = new ArrayList<>(retrievedTutee.getLessons());

        Index outOfBoundIndex = Index.fromOneBased(lessons.size() + 1);
        PaymentAddCommand paymentAddCommand = new PaymentAddCommand(index,
                outOfBoundIndex);

        assertCommandFailure(paymentAddCommand, model, MESSAGE_LESSON_INDEX_OUT_OF_BOUNDS);
    }

    @Test
    public void equals() {
        PaymentAddCommand getFirstCommand = new PaymentAddCommand(INDEX_FIRST_TUTEE,
                INDEX_FIRST_LESSON);
        PaymentAddCommand getSecondCommand = new PaymentAddCommand(INDEX_SECOND_TUTEE,
                INDEX_SECOND_LESSON);

        // same object -> returns true
        assertTrue(getFirstCommand.equals(getFirstCommand));

        // same values -> returns true
        PaymentAddCommand getFirstCommandCopy = new PaymentAddCommand(INDEX_FIRST_TUTEE,
                INDEX_FIRST_LESSON);
        assertTrue(getFirstCommand.equals(getFirstCommandCopy));

        // different types -> returns false
        assertFalse(getFirstCommand.equals(1));

        // null -> returns false
        assertFalse(getFirstCommand.equals(null));

        // different tutee -> returns false
        assertFalse(getFirstCommand.equals(getSecondCommand));
    }

    @Test
    public void execute_validInput_success() throws CommandException, ScheduleClashException {

        model = new ModelManager(getTypicalTrackO(), new UserPrefs());
        Tutee thirdTutee = model.getFilteredTuteeList().get(INDEX_THIRD_TUTEE.getZeroBased());
        Index firstLessonIndex = Index.fromOneBased(1);

        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_THIRD_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        LocalDate existingPayByDate = retrievedTuteePayment.getPayByDate();

        PaymentAddCommand paymentAddCommand = new PaymentAddCommand(INDEX_THIRD_TUTEE,
                firstLessonIndex);

        String newPaymentVal = addLessonCostToValue(firstLessonIndex, thirdTutee);

        Model expectedModel = modifyPaymentOfTutee(INDEX_THIRD_TUTEE, newPaymentVal, existingPayByDate, null);

        String successMsg = String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, retrievedTutee.getName(),
                new Payment(newPaymentVal, existingPayByDate));

        CommandResult expectedMsg = new CommandResult(successMsg);

        assertCommandSuccess(paymentAddCommand, model, expectedMsg, expectedModel);
    }

    @Test
    public void addLessonCostToValue_invalidAmount_throwsCommandException() {
        Tutee testTuteeWithMaxPayment = new TuteeBuilder().withName("John").withPhone("94824422").withSchool("acsp")
                .withLevel("p2").withAddress("3rd street").withPayment(String.format("%.2f", Payment.MAXIMUM_AMOUNT),
                        LocalDate.of(2023, 10, 20)).withLesson(LESSON).build();
        Index firstLessonIndex = Index.fromOneBased(1);
        assertThrows(CommandException.class, () -> addLessonCostToValue(firstLessonIndex,
                testTuteeWithMaxPayment));
    }
}
