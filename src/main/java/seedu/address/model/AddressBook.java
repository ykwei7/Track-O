package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.tutee.Tutee;
import seedu.address.model.tutee.UniqueTuteeList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameTutee comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueTuteeList tutees;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tutees = new UniqueTuteeList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Tutees in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the tutee list with {@code tutees}.
     * {@code tutees} must not contain duplicate tutees.
     */
    public void setTutees(List<Tutee> tutees) {
        this.tutees.setTutees(tutees);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setTutees(newData.getTuteeList());
    }

    //// tutee-level operations

    /**
     * Returns true if a tutee with the same identity as {@code tutee} exists in the address book.
     */
    public boolean hasTutee(Tutee tutee) {
        requireNonNull(tutee);
        return tutees.contains(tutee);
    }

    /**
     * Adds a tutee to the address book.
     * The tutee must not already exist in the address book.
     */
    public void addTutee(Tutee p) {
        tutees.add(p);
    }

    /**
     * Replaces the given tutee {@code target} in the list with {@code editedTutee}.
     * {@code target} must exist in the address book.
     * The tutee identity of {@code editedTutee} must not be the same as another existing tutee in the address book.
     */
    public void setTutee(Tutee target, Tutee editedTutee) {
        requireNonNull(editedTutee);

        tutees.setTutee(target, editedTutee);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTutee(Tutee key) {
        tutees.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return tutees.asUnmodifiableObservableList().size() + " tutees";
        // TODO: refine later
    }

    @Override
    public ObservableList<Tutee> getTuteeList() {
        return tutees.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && tutees.equals(((AddressBook) other).tutees));
    }

    @Override
    public int hashCode() {
        return tutees.hashCode();
    }
}
