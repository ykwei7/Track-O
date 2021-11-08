package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.EMPTY_STRING;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Address;
import seedu.address.model.tutee.Level;
import seedu.address.model.tutee.Name;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Phone;
import seedu.address.model.tutee.School;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_SCHOOL = " ";
    private static final String INVALID_LEVEL = "@2 ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SUBJECT = "Chemistry%";
    private static final String INVALID_PAYMENT_DECIMALS = "500.1";
    private static final String INVALID_PAYMENT = "5a00";
    private static final String INVALID_PAYMENT_EXCEED_MAXIMUM = "100001";
    private static final String INVALID_PAYMENT_DATE = "abc-123-xyz";
    private static final String INVALID_PAYMENT_DATE_PAST = "07-11-2021";
    private static final String INVALID_OVERDUE = "nope";
    private static final String INVALID_DAY_OF_WEEK_1 = "0";
    private static final String INVALID_DAY_OF_WEEK_2 = "8";
    private static final String INVALID_DAY_OF_WEEK_3 = "Monday";
    private static final String INVALID_LOCAL_TIME = "13:45:00"; // not strictly in "HH:mm" format
    private static final String INVALID_HOURLY_RATE_FORMAT = "150.11";
    private static final String INVALID_HOURLY_RATE_EXCEED_MAXIMUM = "1000.50";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_SCHOOL = "SCGS";
    private static final String VALID_LEVEL = "p5";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_SUBJECT = "Chemistry";
    private static final String VALID_SUBJECT_2 = "Math";
    private static final String VALID_PAYMENT = "500.00";
    private static final String VALID_PAYMENT_DECIMALS = "500.50";
    private static final LocalDate VALID_PAYMENT_DATE = LocalDate.of(2021, 11, 1);
    private static final String VALID_OVERDUE = "yes";
    private static final String VALID_DAY_OF_WEEK = "4";
    private static final String VALID_LOCAL_TIME = "14:20";
    private static final String VALID_HOURLY_RATE = "42.75";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_TUTEE, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TUTEE, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parsePaymentLessonIndex_null_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () ->
                ParserUtil.parseLessonIndex(EMPTY_STRING));
    }

    @Test
    public void parsePaymentValue_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePaymentValue((String) null));
    }

    @Test
    public void parsePaymentValue_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, Payment.FORMAT_CONSTRAINTS_MESSAGE, () ->
                ParserUtil.parsePaymentValue(INVALID_PAYMENT));
    }

    @Test
    public void parsePaymentValue_invalidValueWithDecimals_throwsParseException() {
        assertThrows(ParseException.class, Payment.DECIMAL_CONSTRAINTS_MESSAGE, () ->
                ParserUtil.parsePaymentValue(INVALID_PAYMENT_DECIMALS));
    }

    @Test
    public void parsePaymentValue_validValue_returnsPayment() throws ParseException {
        Payment expectedPayment = new Payment(VALID_PAYMENT, VALID_PAYMENT_DATE);
        String expectedPaymentValue = expectedPayment.getValue();
        assertEquals(expectedPaymentValue, ParserUtil.parsePaymentValue(VALID_PAYMENT));
    }

    @Test
    public void parsePaymentValue_validValueWithDecimals_returnsPayment() throws ParseException {
        Payment expectedPayment = new Payment(VALID_PAYMENT_DECIMALS, VALID_PAYMENT_DATE);
        String expectedPaymentValue = expectedPayment.getValue();
        assertEquals(expectedPaymentValue, ParserUtil.parsePaymentValue(VALID_PAYMENT_DECIMALS));
    }

    @Test
    public void parsePaymentValue_validValueWithWhitespace_returnsTrimmedPayment() throws Exception {
        String paymentWithWhitespace = WHITESPACE + VALID_PAYMENT_DECIMALS + WHITESPACE;
        Payment expectedPayment = new Payment(VALID_PAYMENT_DECIMALS, VALID_PAYMENT_DATE);
        String expectedPaymentValue = expectedPayment.getValue();
        assertEquals(expectedPaymentValue, ParserUtil.parsePaymentValue(paymentWithWhitespace));
    }

    @Test
    public void parsePaymentValue_exceedMaximumValue_throwsParseException() {
        assertThrows(ParseException.class, Payment.AMOUNT_CONSTRAINTS_MESSAGE, () ->
                ParserUtil.parsePaymentValue(INVALID_PAYMENT_EXCEED_MAXIMUM));
    }

    @Test
    public void parsePayByDate_emptyDate_returnsNull() throws ParseException {
        assertEquals(null, ParserUtil.parsePayByDate(""));
    }

    @Test
    public void parsePayByDate_invalidDateFormat_throwsParseException() {
        assertThrows(ParseException.class, Payment.DATE_CONSTRAINTS_MESSAGE, () ->
                ParserUtil.parsePayByDate(INVALID_PAYMENT_DATE));
    }

    @Test
    public void parsePayByDate_dateInThePast_throwsParseException() {
        assertThrows(ParseException.class, Payment.DATE_CONSTRAINTS_MESSAGE, () ->
                ParserUtil.parsePayByDate(INVALID_PAYMENT_DATE_PAST));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseSchool_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSchool((String) null));
    }

    @Test
    public void parseSchool_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSchool(INVALID_SCHOOL));
    }

    @Test
    public void parseSchool_validValueWithoutWhitespace_returnsSchool() throws Exception {
        School expectedSchool = new School(VALID_SCHOOL);
        assertEquals(expectedSchool, ParserUtil.parseSchool(VALID_SCHOOL));
    }

    @Test
    public void parseSchool_validValueWithWhitespace_returnsTrimmedSchool() throws Exception {
        String schoolWithWhitespace = WHITESPACE + VALID_SCHOOL + WHITESPACE;
        School expectedSchool = new School(VALID_SCHOOL);
        assertEquals(expectedSchool, ParserUtil.parseSchool(schoolWithWhitespace));
    }

    @Test
    public void parseLevel_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLevel((String) null));
    }

    @Test
    public void parseLevel_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLevel(INVALID_LEVEL));
    }

    @Test
    public void parseLevel_validValueWithoutWhitespace_returnsLevel() throws Exception {
        Level expectedLevel = new Level(VALID_LEVEL);
        assertEquals(expectedLevel, ParserUtil.parseLevel(VALID_LEVEL));
    }

    @Test
    public void parseLevel_validValueWithWhitespace_returnsTrimmedLevel() throws Exception {
        String levelWithWhitespace = WHITESPACE + VALID_LEVEL + WHITESPACE;
        Level expectedLevel = new Level(VALID_LEVEL);
        assertEquals(expectedLevel, ParserUtil.parseLevel(levelWithWhitespace));
    }

    @Test
    public void parseMultipleSubjects_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMultipleSubjects((String) null));
    }

    @Test
    public void parseMultipleSubjects_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleSubjects(INVALID_SUBJECT));
    }

    @Test
    public void parseMultipleSubjects_validValueWithoutWhitespace_returnsLevel() throws Exception {
        assertEquals(VALID_SUBJECT, ParserUtil.parseMultipleSubjects(VALID_SUBJECT)[0]);
    }

    @Test
    public void parseMultipleSubjects_validValueWithWhitespace_returnsTrimmedLevel() throws Exception {
        String levelWithWhitespace = WHITESPACE + VALID_SUBJECT + WHITESPACE;
        assertEquals(VALID_SUBJECT, ParserUtil.parseMultipleSubjects(levelWithWhitespace)[0]);
    }

    @Test
    public void parseMultipleSubjects_multipleValidValuesWithWhitespace_returnsTrimmedLevel() throws Exception {
        String levelWithWhitespace = WHITESPACE + VALID_SUBJECT + WHITESPACE + VALID_SUBJECT_2;
        // 1st Subject
        assertEquals(VALID_SUBJECT, ParserUtil.parseMultipleSubjects(levelWithWhitespace)[0]);

        // 2nd Subject
        assertEquals(VALID_SUBJECT_2, ParserUtil.parseMultipleSubjects(levelWithWhitespace)[1]);
    }

    @Test
    public void parseMultipleSubjects_multipleValidValuesWithInvalidValue_throwsParseException() {
        String levelWithWhitespace = WHITESPACE + VALID_SUBJECT + WHITESPACE + INVALID_SUBJECT;
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleSubjects(levelWithWhitespace));
    }

    @Test
    public void parseIsOverdue_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIsOverdue(INVALID_OVERDUE));
    }

    @Test
    public void parseIsOverdue_multipleValues_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIsOverdue(VALID_OVERDUE + " " + VALID_OVERDUE));
    }

    @Test
    public void parseIsOverdue_validValueWithWhitespace_returnsTrimmedOverdue() throws Exception {
        String overdueWithWhiteSpace = WHITESPACE + VALID_OVERDUE + WHITESPACE;
        assertEquals("true", ParserUtil.parseIsOverdue(overdueWithWhiteSpace)[0]);
    }

    @Test
    public void parseIsOverdue_yesWithoutWhitespace_returnsOverdue() throws Exception {
        assertEquals("true", ParserUtil.parseIsOverdue(VALID_OVERDUE)[0]);
    }

    @Test
    public void parseIsOverdue_noWithoutWhitespace_returnsNotOverdue() throws Exception {
        assertEquals("false", ParserUtil.parseIsOverdue("no")[0]);
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseDayOfWeek_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDayOfWeek((String) null));
    }

    @Test
    public void parseDayOfWeek_invalidDay_throwsParseException() {
        assertThrows(ParseException.class, Time.MESSAGE_CONSTRAINTS_INVALID_DAY, () ->
                ParserUtil.parseDayOfWeek(INVALID_DAY_OF_WEEK_1));
        assertThrows(ParseException.class, Time.MESSAGE_CONSTRAINTS_INVALID_DAY, () ->
                ParserUtil.parseDayOfWeek(INVALID_DAY_OF_WEEK_2));
        assertThrows(ParseException.class, Time.MESSAGE_CONSTRAINTS_INVALID_DAY, () ->
                ParserUtil.parseDayOfWeek(INVALID_DAY_OF_WEEK_3));
    }

    @Test
    public void parseDayOfWeek_validDay_returnsDayOfWeek() throws ParseException {
        DayOfWeek expectedDay = DayOfWeek.of(Integer.parseInt(VALID_DAY_OF_WEEK));
        assertEquals(expectedDay, ParserUtil.parseDayOfWeek(VALID_DAY_OF_WEEK));
    }

    @Test
    public void parseLocalTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocalTime((String) null));
    }

    @Test
    public void parseLocalTime_invalidLocalTime_throwsParseException() {
        assertThrows(ParseException.class, Time.MESSAGE_CONSTRAINTS_INVALID_LOCALTIME, () ->
                ParserUtil.parseLocalTime(INVALID_LOCAL_TIME));
    }

    @Test
    public void parseLocalTime_validLocalTime_returnsLocalTime() throws ParseException {
        LocalTime expectedLocalTime = LocalTime.parse(VALID_LOCAL_TIME);
        assertEquals(expectedLocalTime, ParserUtil.parseLocalTime(VALID_LOCAL_TIME));
    }

    @Test
    public void parseHourlyRate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHourlyRate((String) null));
    }

    @Test
    public void parseHourlyRate_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, Lesson.MESSAGE_CONSTRAINTS_INVALID_HOURLY_RATE_FORMAT, () ->
                ParserUtil.parseHourlyRate(INVALID_HOURLY_RATE_FORMAT));
    }

    @Test
    public void parseHourlyRate_exceedMaximumHourlyRate_throwsParseException() {
        assertThrows(ParseException.class, Lesson.MESSAGE_CONSTRAINTS_MAXIMUM_HOURLY_RATE_EXCEEDED, () ->
                ParserUtil.parseHourlyRate(INVALID_HOURLY_RATE_EXCEED_MAXIMUM));
    }

    @Test
    public void parseHourlyRate_validHourlyRate_returnsHourlyRate() throws ParseException {
        double expectedHourlyRate = Double.parseDouble(VALID_HOURLY_RATE);
        assertEquals(expectedHourlyRate, ParserUtil.parseHourlyRate(VALID_HOURLY_RATE));
    }
}
