package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
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


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
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
     * Parses a {@code String payment} into a {@code Payment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code payment} is invalid.
     */
    public static Payment parsePayment(String payment) throws ParseException {
        requireNonNull(payment);
        String trimmedPayment = payment.trim();
        if (!Payment.isValidPayment(trimmedPayment)) {
            throw new ParseException(Payment.MESSAGE_CONSTRAINTS);
        }
        return new Payment(trimmedPayment, null);
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
     * @throws ParseException if the given {@code level} contains multiple levels or is invalid.
     */
    public static String[] parseMultipleLevels(String levels) throws ParseException {
        requireNonNull(levels);
        String trimmedLevel = levels.trim();
        String[] levelsSplitBySpace = trimmedLevel.split("\\s+");

        if (levelsSplitBySpace.length != 1) {
            throw new ParseException(FindCommand.MESSAGE_LEVEL_CONSTRAINT);
        }
        List<String> arrayOfLevels = new ArrayList<>();

        arrayOfLevels.add(parseLevel(trimmedLevel).getValue());

        String[] arrayOfLevelsInString = new String[arrayOfLevels.size()];
        arrayOfLevelsInString = arrayOfLevels.toArray(arrayOfLevelsInString);
        return arrayOfLevelsInString;
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

    public static String[] parseIsOverdue(String overdue) throws ParseException {
        requireNonNull(overdue);
        String trimmedIsOverdue = overdue.trim();
        String[] isOverdueSplitBySpace = trimmedIsOverdue.split("\\s+");

        if (isOverdueSplitBySpace.length != 1
                || !(isOverdueSplitBySpace[0].equals("true")
                || isOverdueSplitBySpace[0].equals("false"))) {
            throw new ParseException("Overdue flag can only be true or false.");
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
        if (!Lesson.isValidHourlyRate(trimmedHourlyRate)) {
            throw new ParseException(Lesson.MESSAGE_CONSTRAINTS);
        }
        return Double.parseDouble(hourlyRate);
    }
}
