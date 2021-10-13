package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutees.ALICE;
import static seedu.address.testutil.TypicalTutees.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.tutee.NameContainsKeywordsPredicate;
import seedu.address.testutil.TrackOBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new TrackO(), new TrackO(modelManager.getTrackO()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTrackOFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTrackOFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setTrackOFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setTrackOFilePath(null));
    }

    @Test
    public void setTrackOFilePath_validPath_setsTrackOFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setTrackOFilePath(path);
        assertEquals(path, modelManager.getTrackOFilePath());
    }

    @Test
    public void hasTutee_nullTutee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTutee(null));
    }

    @Test
    public void hasTutee_tuteeNotInTrackO_returnsFalse() {
        assertFalse(modelManager.hasTutee(ALICE));
    }

    @Test
    public void hasTutee_tuteeInTrackO_returnsTrue() {
        modelManager.addTutee(ALICE);
        assertTrue(modelManager.hasTutee(ALICE));
    }

    @Test
    public void getFilteredTuteeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTuteeList().remove(0));
    }

    @Test
    public void equals() {
        TrackO trackO = new TrackOBuilder().withTutee(ALICE).withTutee(BENSON).build();
        TrackO differentTrackO = new TrackO();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(trackO, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(trackO, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different trackO -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTrackO, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredTuteeList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(trackO, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTrackOFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(trackO, differentUserPrefs)));
    }
}
