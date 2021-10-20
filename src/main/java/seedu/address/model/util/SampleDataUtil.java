package seedu.address.model.util;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.ReadOnlyTrackO;
import seedu.address.model.TrackO;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Time;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Address;
import seedu.address.model.tutee.Level;
import seedu.address.model.tutee.Name;
import seedu.address.model.tutee.Phone;
import seedu.address.model.tutee.Remark;
import seedu.address.model.tutee.Tutee;

/**
 * Contains utility methods for populating {@code TrackO} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");

    public static final Set<Lesson> EMPTY_LESSONS = new LinkedHashSet<>();

    public static final Set<Lesson> NON_EMPTY_LESSONS = Stream.of(
            new Lesson(new Subject("Math"),
                    new Time(DayOfWeek.WEDNESDAY, LocalTime.parse("19:30"), LocalTime.parse("21:00")),
            40),
            new Lesson(new Subject("Chinese"),
                new Time(DayOfWeek.SATURDAY, LocalTime.parse("08:00"), LocalTime.parse("09:00")),
            40)).collect(Collectors.toCollection(LinkedHashSet::new));

    public static Tutee[] getSampleTutees() {

        return new Tutee[] {
            new Tutee(new Name("Alex Yeoh"), new Phone("87438807"), new Level("p1"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Remark("Good progress!"),
                getTagSet("friends"), EMPTY_LESSONS),
            new Tutee(new Name("Bernice Yu"), new Phone("99272758"), new Level("p2"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Remark("Needs to work on math"),
                getTagSet("colleagues", "friends"), NON_EMPTY_LESSONS),
            new Tutee(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Level("p3"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark("Needs to work on English"),
                getTagSet("neighbours"), EMPTY_LESSONS),
            new Tutee(new Name("David Li"), new Phone("91031282"), new Level("p4"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), EMPTY_REMARK,
                getTagSet("family"), EMPTY_LESSONS),
            new Tutee(new Name("Irfan Ibrahim"), new Phone("92492021"), new Level("p5"),
                new Address("Blk 47 Tampines Street 20, #17-35"), EMPTY_REMARK,
                getTagSet("classmates"), EMPTY_LESSONS),
            new Tutee(new Name("Roy Balakrishnan"), new Phone("92624417"), new Level("p6"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), EMPTY_REMARK,
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
