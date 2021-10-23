package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.tutee.CollectivePredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();
    private List<String> emptyKeywordList = Collections.emptyList();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new CollectivePredicate(Collections.singletonList("Alice"),
                        emptyKeywordList, emptyKeywordList, emptyKeywordList));
        assertParseSuccess(parser, " n/Alice", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t \t", expectedFindCommand);

        //keyword for level
        expectedFindCommand =
                new FindCommand(new CollectivePredicate(emptyKeywordList,
                        Collections.singletonList("p5"), emptyKeywordList, emptyKeywordList));
        assertParseSuccess(parser, " l/p5", expectedFindCommand);

        //keyword for subject
        expectedFindCommand =
                new FindCommand(new CollectivePredicate(emptyKeywordList,
                        emptyKeywordList, Collections.singletonList("Math"), emptyKeywordList));
        assertParseSuccess(parser, " subject/Math", expectedFindCommand);

        //keyword for overdue
        expectedFindCommand =
                new FindCommand(new CollectivePredicate(emptyKeywordList,
                        emptyKeywordList, emptyKeywordList, Collections.singletonList("true")));
        assertParseSuccess(parser, " overdue/true", expectedFindCommand);
    }

}
