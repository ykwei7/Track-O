package seedu.address.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Address;
import seedu.address.model.tutee.Level;
import seedu.address.model.tutee.Name;
import seedu.address.model.tutee.Phone;
import seedu.address.model.tutee.Remark;
import seedu.address.model.tutee.Tutee;

/**
 * Jackson-friendly version of {@link Tutee}.
 */
class JsonAdaptedTutee {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutee's %s field is missing!";

    private final String name;
    private final String phone;
    private final String level;
    private final String address;
    private final String remark;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<String> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTutee} with the given tutee details.
     */
    @JsonCreator
    public JsonAdaptedTutee(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("level") String level, @JsonProperty("address") String address,
                             @JsonProperty("remark") String remark,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                             @JsonProperty("lessons") List<String> lessons) {
        this.name = name;
        this.phone = phone;
        this.level = level;
        this.address = address;
        this.remark = remark;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (lessons != null) {
            this.lessons.addAll(lessons);
        }
    }

    /**
     * Converts a given {@code Tutee} into this class for Jackson use.
     */
    public JsonAdaptedTutee(Tutee source) throws JsonProcessingException {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        level = source.getLevel().value;
        address = source.getAddress().value;
        remark = source.getRemark().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        Set<Lesson> sourceLessons = source.getLessons();
        for (Lesson lesson : sourceLessons) {
            lessons.add(JsonUtil.toJsonString(lesson));
        }
    }

    /**
     * Converts this Jackson-friendly adapted tutee object into the model's {@code Tutee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tutee.
     */
    public Tutee toModelType() throws IllegalValueException, IOException {
        final List<Tag> tuteeTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            tuteeTags.add(tag.toModelType());
        }

        final List<Lesson> tuteeLessons = new ArrayList<>();
        for (String lesson : lessons) {
            tuteeLessons.add(JsonUtil.fromJsonString(lesson, Lesson.class));
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (level == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName()));
        }
        if (!Level.isValidLevel(level)) {
            throw new IllegalValueException(Level.MESSAGE_CONSTRAINTS);
        }
        final Level modelLevel = new Level(level);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(tuteeTags);
        final Set<Lesson> modelLessons = new LinkedHashSet<>(tuteeLessons);

        return new Tutee(modelName, modelPhone, modelLevel, modelAddress, modelRemark, modelTags, modelLessons);
    }

}
