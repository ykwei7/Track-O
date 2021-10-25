package seedu.address.logic.commands.paymentcommandtest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.paymentcommand.PaymentAddCommand.MESSAGE_LESSON_INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.paymentcommand.PaymentAddCommand;
import seedu.address.logic.commands.paymentcommand.PaymentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tutee.Tutee;

public class PaymentAddCommandTest {

    private static final String NEW_PAYMENT_VAL_STUB_1 = "100";
    private static final String NEW_PAYMENT_VAL_STUB_2 = "200";
    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    private Model modifyPaymentOfTutee(Index index, String newPaymentValue,
                                       LocalDate newPayByDate) {
        Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());
        Tutee retrievedTutee = model.getFilteredTuteeList().get(index.getZeroBased());
        Tutee editedTutee = PaymentCommand.createEditedPaymentDetailsTutee(retrievedTutee, newPaymentValue,
                newPayByDate);
        model.setTutee(retrievedTutee, editedTutee);
        return model;
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

    // To be completed once lesson cost calculation is finalized
    /*@Test
    public void execute_changeInPayment_success() throws CommandException {

        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        LocalDate payByDate = retrievedTuteePayment.getPayByDate();
        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, NEW_PAYMENT_VAL_STUB_1, payByDate);

        PaymentAddCommand paymentAddCommand = new PaymentAddCommand(INDEX_FIRST_TUTEE,
                NEW_PAYMENT_VAL_STUB_2);

        paymentAddCommand.execute(model);
        Tutee expectedTutee = PaymentCommand.editedPaymentDetailsTutee(retrievedTutee, NEW_PAYMENT_VAL_STUB_2,
                payByDate);
        Tutee actualTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        assertEquals(expectedTutee.getPayment(), actualTutee.getPayment());
    }*/
}
