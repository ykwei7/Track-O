package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LESSON_INDEX;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.IndexOutOfBoundsException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Time;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Address;
import seedu.address.model.tutee.Level;
import seedu.address.model.tutee.Name;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Phone;
import seedu.address.model.tutee.School;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The index provided is invalid.";

    public static final String EMPTY_STRING = "";

    /**
     * Returns true if string has length less than or equal to 9 after trimming leading zeroes.
     *
     * @param trimmedIndex trimmedIndex
     * @return whether index is within the acceptable range
     */
    private static boolean isWithinIndexRange(String trimmedIndex) {
        String strPattern = "^0+(?!$)";
        trimmedIndex = trimmedIndex.replaceAll(strPattern, "");
        return !(trimmedIndex.length() > 9);
    }

    /**
     * Returns true if string contains only digits from 0 to 9
     * @param s string to check
     * @return true if string includes only digits from 0 to 9
     */
    private static boolean isAllDigits(String s) {
        return s.matches("^\\d+$");
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException, IndexOutOfBoundsException {
        String trimmedIndex = oneBasedIndex.trim();
        requireNonNull(trimmedIndex);
        if (!isAllDigits(trimmedIndex) || trimmedIndex.equals("0")) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        if (!isWithinIndexRange(trimmedIndex)) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_OUT_OF_BOUNDS);
        }

        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String school} into an {@code School}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static School parseSchool(String school) throws ParseException {
        requireNonNull(school);
        String trimmedSchool = school.trim();
        if (!School.isValidSchool(trimmedSchool)) {
            throw new ParseException(School.MESSAGE_CONSTRAINTS);
        }
        return new School(trimmedSchool);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String level} into an {@code Level}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code level} is invalid.
     */
    public static Level parseLevel(String level) throws ParseException {
        requireNonNull(level);
        String trimmedLevel = level.trim();
        if (!Level.isValidLevel(trimmedLevel)) {
            throw new ParseException(Level.MESSAGE_CONSTRAINTS);
        }
        return new Level(trimmedLevel);
    }

    /**
     * Parses multiple {@code String levels} into an array of {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code level} is invalid.
     */
    public static String[] parseMultipleSubjects(String subjects) throws ParseException {
        requireNonNull(subjects);
        String trimmedSubjects = subjects.trim();
        String[] subjectsSplitBySpace = trimmedSubjects.split("\\s+");
        List<String> arrayOfSubjects = new ArrayList<>();

        for (String subjectName: subjectsSplitBySpace) {
            if (!Subject.isValidSubject(subjectName)) {
                throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
            }
            arrayOfSubjects.add(subjectName);
        }

        String[] arrayOfSubjectsInString = new String[arrayOfSubjects.size()];
        arrayOfSubjectsInString = arrayOfSubjects.toArray(arrayOfSubjectsInString);
        return arrayOfSubjectsInString;
    }

    /**
     * Returns an array of String with the overdue boolean at index 0 in type String.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code level} is invalid.
     */
    public static String[] parseIsOverdue(String overdue) throws ParseException {
        requireNonNull(overdue);
        String trimmedIsOverdue = overdue.trim();
        String[] isOverdueSplitBySpace = trimmedIsOverdue.split("\\s+");

        if (isOverdueSplitBySpace.length != 1
                || !(isOverdueSplitBySpace[0].equalsIgnoreCase("yes")
                || isOverdueSplitBySpace[0].equalsIgnoreCase("no"))) {
            throw new ParseException("Overdue flag can only be yes or no.");
        }

        if (isOverdueSplitBySpace[0].equalsIgnoreCase("yes")) {
            isOverdueSplitBySpace[0] = "true";
        } else {
            assert isOverdueSplitBySpace[0].equals("no");
            isOverdueSplitBySpace[0] = "false";
        }

        return isOverdueSplitBySpace;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String dayOfWeek} into a {@code DayOfWeek}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dayOfWeek} is invalid.
     */
    public static DayOfWeek parseDayOfWeek(String dayOfWeek) throws ParseException {
        requireNonNull(dayOfWeek);
        String trimmedDayOfWeek = dayOfWeek.trim();
        try {
            return DayOfWeek.of(Integer.parseInt(trimmedDayOfWeek));
        } catch (DateTimeException | NumberFormatException e) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS_INVALID_DAY);
        }
    }

    /**
     * Parses a {@code String localTime} into a {@code LocalTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code localTime} is invalid.
     */
    public static LocalTime parseLocalTime(String localTime) throws ParseException {
        requireNonNull(localTime);
        String trimmedLocalTime = localTime.trim();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(trimmedLocalTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS_INVALID_LOCALTIME);
        }
    }

    /**
     * Parses a {@code String subject} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubject(trimmedSubject)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }

    /**
     * Parses a {@code String hourlyRate} into a {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code hourlyRate} is invalid.
     */
    public static double parseHourlyRate(String hourlyRate) throws ParseException {
        requireNonNull(hourlyRate);
        String trimmedHourlyRate = hourlyRate.trim();
        if (!Lesson.isValidHourlyRateFormat(trimmedHourlyRate)) {
            throw new ParseException(Lesson.MESSAGE_CONSTRAINTS_INVALID_HOURLY_RATE_FORMAT);
        }

        double parsedHourlyRate = Double.parseDouble(hourlyRate);
        if (Lesson.isExceedMaximumHourlyRate(parsedHourlyRate)) {
            throw new ParseException(Lesson.MESSAGE_CONSTRAINTS_MAXIMUM_HOURLY_RATE_EXCEEDED);
        }
        return parsedHourlyRate;
    }

    /**
     * Trims input by user and checks if it is a positive number represented as a string.
     *
     * @param paymentValue Payment value inputted by user
     * @return Trimmed string of payment value
     * @throws ParseException
     */
    public static String parsePaymentValue(String paymentValue) throws ParseException {
        requireNonNull(paymentValue);
        String trimmedPayment = paymentValue.trim();
        if (!Payment.isNumberWithAnyDecimals(trimmedPayment)) {
            throw new ParseException(Payment.FORMAT_CONSTRAINTS_MESSAGE);
        } else if (!Payment.isValidPaymentFormat(trimmedPayment)) {
            throw new ParseException(Payment.DECIMAL_CONSTRAINTS_MESSAGE);
        } else if (!Payment.isValidPaymentAmount(trimmedPayment)) {
            // Payment amount is greater than maximum allowed
            throw new ParseException(Payment.AMOUNT_CONSTRAINTS_MESSAGE);
        } else {
            Double paymentValueToSetValue = Double.parseDouble(trimmedPayment);
            String paymentValueToSetWithDecimals = String.format("%.2f", paymentValueToSetValue);
            return paymentValueToSetWithDecimals;
        }
    }

    /**
     * Trims input by user and checks if index is a positive number represented as a string.
     *
     * @param lessonIndex Payment value inputted by user
     * @return Trimmed string of payment value
     * @throws ParseException
     */
    public static Index parseLessonIndex(String lessonIndex) throws ParseException {
        requireNonNull(lessonIndex);
        String trimmedLessonIndex = lessonIndex.trim();

        if (trimmedLessonIndex.equals(EMPTY_STRING)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        if (!Payment.isValidPaymentFormat(trimmedLessonIndex)) {
            throw new ParseException(Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
        }

        Index parsedLessonIndex;

        try {
            parsedLessonIndex = ParserUtil.parseIndex(trimmedLessonIndex);
        } catch (IndexOutOfBoundsException ie) {
            throw new ParseException(MESSAGE_INVALID_LESSON_INDEX, ie);
        }
        return parsedLessonIndex;
    }

    /**
     * Trims string by user and parses into a LocalDate format of dd-mm-yyyy.
     *
     * @param payByDate Date to make payment by inputted by user
     * @return LocalDate of formatted string
     * @throws ParseException
     */
    public static LocalDate parsePayByDate(String payByDate) throws ParseException {
        String trimmedPayByDate = payByDate.trim();
        LocalDate formattedPayByDate;
        LocalDate dateToday = LocalDate.now();
        if (trimmedPayByDate.equals("")) {
            return null;
        }

        if (!Payment.isValidPayByDate(trimmedPayByDate)) {
            throw new ParseException(Payment.DATE_CONSTRAINTS_MESSAGE);
        }

        try {
            formattedPayByDate = LocalDate.parse(trimmedPayByDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            throw new ParseException(Payment.DATE_CONSTRAINTS_MESSAGE);
        }

        if (!formattedPayByDate.isAfter(dateToday) && !formattedPayByDate.equals(dateToday)) {
            throw new ParseException(Payment.DATE_CONSTRAINTS_MESSAGE);
        }

        if (formattedPayByDate == null) {
            throw new ParseException(Payment.DATE_CONSTRAINTS_MESSAGE);
        }

        return formattedPayByDate;
    }
}
