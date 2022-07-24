package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURLY_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.TrackOParser.arePrefixesPresent;

import java.time.DayOfWeek;
import java.time.LocalTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.IndexOutOfBoundsException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Subject;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_DAY_OF_WEEK,
                        PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_HOURLY_RATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE), pe);
        } catch (IndexOutOfBoundsException ie) {
            if (arePrefixesPresent(argMultimap, PREFIX_SUBJECT, PREFIX_DAY_OF_WEEK,
                    PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_HOURLY_RATE)) {
                throw new ParseException(MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX, ie);
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE), ie);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT, PREFIX_DAY_OF_WEEK,
                PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_HOURLY_RATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get());
        DayOfWeek dayOfWeek = ParserUtil.parseDayOfWeek(argMultimap.getValue(PREFIX_DAY_OF_WEEK).get());
        LocalTime startTime = ParserUtil.parseLocalTime(argMultimap.getValue(PREFIX_START_TIME).get());
        LocalTime endTime = ParserUtil.parseLocalTime(argMultimap.getValue(PREFIX_END_TIME).get());
        double hourlyRate = ParserUtil.parseHourlyRate(argMultimap.getValue(PREFIX_HOURLY_RATE).get());

        return new AddLessonCommand(index, subject, dayOfWeek, startTime, endTime, hourlyRate);
    }

}
