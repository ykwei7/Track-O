package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;


import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PaymentCommand.*;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PaymentCommand object
 */
public class PaymentCommandParser implements Parser<PaymentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PaymentCommand
     * and returns an PaymentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PaymentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PAYMENT_AMOUNT, PREFIX_LESSON,
                        PREFIX_PAYMENT_DATE, PREFIX_PAYMENT_RECEIVED_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_INDEX_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_LESSON).isPresent()) {
            if (anyPrefixesPresent(argMultimap, PREFIX_PAYMENT_AMOUNT, PREFIX_PAYMENT_DATE,
                    PREFIX_PAYMENT_RECEIVED_DATE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE));
            }

            return new PaymentAddCommand(index);
        }
        if (argMultimap.getValue(PREFIX_PAYMENT_AMOUNT).isPresent()) {
            if (anyPrefixesPresent(argMultimap,PREFIX_LESSON , PREFIX_PAYMENT_DATE, PREFIX_PAYMENT_RECEIVED_DATE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE));
            }
            String paymentValue = ParserUtil.parsePaymentValue(argMultimap.getValue(PREFIX_PAYMENT_AMOUNT).get());
            return new PaymentSetAmountCommand(index, paymentValue);
        }
        if (argMultimap.getValue(PREFIX_PAYMENT_DATE).isPresent()) {
            if (anyPrefixesPresent(argMultimap, PREFIX_PAYMENT_AMOUNT, PREFIX_LESSON, PREFIX_PAYMENT_RECEIVED_DATE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE));
            }
            return new PaymentSetDateCommand(index);
        }
        if (argMultimap.getValue(PREFIX_PAYMENT_RECEIVED_DATE).isPresent()) {
            if (anyPrefixesPresent(argMultimap, PREFIX_PAYMENT_AMOUNT, PREFIX_PAYMENT_DATE, PREFIX_LESSON)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE));
            }
            return new PaymentReceiveCommand(index);
        }
        return new PaymentCommand(index);
    }

    /**
     * Returns true if any of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
