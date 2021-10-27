package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.tutee.Tutee;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Tutee> PREDICATE_SHOW_ALL_TUTEES = unused -> true;

    /**
     * Returns the user's schedule.
     */
    Schedule getSchedule();

    /**
     * Clears the user's schedule.
     */
    void clearSchedule();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' Track-O file path.
     */
    Path getTrackOFilePath();

    /**
     * Sets the user prefs' Track-O file path.
     */
    void setTrackOFilePath(Path trackOFilePath);

    /**
     * Replaces Track-O data with the data in {@code trackO}.
     */
    void setTrackO(ReadOnlyTrackO trackO);

    /** Returns Track-O */
    ReadOnlyTrackO getTrackO();

    /**
     * Returns true if a tutee with the same identity as {@code tutee} exists in Track-O.
     */
    boolean hasTutee(Tutee tutee);

    /**
     * Deletes the given tutee.
     * The tutee must exist in Track-O.
     */
    void deleteTutee(Tutee target);

    /**
     * Adds the given tutee.
     * {@code tutee} must not already exist in Track-O.
     */
    void addTutee(Tutee tutee);

    /**
     * Replaces the given tutee {@code target} with {@code editedTutee}.
     * {@code target} must exist in Track-O.
     * The tutee identity of {@code editedTutee} must not be the same as another existing tutee in Track-O.
     */
    void setTutee(Tutee target, Tutee editedTutee);

    /** Returns an unmodifiable view of the filtered tutee list */
    ObservableList<Tutee> getFilteredTuteeList();

    /**
     * Updates the filter of the filtered tutee list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTuteeList(Predicate<Tutee> predicate);
}
