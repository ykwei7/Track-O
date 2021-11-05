package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PRACTICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_RESCHEDULED;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TrackO;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Time;
import seedu.address.model.tutee.Tutee;


/**
 * A utility class containing a list of {@code Tutee} objects to be used in tests.
 */
public class TypicalTutees {

    public static final Lesson LESSON = new Lesson(
            new Subject("Physics"),
            new Time(DayOfWeek.SUNDAY, LocalTime.parse("12:30"), LocalTime.parse("14:30")), 40.0);

    public static final Lesson LESSON_1 = new Lesson(
            new Subject("Chemistry"),
            new Time(DayOfWeek.TUESDAY, LocalTime.parse("12:30"), LocalTime.parse("14:30")), 40.0);

    public static final Lesson LESSON_2 = new Lesson(
            new Subject("Chemistry"),
            new Time(DayOfWeek.MONDAY, LocalTime.parse("12:30"), LocalTime.parse("14:30")), 40.0);

    public static final Tutee ALICE = new TuteeBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withLevel("p1")
            .withSchool("Tao Nan School").withPhone("94351253")
            .withTags("rescheduled").build();
    public static final Tutee BENSON = new TuteeBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withSchool("Riverside Primary School")
            .withLevel("p2").withPhone("98765432")
            .withRemark("Good progress")
            .withTags("practical").withPayment("90",
                    LocalDate.of(2023, 10, 20)).withLesson(LESSON_2).build();

    public static final Tutee CARL = new TuteeBuilder().withName("Carl Kurz").withPhone("95352563")
            .withSchool("acs primary").withLevel("p3").withAddress("wall street").withPayment("80",
                    LocalDate.of(2023, 10, 20)).withLesson(LESSON).withLesson(LESSON_1).build();
    public static final Tutee DANIEL = new TuteeBuilder().withName("Daniel Meier").withPhone("87652533")
            .withSchool("henry park primary school").withLevel("p4").withAddress("10th street").withTags("language")
            .withPayment("85", LocalDate.of(2023, 10, 20)).build();
    public static final Tutee ELLE = new TuteeBuilder().withName("Elle Meyer").withPhone("94822244")
            .withSchool("scgs").withLevel("p5").withAddress("michegan ave").withPayment("75",
                    LocalDate.of(2023, 10, 20)).build();
    public static final Tutee FIONA = new TuteeBuilder().withName("Fiona Kunz").withPhone("94824277")
            .withSchool("South View Primary School").withLevel("p6").withAddress("little tokyo").withPayment("70",
                    LocalDate.of(2023, 10, 20)).build();
    public static final Tutee GEORGE = new TuteeBuilder().withName("George Best").withPhone("94824422")
            .withSchool("Temasek Primary School").withLevel("p5").withAddress("4th street").withPayment("65",
                    LocalDate.of(2023, 10, 20)).build();


    // Manually added
    public static final Tutee HOON = new TuteeBuilder().withName("Hoon Meier").withPhone("84824244")
            .withSchool("Tao Nan School").withLevel("p5").withAddress("little india").withPayment("100",
                    LocalDate.of(2023, 10, 20)).build();
    public static final Tutee IDA = new TuteeBuilder().withName("Ida Mueller").withPhone("84821311")
            .withSchool("RGPS").withLevel("p4").withAddress("chicago ave").withPayment("110",
                    LocalDate.of(2023, 10, 20)).build();

    // Manually added - Tutee's details found in {@code CommandTestUtil}
    public static final Tutee AMY = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withSchool(VALID_SCHOOL_AMY).withLevel(VALID_LEVEL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_RESCHEDULED).build();
    public static final Tutee BOB = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withSchool(VALID_SCHOOL_BOB).withLevel(VALID_LEVEL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_PRACTICAL, VALID_TAG_RESCHEDULED).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTutees() {} // prevents instantiation

    /**
     * Returns a {@code TrackO} with all the typical tutees.
     */
    public static TrackO getTypicalTrackO() {
        TrackO ab = new TrackO();
        for (Tutee tutee : getTypicalTutees()) {
            ab.addTutee(tutee);
        }
        return ab;
    }

    public static List<Tutee> getTypicalTutees() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
