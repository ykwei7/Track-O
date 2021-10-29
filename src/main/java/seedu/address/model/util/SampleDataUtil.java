package seedu.address.model.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyTrackO;
import seedu.address.model.TrackO;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Time;
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
 * Contains utility methods for populating {@code TrackO} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("-");

    public static final LocalDate STANDARD_PAYMENT_DATE = LocalDate.now();

    public static final List<Lesson> EMPTY_LESSONS = new ArrayList<>();

    public static final List<Lesson> NON_EMPTY_LESSONS = List.of(
            new Lesson(new Subject("Math"),
                    new Time(DayOfWeek.WEDNESDAY, LocalTime.parse("19:30"), LocalTime.parse("21:00")),
                    40),
            new Lesson(new Subject("Chinese"),
                new Time(DayOfWeek.SATURDAY, LocalTime.parse("08:00"), LocalTime.parse("09:00")),
            40));

    public static Tutee[] getSampleTutees() {

        return new Tutee[] {
            new Tutee(new Name("Alex Yeoh"), new Phone("87438807"), new School("Nan Hua Primary School"),
                new Level("p1"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new Payment("50", STANDARD_PAYMENT_DATE), new Remark("Good progress!"),
                getTagSet("friends"), EMPTY_LESSONS),
            new Tutee(new Name("Bernice Yu"), new Phone("99272758"), new School("Nanyang Primary School"),
                new Level("p2"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Payment("55", STANDARD_PAYMENT_DATE), new Remark("Needs to work on math"),
                getTagSet("colleagues", "friends"), NON_EMPTY_LESSONS),
            new Tutee(new Name("Charlotte Oliveiro"), new Phone("93210283"), new School("Canberra Primary School"),
                new Level("p3"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Payment("60", STANDARD_PAYMENT_DATE), new Remark("Needs to work on English"),
                getTagSet("neighbours"), EMPTY_LESSONS),
            new Tutee(new Name("David Li"), new Phone("91031282"), new School("Punggol Primary School"),
                new Level("p4"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Payment("70", STANDARD_PAYMENT_DATE), EMPTY_REMARK, getTagSet("family"), EMPTY_LESSONS),
            new Tutee(new Name("Irfan Ibrahim"), new Phone("92492021"), new School("Rosyth School"), new Level("p5"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Payment("75", STANDARD_PAYMENT_DATE),
                EMPTY_REMARK, getTagSet("classmates"), EMPTY_LESSONS),
            new Tutee(new Name("Roy Balakrishnan"), new Phone("92624417"), new School("River Valley Primary School"),
                new Level("p6"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                new Payment("80", STANDARD_PAYMENT_DATE), EMPTY_REMARK,
                getTagSet("colleagues"), EMPTY_LESSONS)
        };
    }

    public static ReadOnlyTrackO getSampleTrackO() {
        TrackO sampleAb = new TrackO();
        for (Tutee sampleTutee : getSampleTutees()) {
            sampleAb.addTutee(sampleTutee);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
