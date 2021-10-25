package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.tutee.CollectivePredicate;
import seedu.address.model.tutee.Level;

public class FindCommandParserTest {

    private static final List<String> EMPTY_KEYWORD_LIST = Collections.emptyList();
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidLevel_throwsParseException() {
        assertParseFailure(parser, " l/p8", FindCommand.MESSAGE_LEVEL_CONSTRAINT + "\n\n" + Level.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand1 =
                new FindCommand(new CollectivePredicate(Collections.singletonList("Alice"),
                        EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST));
        assertParseSuccess(parser, " n/Alice", expectedFindCommand1);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t \t", expectedFindCommand1);

        //keyword for level
        FindCommand expectedFindCommand2 =
                new FindCommand(new CollectivePredicate(EMPTY_KEYWORD_LIST,
                        Collections.singletonList("p5"), EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST));
        assertParseSuccess(parser, " l/p5", expectedFindCommand2);

        //keyword for subject
        FindCommand expectedFindCommand3 =
                new FindCommand(new CollectivePredicate(EMPTY_KEYWORD_LIST,
                        EMPTY_KEYWORD_LIST, Collections.singletonList("Math"), EMPTY_KEYWORD_LIST));
        assertParseSuccess(parser, " subject/Math", expectedFindCommand3);

        //keyword for overdue
        FindCommand expectedFindCommand4 =
                new FindCommand(new CollectivePredicate(EMPTY_KEYWORD_LIST,
                        EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, Collections.singletonList("true")));
        assertParseSuccess(parser, " overdue/yes", expectedFindCommand4);
    }

}
