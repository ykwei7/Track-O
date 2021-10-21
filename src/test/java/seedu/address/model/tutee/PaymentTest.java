package seedu.address.model.tutee;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class PaymentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Payment(null, null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPayment = "";
        assertThrows(IllegalArgumentException.class, () -> new Payment(invalidPayment, null));
    }

    @Test
    public void isValidPayment() {
        // null payment
        assertThrows(NullPointerException.class, () -> Payment.isValidPayment(null));

        // invalid payment amounts
        assertFalse(Payment.isValidPayment("")); // empty string
        assertFalse(Payment.isValidPayment(" ")); // spaces only
        assertFalse(Payment.isValidPayment("-91")); // // non-numeric
        assertFalse(Payment.isValidPayment("9011p041")); // alphabets within digits
        assertFalse(Payment.isValidPayment("9312 1534")); // spaces within digits

        // valid payment amounts
        assertTrue(Payment.isValidPayment("911"));
        assertTrue(Payment.isValidPayment("93121534"));
        assertTrue(Payment.isValidPayment("124293842033123"));
    }

    @Test
    public void isValidPayByDate() {
        assertThrows(NullPointerException.class, () -> Payment.isValidPayByDate(null));

        // invalid payment dates
        assertFalse(Payment.isValidPayByDate("")); // empty string
        assertFalse(Payment.isValidPayByDate(" "));// spaces only
        assertFalse(Payment.isValidPayByDate("20 October 2021")); // Incorrect DateTime format
        assertFalse(Payment.isValidPayByDate("32-12-2021")); // Impossible day
        assertFalse(Payment.isValidPayByDate("01-13-2021")); // Impossible month

        // valid payment dates
        assertTrue(Payment.isValidPayByDate("20-10-2021"));
        assertTrue(Payment.isValidPayByDate("01-01-2022"));
        assertTrue(Payment.isValidPayByDate("25-12-2021"));
    }

    @Test
    public void isValidPaymentHistory() {
        assertThrows(NullPointerException.class, () -> Payment.isValidPaymentHistory(null));

        // invalid payment histories
        assertFalse(Payment.isValidPaymentHistory(Arrays.asList(""))); // empty string
        assertFalse(Payment.isValidPaymentHistory(Arrays.asList(" "))); // spaces only
        assertFalse(Payment.isValidPaymentHistory(Arrays.asList("never"))); // // "Never" is in lowercase
        assertFalse(Payment.isValidPaymentHistory(Arrays.asList("Never", "20 October 2021"))); // wrong date format
        assertFalse(Payment.isValidPaymentHistory(Arrays.asList("20-10-2021"))); // "Never" is not first element

        // valid payment histories
        assertTrue(Payment.isValidPaymentHistory(Arrays.asList("Never", "20-10-2021")));
        assertTrue(Payment.isValidPaymentHistory(Arrays.asList("Never", "20-10-2021", "21-01-2021")));
        assertTrue(Payment.isValidPaymentHistory(Arrays.asList("Never", "20-10-2021", "21-01-2021", "01-01-2055")));
    }

    @Test
    public void copyPaymentHistoryTest_validPaymentHistory_copiesList() {
        List<String> validPaymentHistory = Arrays.asList("Never", "20-10-2021", "21-10-2021");
        Payment p1 = Payment.initializePayment();
        p1.copyPaymentHistory(validPaymentHistory);
        assertEquals(p1.paymentHistory, validPaymentHistory);
    }

    @Test
    public void copyPaymentHistoryTest_invalidPaymentHistory_keepsOwnList() {
        List<String> invalidPaymentHistory = Arrays.asList("Never", "20-10-2021", "21-13-2021");
        List<String> emptyPaymentHistory = Arrays.asList("Never");
        Payment p1 = Payment.initializePayment();
        p1.copyPaymentHistory(invalidPaymentHistory);
        assertEquals(p1.paymentHistory, emptyPaymentHistory);
    }
}
