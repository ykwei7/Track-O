package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.tutee.Tutee;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the tutees list.
     * This list will not contain any duplicate tutees.
     */
    ObservableList<Tutee> getTuteeList();

}
