package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackO;

/**
 * Represents a storage for {@link seedu.address.model.TrackO}.
 */
public interface TrackOStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTrackOFilePath();

    /**
     * Returns Track-O data as a {@link ReadOnlyTrackO}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTrackO> readTrackO() throws DataConversionException, IOException;

    /**
     * @see #getTrackOFilePath()
     */
    Optional<ReadOnlyTrackO> readTrackO(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTrackO} to the storage.
     * @param trackO cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTrackO(ReadOnlyTrackO trackO) throws IOException;

    /**
     * @see #saveTrackO(ReadOnlyTrackO)
     */
    void saveTrackO(ReadOnlyTrackO trackO, Path filePath) throws IOException;

}
