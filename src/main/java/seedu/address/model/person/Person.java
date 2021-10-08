package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Level level;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Lesson> lessons = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Level level, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, level, address, tags);
        this.name = name;
        this.phone = phone;
        this.level = level;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Level level, Address address, Set<Tag> tags, Set<Lesson> lessons) {
        requireAllNonNull(name, phone, level, address, tags);
        this.name = name;
        this.phone = phone;
        this.level = level;
        this.address = address;
        this.tags.addAll(tags);
        this.lessons.addAll(lessons);
    }

    /**
     * Adds a lesson to the person.
     */
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        this.lessons.add(lesson);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Level getLevel() {
        return level;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable lesson set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Lesson> getLessons() {
        return Collections.unmodifiableSet(lessons);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getLevel().equals(getLevel())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, level, address, tags, lessons);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Level: ")
                .append(getLevel())
                .append("; Address: ")
                .append(getAddress());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }

        Set<Lesson> lessons = getLessons();
        if (!lessons.isEmpty()) {
            builder.append("; Lessons: ");
            lessons.forEach(builder::append);
        }
        return builder.toString();
    }

}
