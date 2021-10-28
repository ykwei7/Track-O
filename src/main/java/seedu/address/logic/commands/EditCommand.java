package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Schedule;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Address;
import seedu.address.model.tutee.Level;
import seedu.address.model.tutee.Name;
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Phone;
import seedu.address.model.tutee.Remark;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Tutee;

/**
 * Edits the details of an existing tutee in Track-O.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the tutee identified "
            + "by the index number used in the displayed tutee list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_SCHOOL + "SCHOOL] "
            + "[" + PREFIX_LEVEL + "LEVEL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_LEVEL + "p5";

    public static final String MESSAGE_EDIT_TUTEE_SUCCESS = "Edited Tutee: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TUTEE = "This tutee already exists in Track-O.";

    private final Index index;
    private final EditTuteeDescriptor editTuteeDescriptor;

    /**
     * @param index of the tutee in the filtered tutee list to edit
     * @param editTuteeDescriptor details to edit the tutee with
     */
    public EditCommand(Index index, EditTuteeDescriptor editTuteeDescriptor) {
        requireNonNull(index);
        requireNonNull(editTuteeDescriptor);

        this.index = index;
        this.editTuteeDescriptor = new EditTuteeDescriptor(editTuteeDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutee> lastShownList = model.getFilteredTuteeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
        }

        Tutee tuteeToEdit = lastShownList.get(index.getZeroBased());
        Tutee editedTutee = createEditedTutee(tuteeToEdit, editTuteeDescriptor);

        if (!tuteeToEdit.isSameTutee(editedTutee) && model.hasTutee(editedTutee)) {
            throw new CommandException(MESSAGE_DUPLICATE_TUTEE);
        }

        if (editTuteeDescriptor.getName().isPresent()) {
            // update schedule to reflect the edited tutee's name
            Schedule schedule = model.getSchedule();
            schedule.updateWithNewTuteeName(editedTutee.getLessons(), editedTutee.getName().toString());
        }

        model.setTutee(tuteeToEdit, editedTutee);
        model.updateFilteredTuteeList(PREDICATE_SHOW_ALL_TUTEES);
        return new CommandResult(String.format(MESSAGE_EDIT_TUTEE_SUCCESS, editedTutee));
    }

    /**
     * Creates and returns a {@code Tutee} with the details of {@code tuteeToEdit}
     * edited with {@code editTuteeDescriptor}.
     */
    private static Tutee createEditedTutee(Tutee tuteeToEdit, EditTuteeDescriptor editTuteeDescriptor) {
        assert tuteeToEdit != null;

        Name updatedName = editTuteeDescriptor.getName().orElse(tuteeToEdit.getName());
        Phone updatedPhone = editTuteeDescriptor.getPhone().orElse(tuteeToEdit.getPhone());
        School updatedSchool = editTuteeDescriptor.getSchool().orElse(tuteeToEdit.getSchool());
        Level updatedLevel = editTuteeDescriptor.getLevel().orElse(tuteeToEdit.getLevel());
        Address updatedAddress = editTuteeDescriptor.getAddress().orElse(tuteeToEdit.getAddress());
        Payment updatedPayment = tuteeToEdit.getPayment(); // edit command does not allow editing fees
        Remark updatedRemark = tuteeToEdit.getRemark(); // edit command does not allow editing remarks
        Set<Tag> updatedTags = editTuteeDescriptor.getTags().orElse(tuteeToEdit.getTags());
        List<Lesson> updatedLessons = tuteeToEdit.getLessons(); // edit command does not allow editing lessons

        return new Tutee(updatedName, updatedPhone, updatedSchool, updatedLevel, updatedAddress,
                updatedPayment, updatedRemark, updatedTags, updatedLessons);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editTuteeDescriptor.equals(e.editTuteeDescriptor);
    }

    /**
     * Stores the details to edit the tutee with. Each non-empty field value will replace the
     * corresponding field value of the tutee.
     */
    public static class EditTuteeDescriptor {
        private Name name;
        private Phone phone;
        private School school;
        private Level level;
        private Address address;
        private Set<Tag> tags;

        public EditTuteeDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTuteeDescriptor(EditTuteeDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setSchool(toCopy.school);
            setLevel(toCopy.level);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, school, level, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setSchool(School school) {
            this.school = school;
        }

        public Optional<School> getSchool() {
            return Optional.ofNullable(school);
        }

        public void setLevel(Level level) {
            this.level = level;
        }

        public Optional<Level> getLevel() {
            return Optional.ofNullable(level);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTuteeDescriptor)) {
                return false;
            }

            // state check
            EditTuteeDescriptor e = (EditTuteeDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getSchool().equals(e.getSchool())
                    && getLevel().equals(e.getLevel())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
