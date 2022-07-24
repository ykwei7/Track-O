package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_DAY_OF_WEEK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_HOURLY_RATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_DAY_OF_WEEK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_DAY_OF_WEEK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_END_TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_END_TIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_HOURLY_RATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_HOURLY_RATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_START_TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_START_TIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_DAY_OF_WEEK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_END_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_HOURLY_RATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_START_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_SUBJECT_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Time;

public class AddLessonCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);

    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser,
                LESSON_SUBJECT_DESC_AMY + LESSON_DAY_OF_WEEK_DESC_AMY + LESSON_START_TIME_DESC_AMY
                        + LESSON_END_TIME_DESC_AMY + LESSON_HOURLY_RATE_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // only some fields specified
        assertParseFailure(parser,
                LESSON_SUBJECT_DESC_AMY + LESSON_DAY_OF_WEEK_DESC_AMY + LESSON_START_TIME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser,
                "-5" + LESSON_SUBJECT_DESC_AMY + LESSON_DAY_OF_WEEK_DESC_AMY + LESSON_START_TIME_DESC_AMY
                        + LESSON_END_TIME_DESC_AMY + LESSON_HOURLY_RATE_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser,
                "0" + LESSON_SUBJECT_DESC_AMY + LESSON_DAY_OF_WEEK_DESC_AMY + LESSON_START_TIME_DESC_AMY
                        + LESSON_END_TIME_DESC_AMY + LESSON_HOURLY_RATE_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser,
                "1 some random string" + LESSON_SUBJECT_DESC_AMY + LESSON_DAY_OF_WEEK_DESC_AMY
                        + LESSON_START_TIME_DESC_AMY + LESSON_END_TIME_DESC_AMY + LESSON_HOURLY_RATE_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser,
                "1 i/ string" + LESSON_SUBJECT_DESC_AMY + LESSON_DAY_OF_WEEK_DESC_AMY
                        + LESSON_START_TIME_DESC_AMY + LESSON_END_TIME_DESC_AMY + LESSON_HOURLY_RATE_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, INDEX_OUT_OF_BOUNDS, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddLessonCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_invalidValue_failure() {

        assertParseFailure(parser,
                "1" + INVALID_LESSON_SUBJECT_DESC + LESSON_DAY_OF_WEEK_DESC_AMY
                        + LESSON_START_TIME_DESC_AMY + LESSON_END_TIME_DESC_AMY + LESSON_HOURLY_RATE_DESC_AMY,
                Subject.MESSAGE_CONSTRAINTS); // invalid subject

        assertParseFailure(parser,
                "1" + LESSON_SUBJECT_DESC_AMY + INVALID_LESSON_DAY_OF_WEEK_DESC
                        + LESSON_START_TIME_DESC_AMY + LESSON_END_TIME_DESC_AMY + LESSON_HOURLY_RATE_DESC_AMY,
                Time.MESSAGE_CONSTRAINTS_INVALID_DAY); // invalid day of week

        assertParseFailure(parser,
                "1" + LESSON_SUBJECT_DESC_AMY + LESSON_DAY_OF_WEEK_DESC_AMY
                        + INVALID_LESSON_START_TIME_DESC + LESSON_END_TIME_DESC_AMY + LESSON_HOURLY_RATE_DESC_AMY,
                Time.MESSAGE_CONSTRAINTS_INVALID_LOCALTIME); // invalid start time

        assertParseFailure(parser,
                "1" + LESSON_SUBJECT_DESC_AMY + LESSON_DAY_OF_WEEK_DESC_AMY
                        + LESSON_START_TIME_DESC_AMY + INVALID_LESSON_END_TIME_DESC + LESSON_HOURLY_RATE_DESC_AMY,
                Time.MESSAGE_CONSTRAINTS_INVALID_LOCALTIME); // invalid end time

        assertParseFailure(parser,
                "1" + LESSON_SUBJECT_DESC_AMY + LESSON_DAY_OF_WEEK_DESC_AMY
                        + LESSON_START_TIME_DESC_AMY + LESSON_END_TIME_DESC_AMY + INVALID_LESSON_HOURLY_RATE_DESC,
                Lesson.MESSAGE_CONSTRAINTS_INVALID_HOURLY_RATE_FORMAT); // invalid hourly rate

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + INVALID_LESSON_SUBJECT_DESC + INVALID_LESSON_DAY_OF_WEEK_DESC
                        + INVALID_LESSON_START_TIME_DESC + LESSON_END_TIME_DESC_AMY + LESSON_HOURLY_RATE_DESC_AMY,
                Subject.MESSAGE_CONSTRAINTS); // invalid subject

    }

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Index targetIndex = INDEX_SECOND_TUTEE;
        String userInput = targetIndex.getOneBased() + LESSON_SUBJECT_DESC_BOB + LESSON_DAY_OF_WEEK_DESC_BOB
                + LESSON_START_TIME_DESC_BOB + LESSON_END_TIME_DESC_BOB + LESSON_HOURLY_RATE_DESC_BOB;

        Subject subject = ParserUtil.parseSubject(VALID_LESSON_SUBJECT_BOB);
        DayOfWeek dayOfWeek = ParserUtil.parseDayOfWeek(VALID_LESSON_DAY_OF_WEEK_BOB);
        LocalTime startTime = ParserUtil.parseLocalTime(VALID_LESSON_START_TIME_BOB);
        LocalTime endTime = ParserUtil.parseLocalTime(VALID_LESSON_END_TIME_BOB);
        double hourlyRate = ParserUtil.parseHourlyRate(VALID_LESSON_HOURLY_RATE_BOB);

        AddLessonCommand expectedCommand = new AddLessonCommand(targetIndex, subject, dayOfWeek,
                startTime, endTime, hourlyRate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
