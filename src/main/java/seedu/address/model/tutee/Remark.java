package seedu.address.model.tutee;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Tutee's remark in Track-O.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public final String value;

    /**
     * Creates a remark.
     * @param remark remark inputted by user
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    /**
     * Appends a new remark to the tutee's existing remarks.
     * @param newRemark new remark inputted by user
     * @return the appended remark
     */
    public Remark appendRemark(Remark newRemark) {
        String newRemarkStr = value + "\n" + newRemark.value;
        return new Remark(newRemarkStr);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
