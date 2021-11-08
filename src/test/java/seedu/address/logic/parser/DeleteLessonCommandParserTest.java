package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LESSON_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteLessonCommand;

public class DeleteLessonCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE);

    private DeleteLessonCommandParser parser = new DeleteLessonCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, LESSON_INDEX, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + LESSON_INDEX, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + LESSON_INDEX, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser,
                "1 some random string" + LESSON_INDEX, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser,
                "1 i/ string" + LESSON_INDEX, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INDEX_OUT_OF_BOUNDS, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteLessonCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1" + LESSON_INDEX + INDEX_OUT_OF_BOUNDS,
                MESSAGE_INVALID_LESSON_INDEX);

        assertParseFailure(parser,
                "1" + INVALID_LESSON_INDEX, MESSAGE_INVALID_INDEX); // invalid subject
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Index tuteeIndex = INDEX_SECOND_TUTEE;
        Index lessonIndex = Index.fromOneBased(Integer.parseInt(VALID_LESSON_INDEX));
        String userInput = tuteeIndex.getOneBased() + LESSON_INDEX;

        DeleteLessonCommand expectedCommand = new DeleteLessonCommand(tuteeIndex, lessonIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
