package seedu.address.testutil;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Address;
import seedu.address.model.tutee.Level;
import seedu.address.model.tutee.Name;
import seedu.address.model.tutee.Phone;
import seedu.address.model.tutee.Remark;
import seedu.address.model.tutee.Tutee;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Tutee objects.
 */
public class TuteeBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_LEVEL = "p1";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "";

    private Name name;
    private Phone phone;
    private Level level;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;
    private Set<Lesson> lessons;

    /**
     * Creates a {@code TuteeBuilder} with the default details.
     */
    public TuteeBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        level = new Level(DEFAULT_LEVEL);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
        lessons = new LinkedHashSet<>();
    }

    /**
     * Initializes the TuteeBuilder with the data of {@code tuteeToCopy}.
     */
    public TuteeBuilder(Tutee tuteeToCopy) {
        name = tuteeToCopy.getName();
        phone = tuteeToCopy.getPhone();
        level = tuteeToCopy.getLevel();
        address = tuteeToCopy.getAddress();
        remark = tuteeToCopy.getRemark();
        tags = new HashSet<>(tuteeToCopy.getTags());
        lessons = new HashSet<>(tuteeToCopy.getLessons());
    }

    /**
     * Sets the {@code Name} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Tutee} that we are building.
     */
    public TuteeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Adds the {@code lesson} to the {@code Set<Lesson>} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withLesson(Lesson lesson) {
        lessons.add(lesson);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withLevel(String level) {
        this.level = new Level(level);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Tutee build() {
        return new Tutee(name, phone, level, address, remark, tags, lessons);
    }

}
