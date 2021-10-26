package seedu.address.logic.commands.paymentcommandtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.paymentcommand.PaymentReceiveCommand.MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.paymentcommand.PaymentCommand;
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
 *  * {@code PaymentReceiveTest}.
 */
public class PaymentReceiveTest {

    private static final String ZERO_PAYMENT_VAL_STUB = "0";
    private static final String NEW_PAYMENT_VAL_STUB_1 = "100";
    private static final String NEW_PAYBYDATE_VAL_STUB_1 = "15-10-2022";
    private static final String NEW_PAYBYDATE_VAL_STUB_2 = "20-10-2022";
    private static final LocalDate NULL_DATE = null;

    private Model model;

    private Model modifyPaymentOfTutee(Index index, String newPaymentValue,
                                       LocalDate newPayByDate) throws ScheduleClashException {
        model = new ModelManager(getTypicalTrackO(), new UserPrefs());
        Tutee retrievedTutee = model.getFilteredTuteeList().get(index.getZeroBased());
        Tutee editedTutee = PaymentCommand.createEditedPaymentDetailsTutee(retrievedTutee, newPaymentValue,
                newPayByDate);
        model.setTutee(retrievedTutee, editedTutee);
        return model;
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws ParseException,
            ScheduleClashException {
        model = new ModelManager(getTypicalTrackO(), new UserPrefs());
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

    @Test
    public void execute_noChangeInPaymentVal_throwsCommandException() throws ScheduleClashException {
        model = new ModelManager(getTypicalTrackO(), new UserPrefs());
        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        LocalDate payByDate = retrievedTuteePayment.getPayByDate();

        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, ZERO_PAYMENT_VAL_STUB, payByDate);

        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                payByDate);

        assertCommandFailure(paymentReceiveCommand, model, MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE);
    }

    // When provided date is null e.g (200, payByDate) -> (0, payByDate)
    @Test
    public void execute_changeInPaymentVal_success() throws CommandException, ScheduleClashException {

        model = new ModelManager(getTypicalTrackO(), new UserPrefs());
        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        LocalDate payByDate = retrievedTuteePayment.getPayByDate();
        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, NEW_PAYMENT_VAL_STUB_1, payByDate);

        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                payByDate);

        paymentReceiveCommand.execute(model);
        Tutee expectedTutee = PaymentCommand.createEditedPaymentDetailsTutee(retrievedTutee, ZERO_PAYMENT_VAL_STUB,
                payByDate);
        Tutee actualTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        assertEquals(expectedTutee.getPayment(), actualTutee.getPayment());
    }

    // When provided date is null e.g (200, payByDate) -> (0, null)
    @Test
    public void execute_changeInPaymentVal2_success() throws CommandException, ScheduleClashException {

        model = new ModelManager(getTypicalTrackO(), new UserPrefs());
        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        LocalDate payByDate = retrievedTuteePayment.getPayByDate();
        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, NEW_PAYMENT_VAL_STUB_1, payByDate);

        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                NULL_DATE);

        paymentReceiveCommand.execute(model);
        Tutee expectedTutee = PaymentCommand.createEditedPaymentDetailsTutee(retrievedTutee, ZERO_PAYMENT_VAL_STUB,
                payByDate);
        Tutee actualTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        assertEquals(expectedTutee.getPayment(), actualTutee.getPayment());
    }

    // When both dates provided are null e.g (200, null) -> (0, null)
    @Test
    public void execute_changeInPaymentVal3_success() throws CommandException, ScheduleClashException {

        model = new ModelManager(getTypicalTrackO(), new UserPrefs());
        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());

        Model expectedModel = model;
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        LocalDate payByDate = retrievedTuteePayment.getPayByDate();
        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, NEW_PAYMENT_VAL_STUB_1, NULL_DATE);

        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                NULL_DATE);

        paymentReceiveCommand.execute(model);
        Tutee expectedTutee = PaymentCommand.createEditedPaymentDetailsTutee(retrievedTutee, ZERO_PAYMENT_VAL_STUB,
                payByDate);

        Tutee actualTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());

        assertEquals(expectedTutee.getPayment(), actualTutee.getPayment());
    }

    // When initial date is null, and changed to non-null date e.g (200, null) -> (0, payByDate)
    @Test
    public void execute_changeInPaymentVal4_success() throws CommandException, ScheduleClashException {

        model = new ModelManager(getTypicalTrackO(), new UserPrefs());
        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        LocalDate payByDate = retrievedTuteePayment.getPayByDate();
        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, NEW_PAYMENT_VAL_STUB_1, NULL_DATE);

        PaymentReceiveCommand paymentReceiveCommand = new PaymentReceiveCommand(INDEX_FIRST_TUTEE,
                payByDate);

        paymentReceiveCommand.execute(model);
        Tutee expectedTutee = PaymentCommand.createEditedPaymentDetailsTutee(retrievedTutee, ZERO_PAYMENT_VAL_STUB,
                payByDate);
        Tutee actualTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        assertEquals(expectedTutee.getPayment(), actualTutee.getPayment());
    }


}
