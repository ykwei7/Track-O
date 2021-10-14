package seedu.address.model.tutee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutee's payment details in Track-O.
 * Guarantees: immutable; is valid as declared in {@link #isValidFee(String)}
 */
public class Fee {


    public static final String MESSAGE_CONSTRAINTS =
            "Fees should only contain numbers, and it should be at least 1 digit long";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;
    public final LocalDateTime lastPaymentDate;
    public final String lastPaymentDateAsString;

    /**
     * Constructs a {@code Fee}.
     *
     * @param fee A valid payment amount.
     */
    public Fee(String fee, LocalDateTime lastPaymentDate) {
        requireNonNull(fee);
        checkArgument(isValidFee(fee), MESSAGE_CONSTRAINTS);
        value = fee;
        this.lastPaymentDate = lastPaymentDate;
        lastPaymentDateAsString = lastPaymentDate.format(DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    /**
     * Returns true if a given string is a valid payment amount.
     */
    public static boolean isValidFee(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        boolean isOverdue = ChronoUnit.MINUTES.between(lastPaymentDate, LocalDateTime.now()) > 1;
        return String.format("$%s (Paid on %s)\nOverdue: %s", value, lastPaymentDateAsString, isOverdue ? "Yes" : "No");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Fee // instanceof handles nulls
                && value.equals(((Fee) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
