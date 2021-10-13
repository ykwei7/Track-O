package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutees.ALICE;
import static seedu.address.testutil.TypicalTutees.HOON;
import static seedu.address.testutil.TypicalTutees.IDA;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackO;
import seedu.address.model.TrackO;

public class JsonTrackOStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTrackOStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTrackO_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTrackO(null));
    }

    private java.util.Optional<ReadOnlyTrackO> readTrackO(String filePath) throws Exception {
        return new JsonTrackOStorage(Paths.get(filePath)).readTrackO(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTrackO("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTrackO("notJsonFormatTrackO.json"));
    }

    @Test
    public void readTrackO_invalidTuteeTrackO_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTrackO("invalidTuteeTrackO.json"));
    }

    @Test
    public void readTrackO_invalidAndValidTuteeTrackO_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTrackO("invalidAndValidTuteeTrackO.json"));
    }

    @Test
    public void readAndSaveTrackO_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTrackO.json");
        TrackO original = getTypicalTrackO();
        JsonTrackOStorage jsonTrackOStorage = new JsonTrackOStorage(filePath);

        // Save in new file and read back
        jsonTrackOStorage.saveTrackO(original, filePath);
        ReadOnlyTrackO readBack = jsonTrackOStorage.readTrackO(filePath).get();
        assertEquals(original, new TrackO(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTutee(HOON);
        original.removeTutee(ALICE);
        jsonTrackOStorage.saveTrackO(original, filePath);
        readBack = jsonTrackOStorage.readTrackO(filePath).get();
        assertEquals(original, new TrackO(readBack));

        // Save and read without specifying file path
        original.addTutee(IDA);
        jsonTrackOStorage.saveTrackO(original); // file path not specified
        readBack = jsonTrackOStorage.readTrackO().get(); // file path not specified
        assertEquals(original, new TrackO(readBack));

    }

    @Test
    public void saveTrackO_nullTrackO_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTrackO(null, "SomeFile.json"));
    }

    /**
     * Saves {@code trackO} at the specified {@code filePath}.
     */
    private void saveTrackO(ReadOnlyTrackO trackO, String filePath) {
        try {
            new JsonTrackOStorage(Paths.get(filePath))
                    .saveTrackO(trackO, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTrackO_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTrackO(new TrackO(), null));
    }
}
