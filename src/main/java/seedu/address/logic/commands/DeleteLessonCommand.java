package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Schedule;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tutee.Tutee;

/**
 * Deletes a lesson from an existing tutee in the tutee list.
 */
public class DeleteLessonCommand extends Command {

    public static final String COMMAND_WORD = "deletelesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a lesson from a tutee identified "
            + "by the index number used in the displayed tutee list.\n"
            + "Parameters: INDEX (must be a positive number) "
            + PREFIX_LESSON + "INDEX "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LESSON + "2";

    public static final String MESSAGE_SUCCESS = "Lesson deleted from Tutee: %1$s";

    private final Index tuteeIndex;
    private final Index lessonIndex;

    /**
     * Creates an DeleteLessonCommand to delete a {@code Lesson} from the specified {@code Tutee}
     *
     * @param tuteeIndex of the person in the filtered tutee list
     * @param lessonIndex of the Lesson in the lesson list
     */
    public DeleteLessonCommand(Index tuteeIndex, Index lessonIndex) {
        this.tuteeIndex = tuteeIndex;
        this.lessonIndex = lessonIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutee> lastShownList = model.getFilteredTuteeList();

        if (tuteeIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
        }

        Tutee tuteeToEdit = lastShownList.get(tuteeIndex.getZeroBased());
        List<Lesson> lessonList = tuteeToEdit.getLessons();

        if (lessonIndex.getZeroBased() >= lessonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_INDEX);
        }

        Lesson lessonToDelete = lessonList.get(lessonIndex.getZeroBased());
        Schedule schedule = model.getSchedule();

        schedule.remove(lessonToDelete, tuteeToEdit.getName().toString());

        Tutee editedTutee = new Tutee(tuteeToEdit.getName(), tuteeToEdit.getPhone(), tuteeToEdit.getSchool(),
                tuteeToEdit.getLevel(), tuteeToEdit.getAddress(), tuteeToEdit.getPayment(), tuteeToEdit.getRemark(),
                tuteeToEdit.getTags(), tuteeToEdit.getLessons());
        editedTutee.deleteLesson(lessonIndex);

        model.setTutee(tuteeToEdit, editedTutee);
        model.updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedTutee));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteLessonCommand)) {
            return false;
        }

        // state check
        DeleteLessonCommand deleteLessonCommand = (DeleteLessonCommand) other;
        return tuteeIndex.equals(deleteLessonCommand.tuteeIndex)
                && lessonIndex.equals(deleteLessonCommand.lessonIndex);
    }
}
