package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTrackO;

/**
 * A class to access Track-O data stored as a json file on the hard disk.
 */
public class JsonTrackOStorage implements TrackOStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTrackOStorage.class);

    private Path filePath;

    public JsonTrackOStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTrackOFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTrackO> readTrackO() throws DataConversionException, IOException {
        return readTrackO(filePath);
    }

    /**
     * Similar to {@link #readTrackO()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTrackO> readTrackO(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableTrackO> jsonTrackO = JsonUtil.readJsonFile(
                filePath, JsonSerializableTrackO.class);
        if (!jsonTrackO.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTrackO.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTrackO(ReadOnlyTrackO trackO) throws IOException {
        saveTrackO(trackO, filePath);
    }

    /**
     * Similar to {@link #saveTrackO(ReadOnlyTrackO)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTrackO(ReadOnlyTrackO trackO, Path filePath) throws IOException {
        requireNonNull(trackO);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTrackO(trackO), filePath);
    }

}
