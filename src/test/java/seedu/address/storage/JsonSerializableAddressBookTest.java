package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalTutees;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_TUTEES_FILE = TEST_DATA_FOLDER.resolve("typicalTuteesAddressBook.json");
    private static final Path INVALID_TUTEE_FILE = TEST_DATA_FOLDER.resolve("invalidTuteeAddressBook.json");
    private static final Path DUPLICATE_TUTEE_FILE = TEST_DATA_FOLDER.resolve("duplicateTuteeAddressBook.json");

    @Test
    public void toModelType_typicalTuteesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_TUTEES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalTuteesAddressBook = TypicalTutees.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalTuteesAddressBook);
    }

    @Test
    public void toModelType_invalidTuteeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_TUTEE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTutees_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TUTEE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_TUTEE,
                dataFromFile::toModelType);
    }

}
