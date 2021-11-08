package seedu.address.model.tutee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a Tutee's payment details in Track-O.
 * Guarantees: immutable; is valid as declared in {@link #isValidPaymentFormat(String)}
 */
public class Payment {

    /** Error message displayed when the payment is not a positive number. */
    public static final String FORMAT_CONSTRAINTS_MESSAGE =
            "Payment values should only contain non-negative numbers with at least 1 digit, allowing 0 or 2 decimals "
                    + "i.e 0, 100 or 74.50";

    /**  Error message displayed when the payment does not have valid decimal places. */
    public static final String DECIMAL_CONSTRAINTS_MESSAGE =
            "Payment values must have either 0 or 2 decimal places. If it has 2 decimal places, it must "
                    + "end with either a 0 or 5, i.e 40.50 or 40.55.";

    /** Error message displayed when the payment exceeds the maximum amount. */
    public static final String AMOUNT_CONSTRAINTS_MESSAGE = "Payment value should not exceed $100,000";

    /** Error message displayed when the payment due date is in the incorrect format. */
    public static final String DATE_CONSTRAINTS_MESSAGE =
            "Payment due dates should be a valid date in the format of dd-MM-yyyy, i.e 20-10-2021 and"
                    + " must equal to or after today's date.";

    /** Error message displayed when the payment history is in the incorrect format. */
    public static final String PAYMENT_HISTORY_CONSTRAINTS_MESSAGE =
            "Payment history should only contain dates in the format of dd-MM-yyyy, i.e 20-10-2021, and 'Never'.";

    /** The formatter used to format dates in the dd-MM-YYY pattern. */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final String TODAY_DATE_AS_STRING = LocalDate.now().format(FORMATTER);

    /** The regex used to determine if a string is a valid non-negative number*/
    public static final String VALIDATION_REGEX_NUMERICAL_FRONT_ANY_DECIMALS = "^[0-9][\\d]*([.][0-9]+)?$"
            .replaceFirst("^0+", "");

    /** The regex used to determine if a string is a valid non_negative number with strictly zero or two decimals. */
    public static final String VALIDATION_REGEX_PAYMENT_NO_OR_TWO_DECIMAL_PLACES = "^[0-9][\\d]*([.][0-9][0|5])?$"
            .replaceFirst("^0+", "");

    /** The maximum amount that a tutee can owe. */
    public static final Double MAXIMUM_AMOUNT = 100000.00;

    /** The payment amount due by the tutee. */
    public final String value;

    /** The payment due date set for the tutee. */
    public final LocalDate payByDate;
    public final String payByDateAsString;

    /** Whether the current date has exceeded the payment due date. */
    public final boolean isOverdue;

    /** The previous payment dates of the tutee. */
    public final List<String> paymentHistory = new ArrayList<String>();


    /**
     * Constructs a {@code Payment}.
     *
     * @param payment A valid payment amount.
     */
    public Payment(String payment, LocalDate payByDate) {
        requireNonNull(payment);
        checkArgument(isValidPaymentFormat(payment), FORMAT_CONSTRAINTS_MESSAGE);
        value = payment;
        this.payByDate = payByDate;
        this.paymentHistory.add("Never");
        payByDateAsString = payByDate == null ? "-" : payByDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        isOverdue = payByDate == null ? false : LocalDate.now().isAfter(payByDate);
    }

    /**
     * Initializes a standard payment sum starting from $0.
     * @return A payment of 0 without a last payment date.
     */
    public static Payment initializePayment() {
        return new Payment("0", null);
    }

    /**
     * Returns true if a given string has a valid payment format of a non-negative number with zero or two decimals.
     * @param test The string to test on
     * @return Whether the string matches the regex
     */
    public static boolean isValidPaymentFormat(String test) {
        return test.matches(VALIDATION_REGEX_PAYMENT_NO_OR_TWO_DECIMAL_PLACES);
    }


    /**
     * Returns true if a given string is a number any number of decimal places which may or may not be a valid payment.
     * @param test The string to test on
     * @return Whether the string matches the regex
     */
    public static boolean isNumberWithAnyDecimals(String test) {
        return test.matches(VALIDATION_REGEX_NUMERICAL_FRONT_ANY_DECIMALS);
    }

    /**
     * Checks if the amount to set is less than or equal to the maximum allowed.
     * @param test The string to test on
     * @return Whether the string matches the regex and is less than the maximum allowed
     */
    public static boolean isValidPaymentAmount(String test) {
        assert test.matches(VALIDATION_REGEX_PAYMENT_NO_OR_TWO_DECIMAL_PLACES)
                : "isValidPaymentAmount() only called after isValidPaymentFormat() regex check";
        return Double.parseDouble(test) <= MAXIMUM_AMOUNT;
    }

    /**
     * Returns true if a given string is a valid pay by date.
     * @param payByDateAsString The string to check
     * @return Whether the given string follows the correct format
     */
    public static boolean isValidPayByDate(String payByDateAsString) {
        if (!payByDateAsString.equals("-")) {
            try {
                LocalDate.parse(payByDateAsString, DateTimeFormatter
                        .ofPattern("dd-MM-uuuu")
                        .withResolverStyle(ResolverStyle.STRICT));
            } catch (DateTimeParseException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a given list is a valid payment history.
     * @param paymentHistory The List to check
     * @return Whether the given List follows the correct format for payment histories
     */
    public static boolean isValidPaymentHistory(List<String> paymentHistory) {
        String firstPaymentDate = paymentHistory.get(0);
        if (!firstPaymentDate.equals("Never")) {
            return false;
        } else {
            // Checks if all the entries are valid payment due dates
            for (int i = 1; i < paymentHistory.size(); i++) {
                if (!isValidPayByDate(paymentHistory.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Copies a payment history to the current Payment.
     * @param historyToCopy The payment history to copy
     */
    public void copyPaymentHistory(List<String> historyToCopy) {
        if (!isValidPaymentHistory(historyToCopy)) {
            return;
        } else {
            // Ensure current payment history is empty before copying over
            paymentHistory.clear();
            assert paymentHistory.isEmpty() : "paymentHistory should be empty";
            for (String payment : historyToCopy) {
                this.paymentHistory.add(payment);
            }
        }
    }

    @Override
    public String toString() {
        String lastPaidDate = paymentHistory.get(paymentHistory.size() - 1);
        return String.format("$%s (Last paid on: %s)\nOverdue: %s",
                getValue(), lastPaidDate, getOverdueStatus());
    }

    /**
     * Provides the String representation of the payment's overdue status.
     * @return The status of the payment as a String
     */
    public String getOverdueStatus() {
        if (isOverdue) {
            return "Yes (on " + payByDateAsString + ")";
        } else if (payByDateAsString.equals("-")) {
            return "No (Pay-by date not set)";
        } else {
            // Payment is not overdue
            return "No (Next payment date by: " + payByDateAsString + ")";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Payment // instanceof handles nulls
                && value.equals(((Payment) other).value)); // state check
    }

    public LocalDate getPayByDate() {
        return this.payByDate;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
