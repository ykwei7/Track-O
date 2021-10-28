package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OVERDUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutee.CollectivePredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_NAME, PREFIX_OVERDUE);
        if (isMissingAllPrefixes(argMultimap, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_NAME, PREFIX_OVERDUE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = new String[0];
        String[] levelNames = new String[0];
        String[] subjectNames = new String[0];
        String[] overdueCheck = new String[0];

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            nameKeywords = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get())
                    .toString().split("\\s+");
        }
        if (argMultimap.getValue(PREFIX_LEVEL).isPresent()) {
            try {
                levelNames = new String[] {ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL).get()
                        .toLowerCase(Locale.ROOT)).getValue()};
            } catch (ParseException pe) {
                throw new ParseException(FindCommand.MESSAGE_LEVEL_CONSTRAINT + "\n\n" + pe.getMessage());
            }
        }
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            subjectNames = ParserUtil.parseMultipleSubjects(argMultimap.getValue(PREFIX_SUBJECT).get());
        }
        if (argMultimap.getValue(PREFIX_OVERDUE).isPresent()) {
            overdueCheck = ParserUtil.parseIsOverdue(argMultimap.getValue(PREFIX_OVERDUE).get());
        }
        CollectivePredicate combinedPredicate = new CollectivePredicate(Arrays.asList(nameKeywords),
                Arrays.asList(levelNames), Arrays.asList(subjectNames), Arrays.asList(overdueCheck));
        return new FindCommand(combinedPredicate);
    }

    /**
     * Returns true if none of the prefixes has a {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isMissingAllPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).noneMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
