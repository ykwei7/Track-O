package seedu.address.model.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public static final LocalDate STANDARD_PAYMENT_DATE = LocalDate.now().plusYears(1);

    public static final LocalDate OVERDUE_PAYMENT_DATE = LocalDate.now().minusDays(1);

    public static final List<Lesson> LESSONS_ALEX = List.of(
            new Lesson(new Subject("Chinese"),
                    new Time(DayOfWeek.SATURDAY, LocalTime.parse("08:00"), LocalTime.parse("09:00")),
                    50),
            new Lesson(new Subject("Math"),
                    new Time(DayOfWeek.WEDNESDAY, LocalTime.parse("19:30"), LocalTime.parse("21:00")),
                    40.50));

    public static final List<Lesson> LESSONS_BERNICE = List.of(
            new Lesson(new Subject("English"),
                    new Time(DayOfWeek.TUESDAY, LocalTime.parse("19:30"), LocalTime.parse("21:00")),
                    35));

    public static final List<Lesson> LESSONS_CHARLOTTE = List.of(
            new Lesson(new Subject("Geography"),
                    new Time(DayOfWeek.MONDAY, LocalTime.parse("19:30"), LocalTime.parse("21:00")),
                    50));

    public static final List<Lesson> LESSONS_DAVID = List.of(
            new Lesson(new Subject("Literature"),
                    new Time(DayOfWeek.MONDAY, LocalTime.parse("08:30"), LocalTime.parse("09:30")),
                    50),
            new Lesson(new Subject("Physics"),
                    new Time(DayOfWeek.THURSDAY, LocalTime.parse("11:30"), LocalTime.parse("13:30")),
                    55.50));

    public static Tutee[] getSampleTutees() {

        return new Tutee[] {
            new Tutee(new Name("Alex Yeoh"), new Phone("87438807"), new School("Nan Hua Primary School"),
                new Level("p6"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new Payment("50", STANDARD_PAYMENT_DATE), new Remark("Good progress!"),
                getTagSet("PSLE"), LESSONS_ALEX),
            new Tutee(new Name("Bernice Yu"), new Phone("98272758"), new School("Nanyang Primary School"),
                new Level("p3"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Payment("55", STANDARD_PAYMENT_DATE), new Remark("Upcoming oral and compo exams"),
                getTagSet("Oral", "Composition"), LESSONS_BERNICE),
            new Tutee(new Name("Charlotte Oliveiro"), new Phone("93210283"), new School("Canberra Secondary School"),
                new Level("s2"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Payment("60", OVERDUE_PAYMENT_DATE), new Remark("Need to work on Human Geography"),
                getTagSet("Holiday"), LESSONS_CHARLOTTE),
            new Tutee(new Name("David Li"), new Phone("91031282"), new School("National Junior College"),
                new Level("j2"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Payment("70", STANDARD_PAYMENT_DATE), EMPTY_REMARK, getTagSet("Hamlet"), LESSONS_DAVID)
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
