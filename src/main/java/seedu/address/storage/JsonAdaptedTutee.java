package seedu.address.storage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
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
import seedu.address.model.tutee.Payment;
import seedu.address.model.tutee.Phone;
import seedu.address.model.tutee.Remark;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Tutee;


/**
 * Jackson-friendly version of {@link Tutee}.
 */
class JsonAdaptedTutee {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutee's %s field is missing!";

    private final String name;
    private final String phone;
    private final String school;
    private final String level;
    private final String address;
    private final String remark;
    private final String payment;
    private final String payByDateAsString;
    private final List<String> paymentHistory;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<String> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTutee} with the given tutee details.
     */
    @JsonCreator
    public JsonAdaptedTutee(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                            @JsonProperty("school") String school,
                            @JsonProperty("level") String level, @JsonProperty("address") String address,
                            @JsonProperty("remark") String remark,
                            @JsonProperty("payment") String payment,
                            @JsonProperty("payByDateAsString") String payByDateAsString,
                            @JsonProperty("paymentHistory") List<String> paymentHistory,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                            @JsonProperty("lessons") List<String> lessons) {
        this.name = name;
        this.phone = phone;
        this.school = school;
        this.level = level;
        this.address = address;
        this.remark = remark;
        this.payment = payment;
        this.payByDateAsString = payByDateAsString;
        this.paymentHistory = paymentHistory;
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
        school = source.getSchool().value;
        level = source.getLevel().value;
        address = source.getAddress().value;
        remark = source.getRemark().value;
        payment = source.getPayment().value;
        payByDateAsString = source.getPayment().payByDateAsString;
        paymentHistory = source.getPayment().paymentHistory;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        List<Lesson> sourceLessons = source.getLessons();
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

        if (school == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName()));
        }
        if (!School.isValidSchool(school)) {
            throw new IllegalValueException(School.MESSAGE_CONSTRAINTS);
        }
        final School modelSchool = new School(school);

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

        if (payment == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Payment.class.getSimpleName()));
        }
        if (!Payment.isValidPaymentFormat(payment)) {
            throw new IllegalValueException(Payment.FORMAT_CONSTRAINTS_MESSAGE);
        }

        if (payByDateAsString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "payment pay-by date"));
        }
        if (!Payment.isValidPayByDate(payByDateAsString)) {
            throw new IllegalValueException(Payment.DATE_CONSTRAINTS_MESSAGE);
        }

        if (paymentHistory == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "payment history"));
        }
        if (!Payment.isValidPaymentHistory(paymentHistory)) {
            throw new IllegalValueException(Payment.PAYMENT_HISTORY_CONSTRAINTS_MESSAGE);
        }

        final Address modelAddress = new Address(address);

        final Payment modelPayment = new Payment(payment, payByDateAsString.equals("-") ? null
                : LocalDate.parse(payByDateAsString, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        modelPayment.copyPaymentHistory(paymentHistory);

        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(tuteeTags);

        final List<Lesson> modelLessons = new ArrayList<>(tuteeLessons);

        return new Tutee(modelName, modelPhone, modelSchool, modelLevel, modelAddress, modelPayment,
                         modelRemark, modelTags, modelLessons);
    }

}
