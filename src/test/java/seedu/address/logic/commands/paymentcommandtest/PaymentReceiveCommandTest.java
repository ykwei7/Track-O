package seedu.address.logic.commands.paymentcommandtest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.paymentcommand.PaymentCommand.UPDATE_TUTEE_PAYMENT_SUCCESS;
import static seedu.address.logic.commands.paymentcommand.PaymentReceiveCommand.MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE;
import static seedu.address.logic.commands.paymentcommandtest.PaymentCommandTest.modifyPaymentOfTutee;
import static seedu.address.model.tutee.Payment.TODAY_DATE_AS_STRING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.paymentcommand.PaymentReceiveCommand;
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
public class PaymentReceiveCommandTest {

    private static final String ZERO_PAYMENT_VAL_STUB = "0.00";
    private static final String NEW_PAYMENT_VAL_STUB_1 = "100";
    private static final String NEW_PAYBYDATE_VAL_STUB_1 = "15-10-2022";
    private static final String NEW_PAYBYDATE_VAL_STUB_2 = "20-10-2022";
    private static final List<String> TODAY_DATE_AS_LIST = Arrays.asList("Never", TODAY_DATE_AS_STRING);
    private static final LocalDate NULL_DATE = null;

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public PaymentReceiveCommandTest() throws ScheduleClashException {
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws ParseException {

        LocalDate newPayByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_1);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTuteeList().size() + 1);
        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(outOfBoundIndex,
                newPayByDate);

        assertCommandFailure(paymentReceiveCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws ParseException {
        LocalDate newPayByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_1);
        LocalDate newPayByDate2 = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);
        PaymentReceiveCommand getFirstCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                newPayByDate);
        PaymentReceiveCommand getSecondCommand = new PaymentReceiveCommand(INDEX_SECOND_TUTEE,
                newPayByDate2);

        // same object -> returns true
        assertTrue(getFirstCommand.equals(getFirstCommand));

        // same values -> returns true
        PaymentReceiveCommand getFirstCommandCopy = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                newPayByDate);
        assertTrue(getFirstCommand.equals(getFirstCommandCopy));

        // different types -> returns false
        assertFalse(getFirstCommand.equals(1));

        // null -> returns false
        assertFalse(getFirstCommand.equals(null));

        // different tutee -> returns false
        assertFalse(getFirstCommand.equals(getSecondCommand));
    }

    // Initial tutee has (0, null) and receive command is run without date
    @Test
    public void execute_noChangeInPaymentVal_throwsCommandException() throws ScheduleClashException {
        // Creates tutee with specified payment details
        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, ZERO_PAYMENT_VAL_STUB, NULL_DATE, TODAY_DATE_AS_STRING);

        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                NULL_DATE);

        assertCommandFailure(paymentReceiveCommand, model, MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE);
    }

    // Initial tutee has (0, date1) and receive command is run with date1
    @Test
    public void execute_sameDatesGiven_throwsCommandException() throws ScheduleClashException,
            ParseException {
        // Creates model with specified payment details
        LocalDate samePayByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);
        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, ZERO_PAYMENT_VAL_STUB, samePayByDate, TODAY_DATE_AS_STRING);

        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                samePayByDate);

        assertCommandFailure(paymentReceiveCommand, model, MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE);
    }

    // Initial tutee has (100, date1) and receive command is run with null, expected tutee => (0, null)
    @Test
    public void execute_changeInPaymentVal_success() throws ScheduleClashException {

        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        LocalDate existingPayByDate = retrievedTuteePayment.getPayByDate();

        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, NEW_PAYMENT_VAL_STUB_1, existingPayByDate,
                null);
        Model expectedModel = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, ZERO_PAYMENT_VAL_STUB, NULL_DATE,
                TODAY_DATE_AS_STRING);
        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                NULL_DATE);

        Payment expectedPayment = new Payment(ZERO_PAYMENT_VAL_STUB, NULL_DATE);
        expectedPayment.copyPaymentHistory(TODAY_DATE_AS_LIST);
        String successMsg = String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, retrievedTutee.getName(),
                expectedPayment);

        CommandResult expectedMsg = new CommandResult(successMsg);

        assertCommandSuccess(paymentReceiveCommand, model, expectedMsg, expectedModel);
    }

    // Initial tutee has (100, date1) and receive command is run with date2, expected tutee => (0, date2)
    @Test
    public void execute_changeInPaymentVal2_success() throws ScheduleClashException, ParseException {

        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        LocalDate existingPayByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_1);
        LocalDate newPayByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);

        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, NEW_PAYMENT_VAL_STUB_1, existingPayByDate,
                null);

        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                newPayByDate);

        Payment expectedPayment = new Payment(ZERO_PAYMENT_VAL_STUB, newPayByDate);
        expectedPayment.copyPaymentHistory(TODAY_DATE_AS_LIST);
        Model expectedModel = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, ZERO_PAYMENT_VAL_STUB, newPayByDate,
                TODAY_DATE_AS_STRING);

        String successMsg = String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, retrievedTutee.getName(), expectedPayment);
        CommandResult expectedMsg = new CommandResult(successMsg);

        assertCommandSuccess(paymentReceiveCommand, model, expectedMsg, expectedModel);
    }

    // Initial tutee has (0, date1) and receive command is run with null, expected tutee => (0, null)
    @Test
    public void execute_changeInPaymentVal3_success() throws ScheduleClashException, ParseException {

        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        LocalDate existingPayByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_1);

        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, ZERO_PAYMENT_VAL_STUB, existingPayByDate,
                null);
        Model expectedModel = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, ZERO_PAYMENT_VAL_STUB, NULL_DATE,
                TODAY_DATE_AS_STRING);
        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                NULL_DATE);

        Payment expectedPayment = new Payment(ZERO_PAYMENT_VAL_STUB, NULL_DATE);
        expectedPayment.copyPaymentHistory(TODAY_DATE_AS_LIST);
        String successMsg = String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, retrievedTutee.getName(),
                expectedPayment);

        CommandResult expectedMsg = new CommandResult(successMsg);

        assertCommandSuccess(paymentReceiveCommand, model, expectedMsg, expectedModel);
    }

    // Initial tutee has (0, null) and receive command is run with date1, expected tutee => (0, date1)
    @Test
    public void execute_changeInPaymentVal4_success() throws ScheduleClashException, ParseException {

        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        LocalDate newPayByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);

        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, ZERO_PAYMENT_VAL_STUB, NULL_DATE,
                null);
        Model expectedModel = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, ZERO_PAYMENT_VAL_STUB, NULL_DATE,
                TODAY_DATE_AS_STRING);
        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                newPayByDate);

        Payment expectedPayment = new Payment(ZERO_PAYMENT_VAL_STUB, newPayByDate);
        expectedPayment.copyPaymentHistory(TODAY_DATE_AS_LIST);
        String successMsg = String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, retrievedTutee.getName(),
                expectedPayment);

        CommandResult expectedMsg = new CommandResult(successMsg);

        assertCommandSuccess(paymentReceiveCommand, model, expectedMsg, expectedModel);
    }
}
