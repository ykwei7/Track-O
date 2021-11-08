package seedu.address.model.tutee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PaymentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Payment(null, null));
    }

    @Test
    public void constructor_invalidPayment_throwsIllegalArgumentException() {
        String invalidPayment = "";
        assertThrows(IllegalArgumentException.class, () -> new Payment(invalidPayment, null));
    }

    @Test
    public void isValidPaymentFormatTest() {
        // null payment
        assertThrows(NullPointerException.class, () -> Payment.isValidPaymentFormat(null));

        // invalid payment formats
        assertFalse(Payment.isValidPaymentFormat("")); // empty string
        assertFalse(Payment.isValidPaymentFormat(" ")); // spaces only
        assertFalse(Payment.isValidPaymentFormat("-91")); // // negative number
        assertFalse(Payment.isValidPaymentFormat("9011p041")); // alphabets within digits
        assertFalse(Payment.isValidPaymentFormat("9312 1534")); // spaces within digits
        assertFalse(Payment.isValidPaymentFormat("90.1")); // 1 decimal place
        assertFalse(Payment.isValidPaymentFormat("90.33")); // second decimal place non-0 and non-5
        assertFalse(Payment.isValidPaymentFormat("90.333")); // more than 2 decimal places


        // valid payment formats
        assertTrue(Payment.isValidPaymentFormat("911")); // 0 decimal places
        assertTrue(Payment.isValidPaymentFormat("90.50")); // 2 decimal places ending with 0
        assertTrue(Payment.isValidPaymentFormat("90.05")); // 2 decimal places ending with 5
    }

    @Test
    public void isValidPaymentAmountTest() {
        // null payment
        assertThrows(NullPointerException.class, () -> Payment.isValidPaymentAmount(null));

        // invalid payment formats
        assertThrows(AssertionError.class, () ->
                Payment.isValidPaymentAmount("")); // empty string
        assertThrows(AssertionError.class, () ->
                Payment.isValidPaymentAmount(" ")); // spaces only
        assertThrows(AssertionError.class, () ->
                Payment.isValidPaymentAmount("-91")); // // negative number
        assertThrows(AssertionError.class, () ->
                Payment.isValidPaymentAmount("9011p041")); // alphabets within digits
        assertThrows(AssertionError.class, () ->
                Payment.isValidPaymentAmount("9312 1534")); // spaces within digits
        assertThrows(AssertionError.class, () ->
                Payment.isValidPaymentAmount("90.1")); // 1 decimal place
        assertThrows(AssertionError.class, () ->
                Payment.isValidPaymentAmount("90.33")); // second decimal place non-0 and non-5
        assertThrows(AssertionError.class, () ->
                Payment.isValidPaymentAmount("90.333")); // more than 2 decimal places

        // invalid payment amounts
        assertFalse(Payment.isValidPaymentAmount("100001")); // exceeds 100000
        assertFalse(Payment.isValidPaymentAmount(String.valueOf(Integer.MAX_VALUE))); // MAX_INT

        // valid payment amounts
        assertTrue(Payment.isValidPaymentAmount("100000")); // upper boundary value
        assertTrue(Payment.isValidPaymentAmount("0")); // lower boundary value
        assertTrue(Payment.isValidPaymentAmount("911")); // whole number with 0 decimal places
        assertTrue(Payment.isValidPaymentAmount("90.50")); // 2 decimal places ending with 0
        assertTrue(Payment.isValidPaymentAmount("90.05")); // 2 decimal places ending with 5
    }

    @Test
    public void isPaymentWithAnyDecimalsTest() {
        // null payment
        assertThrows(NullPointerException.class, () -> Payment.isNumberWithAnyDecimals(null));

        // invalid payment amounts
        assertFalse(Payment.isNumberWithAnyDecimals("")); // empty string
        assertFalse(Payment.isNumberWithAnyDecimals(" ")); // spaces only
        assertFalse(Payment.isNumberWithAnyDecimals("-91")); // // negative number
        assertFalse(Payment.isNumberWithAnyDecimals("9011p041")); // alphabets within digits
        assertFalse(Payment.isNumberWithAnyDecimals("9312 1534")); // spaces within digits
        assertFalse(Payment.isNumberWithAnyDecimals("90.")); // decimal point with no decimal value


        // valid payment amounts
        assertTrue(Payment.isNumberWithAnyDecimals("90.33")); // 2 decimal places allowed
        assertTrue(Payment.isNumberWithAnyDecimals("90.333")); // 3 decimal places allowed
        assertTrue(Payment.isNumberWithAnyDecimals("90.123456789")); // any number of decimal places allowed
        assertTrue(Payment.isNumberWithAnyDecimals("124293842033123")); // whole number with 0 decimals
    }

    @Test
    public void isValidPayByDateTest() {
        assertThrows(NullPointerException.class, () -> Payment.isValidPayByDate(null));

        // invalid payment dates
        assertFalse(Payment.isValidPayByDate("")); // empty string
        assertFalse(Payment.isValidPayByDate(" ")); // spaces only
        assertFalse(Payment.isValidPayByDate("20 October 2021")); // Incorrect DateTime format
        assertFalse(Payment.isValidPayByDate("32-12-2021")); // Impossible day
        assertFalse(Payment.isValidPayByDate("01-13-2021")); // Impossible month

        // possible day, possible month, but impossible day and month
        assertFalse(Payment.isValidPayByDate("29-02-2022"));
        assertFalse(Payment.isValidPayByDate("30-02-2022"));
        assertFalse(Payment.isValidPayByDate("31-02-2022"));
        assertFalse(Payment.isValidPayByDate("31-11-2021"));

        // valid payment dates
        assertTrue(Payment.isValidPayByDate("20-10-2021"));
        assertTrue(Payment.isValidPayByDate("01-01-2022"));
        assertTrue(Payment.isValidPayByDate("25-12-2021"));
        assertTrue(Payment.isValidPayByDate("29-02-2024")); // leap day
    }

    @Test
    public void isValidPaymentHistoryTest() {
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
    public void copyPaymentHistoryTest_validPaymentHistory_success() {
        List<String> validPaymentHistory = Arrays.asList("Never", "20-10-2021", "21-10-2021");
        Payment p1 = Payment.initializePayment();
        p1.copyPaymentHistory(validPaymentHistory);
        assertEquals(p1.paymentHistory, validPaymentHistory);
    }

    @Test
    public void copyPaymentHistoryTest_invalidPaymentHistory_failure() {
        List<String> invalidPaymentHistory = Arrays.asList("Never", "20-10-2021", "21-13-2021");
        List<String> emptyPaymentHistory = Arrays.asList("Never");
        Payment p1 = Payment.initializePayment();
        p1.copyPaymentHistory(invalidPaymentHistory);
        assertEquals(p1.paymentHistory, emptyPaymentHistory);
    }
}
