package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TrackO;
import seedu.address.testutil.TypicalTutees;

public class JsonSerializableTrackOTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTrackOTest");
    private static final Path TYPICAL_TUTEES_FILE = TEST_DATA_FOLDER.resolve("typicalTuteesTrackO.json");
    private static final Path INVALID_TUTEE_FILE = TEST_DATA_FOLDER.resolve("invalidTuteeTrackO.json");
    private static final Path DUPLICATE_TUTEE_FILE = TEST_DATA_FOLDER.resolve("duplicateTuteeTrackO.json");

    @Test
    public void toModelType_typicalTuteesFile_success() throws Exception {
        JsonSerializableTrackO dataFromFile = JsonUtil.readJsonFile(TYPICAL_TUTEES_FILE,
                JsonSerializableTrackO.class).get();
        TrackO trackOFromFile = dataFromFile.toModelType();
        TrackO typicalTuteesTrackO = TypicalTutees.getTypicalTrackO();
        assertEquals(trackOFromFile, typicalTuteesTrackO);
    }

    @Test
    public void toModelType_invalidTuteeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTrackO dataFromFile = JsonUtil.readJsonFile(INVALID_TUTEE_FILE,
                JsonSerializableTrackO.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTutees_throwsIllegalValueException() throws Exception {
        JsonSerializableTrackO dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TUTEE_FILE,
                JsonSerializableTrackO.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTrackO.MESSAGE_DUPLICATE_TUTEE,
                dataFromFile::toModelType);
    }

}
