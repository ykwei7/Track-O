package seedu.address.logic.commands.paymentcommandtest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.paymentcommand.PaymentSetAmountCommand.MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE;
import static seedu.address.logic.commands.paymentcommand.PaymentSetAmountCommand.UPDATE_TUTEE_PAYMENT_SUCCESS;
import static seedu.address.logic.commands.paymentcommandtest.PaymentCommandTest.modifyPaymentOfTutee;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.paymentcommand.PaymentSetAmountCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Tutee;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 *  * {@code PaymentSetAmountCommand}.
 */
public class PaymentSetAmountCommandTest {

    private static final String NEW_PAYMENT_VAL_STUB_1 = "100";
    private static final String NEW_PAYMENT_VAL_STUB_2 = "200";
    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public PaymentSetAmountCommandTest() throws ScheduleClashException {
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws ScheduleClashException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTuteeList().size() + 1);
        PaymentSetAmountCommand paymentSetAmountCommand = new PaymentSetAmountCommand(outOfBoundIndex,
                NEW_PAYMENT_VAL_STUB_1);

        assertCommandFailure(paymentSetAmountCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        PaymentSetAmountCommand getFirstCommand = new PaymentSetAmountCommand(INDEX_FIRST_TUTEE,
                NEW_PAYMENT_VAL_STUB_1);
        PaymentSetAmountCommand getSecondCommand = new PaymentSetAmountCommand(INDEX_SECOND_TUTEE,
                NEW_PAYMENT_VAL_STUB_2);

        // same object -> returns true
        assertTrue(getFirstCommand.equals(getFirstCommand));

        // same values -> returns true
        PaymentSetAmountCommand getFirstCommandCopy = new PaymentSetAmountCommand(INDEX_FIRST_TUTEE,
                NEW_PAYMENT_VAL_STUB_1);
        assertTrue(getFirstCommand.equals(getFirstCommandCopy));

        // different types -> returns false
        assertFalse(getFirstCommand.equals(1));

        // null -> returns false
        assertFalse(getFirstCommand.equals(null));

        // different tutee -> returns false
        assertFalse(getFirstCommand.equals(getSecondCommand));
    }

    @Test
    public void execute_noChangeInPayment_throwsCommandException() throws ScheduleClashException {

        // Creates tutee with specified payment details
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        LocalDate payByDate = retrievedTuteePayment.getPayByDate();

        model = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, NEW_PAYMENT_VAL_STUB_1, payByDate, null);

        PaymentSetAmountCommand paymentSetAmountCommand = new PaymentSetAmountCommand(INDEX_FIRST_TUTEE,
                NEW_PAYMENT_VAL_STUB_1);

        assertCommandFailure(paymentSetAmountCommand, model, MESSAGE_NO_CHANGE_IN_PAYMENT_VALUE);
    }

    @Test
    public void execute_changeInPayment_success() throws ScheduleClashException {
        // Creates tutee with specified payment details

        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        Payment retrievedTuteePayment = retrievedTutee.getPayment();
        LocalDate existingPayByDate = retrievedTuteePayment.getPayByDate();

        Model expectedModel = modifyPaymentOfTutee(INDEX_FIRST_TUTEE, NEW_PAYMENT_VAL_STUB_2, existingPayByDate,
                null);

        PaymentSetAmountCommand paymentSetAmountCommand = new PaymentSetAmountCommand(INDEX_FIRST_TUTEE,
                NEW_PAYMENT_VAL_STUB_2);

        String successMsg = String.format(UPDATE_TUTEE_PAYMENT_SUCCESS, retrievedTutee.getName(),
                new Payment(NEW_PAYMENT_VAL_STUB_2, existingPayByDate));
        CommandResult expectedMsg = new CommandResult(successMsg);

        assertCommandSuccess(paymentSetAmountCommand, model, expectedMsg , expectedModel);
    }
}
