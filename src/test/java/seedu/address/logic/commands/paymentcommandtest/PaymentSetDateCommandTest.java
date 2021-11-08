package seedu.address.logic.commands.paymentcommandtest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.paymentcommand.PaymentSetDateCommand.MESSAGE_NO_CHANGE_IN_PAYMENT_DATE;
import static seedu.address.logic.commands.paymentcommand.PaymentSetDateCommand.UPDATE_TUTEE_PAYMENT_SUCCESS;
import static seedu.address.logic.commands.paymentcommandtest.PaymentCommandTest.modifyPaymentOfTutee;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.paymentcommand.PaymentSetDateCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Tutee;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 *  * {@code PaymentReceiveCommand}.
 */
public class PaymentSetDateCommandTest {

    private static final String NEW_PAYBYDATE_VAL_STUB_1 = "15-10-2022";
    private static final String NEW_PAYBYDATE_VAL_STUB_2 = "20-10-2022";
    private static final LocalDate NULL_DATE = null;

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public PaymentSetDateCommandTest() throws ScheduleClashException {
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws ParseException {
        LocalDate newPayByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_1);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTuteeList().size() + 1);
        PaymentSetDateCommand paymentSetDateCommand = new PaymentSetDateCommand(outOfBoundIndex,
                newPayByDate);

        assertCommandFailure(paymentSetDateCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws ParseException {
        LocalDate newPayByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_1);
        LocalDate newPayByDate2 = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);
        PaymentSetDateCommand getFirstCommand = new PaymentSetDateCommand(INDEX_FIRST_TUTEE,
                newPayByDate);
        PaymentSetDateCommand getSecondCommand = new PaymentSetDateCommand(INDEX_SECOND_TUTEE,
                newPayByDate2);

        // same object -> returns true
        assertTrue(getFirstCommand.equals(getFirstCommand));

        // same values -> returns true
        PaymentSetDateCommand getFirstCommandCopy = new PaymentSetDateCommand(INDEX_FIRST_TUTEE,
                newPayByDate);
        assertTrue(getFirstCommand.equals(getFirstCommandCopy));

        // different types -> returns false
        assertFalse(getFirstCommand.equals(1));

        // null -> returns false
        assertFalse(getFirstCommand.equals(null));

        // different tutee -> returns false
        assertFalse(getFirstCommand.equals(getSecondCommand));
    }

    // Input date is same as existing date
    @Test
    public void execute_noChangeInPaymentDate_throwsCommandException() throws ParseException, ScheduleClashException {

        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        String retrievedPaymentVal = retrievedTuteePayment.getValue();
        LocalDate payByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);

        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, retrievedPaymentVal , payByDate, null);

        PaymentSetDateCommand paymentSetDateCommand = new PaymentSetDateCommand(INDEX_FIRST_TUTEE,
                payByDate);

        assertCommandFailure(paymentSetDateCommand, model, MESSAGE_NO_CHANGE_IN_PAYMENT_DATE);
    }

    // Tutee with null date to initialized date
    @Test
    public void execute_changeInPaymentDate_success() throws ParseException, ScheduleClashException {

        LocalDate newPayByDate2 = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);
        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        String retrievedPaymentVal = retrievedTuteePayment.getValue();

        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, retrievedPaymentVal, NULL_DATE, null);
        Model expectedModel = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, retrievedPaymentVal, newPayByDate2, null);
        PaymentSetDateCommand paymentSetDateCommand = new PaymentSetDateCommand(INDEX_FIRST_TUTEE,
                newPayByDate2);

        String successMsg = String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, retrievedTutee.getName(),
                new Payment(retrievedPaymentVal, newPayByDate2));

        CommandResult expectedMsg = new CommandResult(successMsg);

        assertCommandSuccess(paymentSetDateCommand, model, expectedMsg, expectedModel);
    }

    // Tutee with date initialized to new different date
    @Test
    public void execute_changeInPaymentDate2_success() throws ParseException, ScheduleClashException {

        LocalDate newPayByDate2 = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);
        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        String retrievedPaymentVal = retrievedTuteePayment.getValue();

        Model expectedModel = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, retrievedPaymentVal, newPayByDate2, null);
        PaymentSetDateCommand paymentSetDateCommand = new PaymentSetDateCommand(INDEX_FIRST_TUTEE,
                newPayByDate2);

        String successMsg = String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, retrievedTutee.getName(),
                new Payment(retrievedPaymentVal, newPayByDate2));

        CommandResult expectedMsg = new CommandResult(successMsg);

        assertCommandSuccess(paymentSetDateCommand, model, expectedMsg, expectedModel);
    }
}
