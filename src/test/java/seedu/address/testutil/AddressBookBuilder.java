package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.tutee.Tutee;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withTutee("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Tutee} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withTutee(Tutee tutee) {
        addressBook.addTutee(tutee);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
