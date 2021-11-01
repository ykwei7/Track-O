package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURLY_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_RECEIVED_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TrackO;
import seedu.address.model.tutee.CollectivePredicate;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.EditTuteeDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_SCHOOL_AMY = "scgs";
    public static final String VALID_SCHOOL_BOB = "St. Joseph's Institution";
    public static final String VALID_LEVEL_AMY = "p1";
    public static final String VALID_LEVEL_BOB = "s2";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_PRACTICAL = "practical";
    public static final String VALID_TAG_RESCHEDULED = "rescheduled";
    public static final String VALID_REMARK_AMY = "Good progress";
    public static final String VALID_REMARK_BOB = "Need to work on Math";

    public static final String VALID_LESSON_SUBJECT_AMY = "Economics";
    public static final String VALID_LESSON_SUBJECT_BOB = "Math";
    public static final String VALID_LESSON_DAY_OF_WEEK_AMY = "3";
    public static final String VALID_LESSON_DAY_OF_WEEK_BOB = "6";
    public static final String VALID_LESSON_START_TIME_AMY = "11:30";
    public static final String VALID_LESSON_START_TIME_BOB = "15:00";
    public static final String VALID_LESSON_END_TIME_AMY = "12:30";
    public static final String VALID_LESSON_END_TIME_BOB = "16:30";
    public static final String VALID_LESSON_HOURLY_RATE_AMY = "40";
    public static final String VALID_LESSON_HOURLY_RATE_BOB = "45.50";
    public static final String VALID_LESSON_INDEX = "1";

    public static final String VALID_PAYMENT_AMOUNT_AMY = "0.00";
    public static final String VALID_PAYMENT_AMOUNT_BOB = "200.00";
    public static final String VALID_PAYMENT_DATE_AMY = "15-10-2022";
    public static final String VALID_PAYMENT_DATE_BOB = "02-05-2022";
    public static final String VALID_PAYMENT_RECV_DATE_AMY = "03-10-2022";
    public static final String VALID_PAYMENT_RECV_DATE_BOB = "";
    public static final String VALID_LESSON_INDEX_AMY = "1";
    public static final String VALID_LESSON_INDEX_BOB = "3";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String SCHOOL_DESC_AMY = " " + PREFIX_SCHOOL + VALID_SCHOOL_AMY;
    public static final String SCHOOL_DESC_BOB = " " + PREFIX_SCHOOL + VALID_SCHOOL_BOB;
    public static final String LEVEL_DESC_AMY = " " + PREFIX_LEVEL + VALID_LEVEL_AMY;
    public static final String LEVEL_DESC_BOB = " " + PREFIX_LEVEL + VALID_LEVEL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_RESCHEDULED;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_PRACTICAL;

    public static final String LESSON_SUBJECT_DESC_AMY = " " + PREFIX_SUBJECT + VALID_LESSON_SUBJECT_AMY;
    public static final String LESSON_SUBJECT_DESC_BOB = " " + PREFIX_SUBJECT + VALID_LESSON_SUBJECT_BOB;
    public static final String LESSON_DAY_OF_WEEK_DESC_AMY = " " + PREFIX_DAY_OF_WEEK + VALID_LESSON_DAY_OF_WEEK_AMY;
    public static final String LESSON_DAY_OF_WEEK_DESC_BOB = " " + PREFIX_DAY_OF_WEEK + VALID_LESSON_DAY_OF_WEEK_BOB;
    public static final String LESSON_START_TIME_DESC_AMY = " " + PREFIX_START_TIME + VALID_LESSON_START_TIME_AMY;
    public static final String LESSON_START_TIME_DESC_BOB = " " + PREFIX_START_TIME + VALID_LESSON_START_TIME_BOB;
    public static final String LESSON_END_TIME_DESC_AMY = " " + PREFIX_END_TIME + VALID_LESSON_END_TIME_AMY;
    public static final String LESSON_END_TIME_DESC_BOB = " " + PREFIX_END_TIME + VALID_LESSON_END_TIME_BOB;
    public static final String LESSON_HOURLY_RATE_DESC_AMY = " " + PREFIX_HOURLY_RATE + VALID_LESSON_HOURLY_RATE_AMY;
    public static final String LESSON_HOURLY_RATE_DESC_BOB = " " + PREFIX_HOURLY_RATE + VALID_LESSON_HOURLY_RATE_BOB;
    public static final String LESSON_INDEX = " " + PREFIX_LESSON + VALID_LESSON_INDEX;

    public static final String PAYMENT_AMOUNT_DESC_AMY = " " + PREFIX_PAYMENT_AMOUNT + VALID_PAYMENT_AMOUNT_AMY;
    public static final String PAYMENT_AMOUNT_DESC_BOB = " " + PREFIX_PAYMENT_AMOUNT + VALID_PAYMENT_AMOUNT_BOB;
    public static final String PAYMENT_DATE_DESC_AMY = " " + PREFIX_PAYMENT_DATE + VALID_PAYMENT_DATE_AMY;
    public static final String PAYMENT_DATE_DESC_BOB = " " + PREFIX_PAYMENT_DATE + VALID_PAYMENT_DATE_BOB;
    public static final String PAYMENT_RECV_DATE_DESC_AMY = " " + PREFIX_PAYMENT_RECEIVED_DATE
            + VALID_PAYMENT_RECV_DATE_AMY;
    public static final String PAYMENT_RECV_DATE_DESC_BOB = " " + PREFIX_PAYMENT_RECEIVED_DATE
            + VALID_PAYMENT_RECV_DATE_BOB;
    public static final String PAYMENT_LESSON_INDEX_AMY = " " + PREFIX_LESSON + VALID_LESSON_INDEX_AMY;
    public static final String PAYMENT_LESSON_INDEX_BOB = " " + PREFIX_LESSON + VALID_LESSON_INDEX_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_SCHOOL_DESC = " " + PREFIX_SCHOOL; //empty string not allowed for schools
    public static final String INVALID_LEVEL_DESC = " " + PREFIX_LEVEL + "p7"; // year of study out of range
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_LESSON_SUBJECT_DESC =
            " " + PREFIX_SUBJECT + "Math&"; // '&' not allowed in subjects
    public static final String INVALID_LESSON_DAY_OF_WEEK_DESC = " " + PREFIX_DAY_OF_WEEK + "8"; // out of range [1, 7]
    public static final String INVALID_LESSON_START_TIME_DESC = " " + PREFIX_START_TIME + "25:30"; // out of range
    public static final String INVALID_LESSON_END_TIME_DESC =
            " " + PREFIX_END_TIME + "20:00!"; // invalid symbol '!'
    public static final String INVALID_LESSON_HOURLY_RATE_DESC =
            " " + PREFIX_HOURLY_RATE + "45.5"; // not expressed in 2dp
    public static final String INVALID_LESSON_INDEX = " " + PREFIX_LESSON + "*"; // not an integer larger than 0

    public static final String INVALID_PAYMENT_AMOUNT_DESC = " " + PREFIX_PAYMENT_AMOUNT + "20!"; // No unknown symbols
    public static final String INVALID_LESSON_INDEX_DESC = " " + PREFIX_LESSON + "0"; // Not a positive integer
    public static final String INVALID_PAYMENT_DATE_DESC = " "
            + PREFIX_PAYMENT_DATE + "15-Oct-2021"; // Invalid date formatting
    public static final String INVALID_PAYMENT_RECV_DATE_DESC = " "
            + PREFIX_PAYMENT_RECEIVED_DATE + "15-Oct-2021"; // Invalid date formatting

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";


    public static final EditCommand.EditTuteeDescriptor DESC_AMY;
    public static final EditCommand.EditTuteeDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditTuteeDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withSchool(VALID_SCHOOL_AMY).withLevel(VALID_LEVEL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_RESCHEDULED).build();
        DESC_BOB = new EditTuteeDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchool(VALID_SCHOOL_BOB).withLevel(VALID_LEVEL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_PRACTICAL, VALID_TAG_RESCHEDULED).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - Track-O, filtered tutee list and selected tutee in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TrackO expectedTrackO = new TrackO(actualModel.getTrackO());
        List<Tutee> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTuteeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTrackO, actualModel.getTrackO());
        assertEquals(expectedFilteredList, actualModel.getFilteredTuteeList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the tutee at the given {@code targetIndex} in the
     * {@code model}'s Track-O.
     */
    public static void showTuteeAtIndex(Model model, Index targetIndex) {
        List<String> emptyKeywordList = Collections.emptyList();
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTuteeList().size());

        Tutee tutee = model.getFilteredTuteeList().get(targetIndex.getZeroBased());
        final String[] splitName = tutee.getName().fullName.split("\\s+");
        model.updateFilteredTuteeList(new CollectivePredicate(Arrays.asList(splitName[0]),
                emptyKeywordList, emptyKeywordList, emptyKeywordList));

        assertEquals(1, model.getFilteredTuteeList().size());
    }

}
