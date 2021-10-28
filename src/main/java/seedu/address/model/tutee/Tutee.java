package seedu.address.model.tutee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;

/**
 * Represents a Tutee in Track-O.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutee {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final School school;
    private final Level level;

    // Data fields
    private final Address address;
    private final Payment payment;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private final List<Lesson> lessons = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */

    public Tutee(Name name, Phone phone, School school, Level level, Address address, Payment payment, Remark remark,
                 Set<Tag> tags, List<Lesson> lessons) {
        requireAllNonNull(name, phone, level, address, tags, lessons);
        this.name = name;
        this.phone = phone;
        this.school = school;
        this.level = level;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
        this.payment = payment == null ? Payment.initializePayment() : payment;
        this.lessons.addAll(lessons);
    }

    /**
     * Adds a lesson to the tutee.
     */
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        this.lessons.add(lesson);
    }

    /**
     * Deletes a lesson from the tutee.
     */
    public void deleteLesson(Index lessonIndex) {
        requireNonNull(lessonIndex);
        this.lessons.remove(lessonIndex.getZeroBased());
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public School getSchool() {
        return school;
    }

    public Level getLevel() {
        return level;
    }

    public Address getAddress() {
        return address;
    }

    public Payment getPayment() {
        return payment;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable lesson list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
    }

    /**
     * Returns true if both tutees have the same name.
     * This defines a weaker notion of equality between two tutees.
     */
    public boolean isSameTutee(Tutee otherTutee) {
        if (otherTutee == this) {
            return true;
        }

        return otherTutee != null
                && otherTutee.getName().equals(getName());
    }

    /**
     * Returns true if both tutees have the same identity and data fields.
     * This defines a stronger notion of equality between two tutees.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutee)) {
            return false;
        }

        Tutee otherTutee = (Tutee) other;
        return otherTutee.getName().equals(getName())
                && otherTutee.getPhone().equals(getPhone())
                && otherTutee.getSchool().equals(getSchool())
                && otherTutee.getLevel().equals(getLevel())
                && otherTutee.getAddress().equals(getAddress())
                && otherTutee.getPayment().equals(getPayment())
                && otherTutee.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, school, level, address, payment,
                            remark, tags, lessons);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nPhone: ")
                .append(getPhone())
                .append("\nSchool: ")
                .append(getSchool())
                .append("\nLevel: ")
                .append(getLevel())
                .append("\nAddress: ")
                .append(getAddress())
                .append("\nFees incurred: ")
                .append(getPayment());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("\nTags: ");
            tags.forEach(builder::append);
        }

        List<Lesson> lessons = getLessons();
        if (!lessons.isEmpty()) {
            builder.append("\n\nLessons: \n");
            for (int i = 0; i < lessons.size(); i++) {
                builder.append(i + 1)
                        .append(". ")
                        .append(lessons.get(i));
            }
        }

        builder.append("\nRemark: ")
                .append(getRemark())
                .append("\n");

        builder.append("\n");

        return builder.toString();
    }

}
