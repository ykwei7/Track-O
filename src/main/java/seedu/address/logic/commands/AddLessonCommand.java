package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Time;
import seedu.address.model.person.Person;

/**
 * Adds a lesson to an existing tutee in the tutee list.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the tutee identified "
            + "by the index number used in the displayed tutee list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_DAY_OF_WEEK + "DAY_OF_WEEK "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SUBJECT + "Principles of Accounting "
            + PREFIX_DAY_OF_WEEK + "Thursday "
            + PREFIX_START_TIME + "11:30 "
            + PREFIX_END_TIME + "13:30 ";

    public static final String MESSAGE_SUCCESS = "New lesson added to Tutee: %1$s";

    private final Index targetIndex;
    private final Subject subject;
    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    /**
     * Creates an AddLessonCommand to add a {@code Lesson} to the specified {@code Person}
     *
     * @param targetIndex of the person in the filtered tutee list
     * @param subject of the Lesson
     * @param dayOfWeek of the Lesson
     * @param startTime of the Lesson
     * @param endTime of the Lesson
     */
    public AddLessonCommand(Index targetIndex, Subject subject, DayOfWeek dayOfWeek,
                            LocalTime startTime, LocalTime endTime) {
        this.targetIndex = targetIndex;
        this.subject = subject;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Time lessonTime;
        try {
            lessonTime = new Time(dayOfWeek, startTime, endTime);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
        Lesson lesson = new Lesson(subject, lessonTime);

        Person editedPerson = lastShownList.get(targetIndex.getZeroBased());
        editedPerson.addLesson(lesson);
        model.setPerson(lastShownList.get(targetIndex.getZeroBased()), editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLessonCommand)) {
            return false;
        }

        // state check
        AddLessonCommand addLessonCommand = (AddLessonCommand) other;
        return targetIndex.equals(addLessonCommand.targetIndex)
                && subject.equals(addLessonCommand.subject)
                && dayOfWeek.equals(addLessonCommand.dayOfWeek)
                && startTime.equals(addLessonCommand.startTime)
                && endTime.equals(addLessonCommand.endTime);
    }
}
