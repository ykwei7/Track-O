package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TUTEES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTutees.BENSON;
import static seedu.address.testutil.TypicalTutees.CARL;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.tutee.CollectivePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private static final List<String> EMPTY_KEYWORD_LIST = Collections.emptyList();

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTrackO(), new UserPrefs());

    public FindCommandTest() throws ScheduleClashException {
    }

    @Test
    public void equals() {
        CollectivePredicate firstPredicate =
                new CollectivePredicate(Collections.singletonList("first"),
                        EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        CollectivePredicate secondPredicate =
                new CollectivePredicate(Collections.singletonList("second"),
                        EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different tutee -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTuteeFound() {
        String expectedMessage = String.format(MESSAGE_TUTEES_LISTED_OVERVIEW, 0);
        String keyword = " ";
        List<String> nameList = Arrays.asList(keyword.split("\\s+"));
        CollectivePredicate predicate = new CollectivePredicate(nameList,
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTuteeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTuteeList());
    }

    @Test
    public void execute_multipleKeywords_tuteesFound() {
        String expectedMessage = String.format(MESSAGE_TUTEES_LISTED_OVERVIEW, 1);
        String keyword = "Physics Chemistry";
        List<String> subjectList = Arrays.asList(keyword.split("\\s+"));
        CollectivePredicate predicate = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, subjectList, EMPTY_KEYWORD_LIST);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTuteeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredTuteeList());
    }

    @Test
    public void execute_multipleKeywords_multipleTuteesFound() {
        String expectedMessage = String.format(MESSAGE_TUTEES_LISTED_OVERVIEW, 2);
        String keyword = "Chemistry";
        List<String> subjectList = Arrays.asList(keyword.split("\\s+"));
        CollectivePredicate predicate = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, subjectList, EMPTY_KEYWORD_LIST);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTuteeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(BENSON, CARL), model.getFilteredTuteeList());
    }
}
