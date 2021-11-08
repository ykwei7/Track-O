package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LESSON_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.TrackOParser.anyPrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.logic.parser.exceptions.IndexOutOfBoundsException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteLessonCommand object
 */
public class DeleteLessonCommandParser implements Parser<DeleteLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLessonCommand
     * and returns an DeleteLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLessonCommand parse(String args) throws ParseException, IndexOutOfBoundsException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LESSON);

        Index tuteeIndex;

        try {
            tuteeIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLessonCommand.MESSAGE_USAGE), pe);
        } catch (IndexOutOfBoundsException ie) {
            if (anyPrefixesPresent(argMultimap, PREFIX_LESSON)) {
                throw new ParseException(MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX, ie);
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLessonCommand.MESSAGE_USAGE), ie);
        }

        if (argMultimap.getValue(PREFIX_LESSON).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
        }
        Index lessonIndex;

        try {
            lessonIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LESSON).get());
        } catch (IndexOutOfBoundsException ie) {
            throw new ParseException(MESSAGE_INVALID_LESSON_INDEX, ie);
        }
        return new DeleteLessonCommand(tuteeIndex, lessonIndex);
    }

}
