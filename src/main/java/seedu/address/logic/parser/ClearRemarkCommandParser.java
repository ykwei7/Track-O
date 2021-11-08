package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearRemarkCommand;
import seedu.address.logic.parser.exceptions.IndexOutOfBoundsException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearRemarkCommand object
 */
public class ClearRemarkCommandParser implements Parser<ClearRemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearRemarkCommand
     * and returns a ClearRemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearRemarkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ClearRemarkCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearRemarkCommand.MESSAGE_USAGE), pe);
        } catch (IndexOutOfBoundsException ie) {
            throw new ParseException(MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX, ie);
        }
    }

}
