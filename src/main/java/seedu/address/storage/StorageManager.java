package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackO;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Track-O data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TrackOStorage trackOStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code TrackOStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(TrackOStorage trackOStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.trackOStorage = trackOStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Track-O methods ==============================

    @Override
    public Path getTrackOFilePath() {
        return trackOStorage.getTrackOFilePath();
    }

    @Override
    public Optional<ReadOnlyTrackO> readTrackO() throws DataConversionException, IOException {
        return readTrackO(trackOStorage.getTrackOFilePath());
    }

    @Override
    public Optional<ReadOnlyTrackO> readTrackO(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return trackOStorage.readTrackO(filePath);
    }

    @Override
    public void saveTrackO(ReadOnlyTrackO trackO) throws IOException {
        saveTrackO(trackO, trackOStorage.getTrackOFilePath());
    }

    @Override
    public void saveTrackO(ReadOnlyTrackO trackO, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        trackOStorage.saveTrackO(trackO, filePath);
    }

}
