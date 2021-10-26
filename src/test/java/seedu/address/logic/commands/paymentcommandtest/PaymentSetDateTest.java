package seedu.address.logic.commands.paymentcommandtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.paymentcommand.PaymentSetDateCommand.MESSAGE_NO_CHANGE_IN_PAYMENT_DATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.paymentcommand.PaymentCommand;
import seedu.address.logic.commands.paymentcommand.PaymentSetDateCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Tutee;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 *  * {@code PaymentReceiveTest}.
 */
public class PaymentSetDateTest {

    private static final String NEW_PAYBYDATE_VAL_STUB_1 = "15-10-2022";
    private static final String NEW_PAYBYDATE_VAL_STUB_2 = "20-10-2022";
    private static final LocalDate NULL_DATE = null;

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

    @Test
    public void execute_noChangeInPaymentDate_throwsCommandException() throws ParseException {
        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        String retrievedPaymentVal = retrievedTuteePayment.getValue();
        LocalDate payByDate = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);

        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, retrievedPaymentVal , payByDate);

        PaymentSetDateCommand paymentSetDateCommand = new PaymentSetDateCommand(INDEX_FIRST_TUTEE,
                payByDate);

        assertCommandFailure(paymentSetDateCommand, model, MESSAGE_NO_CHANGE_IN_PAYMENT_DATE);
    }

    // Tutee with null date to initialized date
    @Test
    public void execute_changeInPaymentDate_success() throws CommandException, ParseException {

        LocalDate newPayByDate2 = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);
        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        String retrievedPaymentVal = retrievedTuteePayment.getValue();

        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, retrievedPaymentVal, NULL_DATE);

        PaymentSetDateCommand paymentSetDateCommand = new PaymentSetDateCommand(INDEX_FIRST_TUTEE,
                newPayByDate2);

        paymentSetDateCommand.execute(model);
        Tutee expectedTutee = PaymentCommand.createEditedPaymentDetailsTutee(retrievedTutee, retrievedPaymentVal,
                newPayByDate2);
        Tutee actualTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        assertEquals(expectedTutee.getPayment(), actualTutee.getPayment());
    }

    // Tutee with initalized date to new initialized different date
    @Test
    public void execute_changeInPaymentDate2_success() throws CommandException, ParseException {

        LocalDate newPayByDate2 = ParserUtil.parsePayByDate(NEW_PAYBYDATE_VAL_STUB_2);
        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        String retrievedPaymentVal = retrievedTuteePayment.getValue();

        PaymentSetDateCommand paymentSetDateCommand = new PaymentSetDateCommand(INDEX_FIRST_TUTEE,
                newPayByDate2);

        paymentSetDateCommand.execute(model);
        Tutee expectedTutee = PaymentCommand.createEditedPaymentDetailsTutee(retrievedTutee, retrievedPaymentVal,
                newPayByDate2);
        Tutee actualTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        assertEquals(expectedTutee.getPayment(), actualTutee.getPayment());
    }

}
