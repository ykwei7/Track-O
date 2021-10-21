package seedu.address.model.tutee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a Tutee's payment details in Track-O.
 * Guarantees: immutable; is valid as declared in {@link #isValidPayment(String)}
 */
public class Payment {


    public static final String MESSAGE_CONSTRAINTS =
            "Payments should only contain numbers, and it should be at least 1 digit long";
    public static final String DATE_CONSTRAINTS =
            "Payment due dates should be in the format of dd-MM-yyyy, i.e 20-Oct-2021";
    public static final String PAYMENT_HISTORY_CONSTRAINTS =
            "Payment history should only contain dates in the format of dd-MM-yyyy, i.e 20-Oct-2021, and 'Never'";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;
    public final LocalDate payByDate;
    public final String payByDateAsString;
    public final boolean isOverdue;
    public final List<String> paymentHistory = new ArrayList<String>();


    /**
     * Constructs a {@code Payment}.
     *
     * @param payment A valid payment amount.
     */
    public Payment(String payment, LocalDate payByDate) {
        requireNonNull(payment);
        checkArgument(isValidPayment(payment), MESSAGE_CONSTRAINTS);
        value = payment;
        this.payByDate = payByDate;
        this.paymentHistory.add("Never");
        payByDateAsString = payByDate == null ? "-" : payByDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        isOverdue = payByDate == null ? false : LocalDate.now().isAfter(payByDate);
    }

    /**
     * Initializes a standard payment sum starting from $0
     * @return A payment of 0 without a last payment date.
     */
    public static Payment initializePayment() {
        return new Payment("0", null);
    }

    /**
     * Returns true if a given string is a valid payment amount.
     */
    public static boolean isValidPayment(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid pay by date.
     */
    public static boolean isValidPayByDate(String payByDateAsString) {
        if (!payByDateAsString.equals("-")) {
            try {
                LocalDate.parse(payByDateAsString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a given list is a valid payment history.
     */
    public static boolean isValidPaymentHistory(List<String> paymentHistory) {
        if (!paymentHistory.get(0).equals("Never")) {
            return false;
        }
        for (int i = 1; i < paymentHistory.size(); i++) {
            if (!isValidPayByDate(paymentHistory.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Copies a payment history to the current Payment
     * @param historyToCopy The payment history to copy
     */
    public void copyPaymentHistory(List<String> historyToCopy) {
        if (!isValidPaymentHistory(historyToCopy)) {
            return;
        } else {
            paymentHistory.clear();
            assert paymentHistory.isEmpty() : "paymentHistory should be empty";
            for (String payment : historyToCopy) {
                this.paymentHistory.add(payment);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("$%s (Last paid on: %s)\nOverdue: %s",
                value,
                paymentHistory.get(paymentHistory.size() - 1),
                isOverdue ? "Yes (on " + payByDateAsString + ")" : "No");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Payment // instanceof handles nulls
                && value.equals(((Payment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
