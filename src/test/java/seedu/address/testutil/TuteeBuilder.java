package seedu.address.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Tutee objects.
 */
public class TuteeBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_SCHOOL = "SCGS";
    public static final String DEFAULT_LEVEL = "p1";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_FEE = "0";
    public static final String DEFAULT_REMARK = "-";

    private Name name;
    private Phone phone;
    private School school;
    private Level level;
    private Address address;
    private Payment payment;
    private Remark remark;
    private Set<Tag> tags;
    private List<Lesson> lessons;

    /**
     * Creates a {@code TuteeBuilder} with the default details.
     */
    public TuteeBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        school = new School(DEFAULT_SCHOOL);
        level = new Level(DEFAULT_LEVEL);
        address = new Address(DEFAULT_ADDRESS);
        payment = new Payment(DEFAULT_FEE, null);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
        lessons = new ArrayList<>();
    }

    /**
     * Initializes the TuteeBuilder with the data of {@code tuteeToCopy}.
     */
    public TuteeBuilder(Tutee tuteeToCopy) {
        name = tuteeToCopy.getName();
        phone = tuteeToCopy.getPhone();
        school = tuteeToCopy.getSchool();
        level = tuteeToCopy.getLevel();
        address = tuteeToCopy.getAddress();
        payment = tuteeToCopy.getPayment();
        remark = tuteeToCopy.getRemark();
        tags = new HashSet<>(tuteeToCopy.getTags());
        lessons = new ArrayList<>(tuteeToCopy.getLessons());
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
     * Sets the {@code Fee} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withPayment(String payment, LocalDate payByDate) {
        this.payment = new Payment(payment, payByDate);
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
     * Sets the {@code School} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withSchool(String school) {
        this.school = new School(school);
        return this;
    }

    /**
     * Sets the {@code Level} of the {@code Tutee} that we are building.
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
        return new Tutee(name, phone, school, level, address, payment, remark, tags, lessons);
    }

}
