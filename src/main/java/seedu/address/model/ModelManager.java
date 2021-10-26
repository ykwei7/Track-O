package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.tutee.Tutee;

/**
 * Represents the in-memory model of Track-O data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TrackO trackO;
    private final UserPrefs userPrefs;
    private final FilteredList<Tutee> filteredTutees;
    private final Schedule schedule;

    /**
     * Initializes a ModelManager with the given Track-O and userPrefs.
     */
    public ModelManager(ReadOnlyTrackO trackO, ReadOnlyUserPrefs userPrefs) throws ScheduleClashException {
        super();
        requireAllNonNull(trackO, userPrefs);

        logger.fine("Initializing with Track-O: " + trackO + " and user prefs " + userPrefs);

        this.trackO = new TrackO(trackO);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTutees = new FilteredList<>(this.trackO.getTuteeList());
        schedule = new Schedule(this.trackO.getTuteeList());
    }

    public ModelManager() throws ScheduleClashException {
        this(new TrackO(), new UserPrefs());
    }

    //=========== Schedule ==================================================================================

    @Override
    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public void clearSchedule() {
        schedule.clear();
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTrackOFilePath() {
        return userPrefs.getTrackOFilePath();
    }

    @Override
    public void setTrackOFilePath(Path trackOFilePath) {
        requireNonNull(trackOFilePath);
        userPrefs.setTrackOFilePath(trackOFilePath);
    }

    //=========== TrackO ================================================================================

    @Override
    public void setTrackO(ReadOnlyTrackO trackO) {
        this.trackO.resetData(trackO);
    }

    @Override
    public ReadOnlyTrackO getTrackO() {
        return trackO;
    }

    @Override
    public boolean hasTutee(Tutee tutee) {
        requireNonNull(tutee);
        return trackO.hasTutee(tutee);
    }

    @Override
    public void deleteTutee(Tutee target) {
        trackO.removeTutee(target);
    }

    @Override
    public void addTutee(Tutee tutee) {
        trackO.addTutee(tutee);
        updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);
    }

    @Override
    public void setTutee(Tutee target, Tutee editedTutee) {
        requireAllNonNull(target, editedTutee);

        trackO.setTutee(target, editedTutee);
    }

    //=========== Filtered Tutee List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tutee} backed by the internal list of
     * {@code versionedTrackO}
     */
    @Override
    public ObservableList<Tutee> getFilteredTuteeList() {
        return filteredTutees;
    }

    @Override
    public void updateFilteredTuteeList(Predicate<Tutee> predicate) {
        requireNonNull(predicate);
        filteredTutees.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return trackO.equals(other.trackO)
                && userPrefs.equals(other.userPrefs)
                && filteredTutees.equals(other.filteredTutees);
    }

}
