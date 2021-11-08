package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_RECEIVED_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.paymentcommand.PaymentAddCommand;
import seedu.address.logic.commands.paymentcommand.PaymentCommand;
import seedu.address.logic.commands.paymentcommand.PaymentReceiveCommand;
import seedu.address.logic.commands.paymentcommand.PaymentSetAmountCommand;
import seedu.address.logic.commands.paymentcommand.PaymentSetDateCommand;
import seedu.address.logic.parser.exceptions.IndexOutOfBoundsException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutee.Payment;

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PaymentCommand.MESSAGE_USAGE_ALL), pe);
        } catch (IndexOutOfBoundsException ie) {
            throw new ParseException(MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX, ie);
        }

        if (argMultimap.getValue(PREFIX_LESSON).isPresent()) {
            return parsePaymentAddCmd(index, argMultimap);
        } else if (argMultimap.getValue(PREFIX_PAYMENT_AMOUNT).isPresent()) {
            return parsePaymentSetAmountCmd(index, argMultimap);
        } else if (argMultimap.getValue(PREFIX_PAYMENT_DATE).isPresent()) {
            return parsePaymentSetDateCmd(index, argMultimap);
        } else if (argMultimap.getValue(PREFIX_PAYMENT_RECEIVED_DATE).isPresent()) {
            return parsePaymentReceiveCmd(index, argMultimap);
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


    private static PaymentAddCommand parsePaymentAddCmd(Index index, ArgumentMultimap argMultimap)
            throws ParseException {
        if (anyPrefixesPresent(argMultimap, PREFIX_PAYMENT_AMOUNT, PREFIX_PAYMENT_DATE,
                PREFIX_PAYMENT_RECEIVED_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PaymentCommand.MESSAGE_USAGE_ALL));
        }
        Index lessonIndex = ParserUtil.parseLessonIndex(argMultimap.getValue(PREFIX_LESSON).get());
        return new PaymentAddCommand(index, lessonIndex);
    }


    private static PaymentSetAmountCommand parsePaymentSetAmountCmd(Index index, ArgumentMultimap argMultimap)
            throws ParseException {
        if (anyPrefixesPresent(argMultimap, PREFIX_LESSON , PREFIX_PAYMENT_DATE, PREFIX_PAYMENT_RECEIVED_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PaymentCommand.MESSAGE_USAGE_ALL));
        }
        String paymentValueToSet = ParserUtil.parsePaymentValue(argMultimap.getValue(PREFIX_PAYMENT_AMOUNT).get());
        return new PaymentSetAmountCommand(index, paymentValueToSet);
    }

    private static PaymentSetDateCommand parsePaymentSetDateCmd(Index index, ArgumentMultimap argMultimap)
            throws ParseException {
        if (anyPrefixesPresent(argMultimap, PREFIX_PAYMENT_AMOUNT, PREFIX_LESSON, PREFIX_PAYMENT_RECEIVED_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PaymentCommand.MESSAGE_USAGE_ALL));
        }
        // Null value for payByDate is disallowed in the constructor for PaymentSetDateCommand
        String userInput = argMultimap.getValue(PREFIX_PAYMENT_DATE).get();
        LocalDate paymentPayByDateToSet = ParserUtil.parsePayByDate(userInput);
        if (paymentPayByDateToSet == null) {
            throw new ParseException(Payment.DATE_CONSTRAINTS_MESSAGE);
        }
        return new PaymentSetDateCommand(index, paymentPayByDateToSet);
    }


    private static PaymentReceiveCommand parsePaymentReceiveCmd(Index index, ArgumentMultimap argMultimap)
            throws ParseException {
        if (anyPrefixesPresent(argMultimap, PREFIX_PAYMENT_AMOUNT, PREFIX_PAYMENT_DATE, PREFIX_LESSON)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PaymentCommand.MESSAGE_USAGE_ALL));
        }
        // Null value for payByDate is allowed here
        LocalDate paymentPayByDateToSet =
                ParserUtil.parsePayByDate(argMultimap.getValue(PREFIX_PAYMENT_RECEIVED_DATE).get());
        return new PaymentReceiveCommand(index, paymentPayByDateToSet);
    }

}
