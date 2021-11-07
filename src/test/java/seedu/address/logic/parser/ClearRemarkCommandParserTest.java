package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_BOUNDS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearRemarkCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ClearRemarkCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ClearRemarkCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ClearRemarkCommandParserTest {

    private ClearRemarkCommandParser parser = new ClearRemarkCommandParser();

    @Test
    public void parse_validArgs_returnsGetCommand() {
        assertParseSuccess(parser, "1", new ClearRemarkCommand(INDEX_FIRST_TUTEE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        assertParseFailure(parser, INDEX_OUT_OF_BOUNDS, MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);

        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearRemarkCommand.MESSAGE_USAGE));
    }
}
