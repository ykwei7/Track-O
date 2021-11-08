package seedu.address.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.LESSONS_ALEX;
import static seedu.address.model.util.SampleDataUtil.LESSONS_BERNICE;
import static seedu.address.model.util.SampleDataUtil.LESSONS_CHARLOTTE;
import static seedu.address.model.util.SampleDataUtil.LESSONS_DAVID;
import static seedu.address.model.util.SampleDataUtil.OVERDUE_PAYMENT_DATE;
import static seedu.address.model.util.SampleDataUtil.STANDARD_PAYMENT_DATE;

import org.junit.jupiter.api.Test;

import seedu.address.model.TrackO;
import seedu.address.model.tutee.Tutee;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TuteeBuilder;

/**
 * This class ensures that the sample data in Track-O have not been changed.
 */
class SampleDataUtilTest {

    private static final Tutee[] VALID_SAMPLE_TUTEES = new Tutee[] {
            new TuteeBuilder().withName("Alex Yeoh").withPhone("87438807").withSchool("Nan Hua Primary School")
                    .withLevel("p6").withAddress("Blk 30 Geylang Street 29, #06-40")
                    .withPayment("50", STANDARD_PAYMENT_DATE).withRemark("Good progress!").withTags("PSLE")
                    .withLesson(LESSONS_ALEX.get(0)).withLesson(LESSONS_ALEX.get(1)).build(),
            new TuteeBuilder().withName("Bernice Yu").withPhone("98272758").withSchool("Nanyang Primary School")
                    .withLevel("p3").withAddress("Blk 30 Lorong 3 Serangoon Gardens, #07-18")
                    .withPayment("55", STANDARD_PAYMENT_DATE).withRemark("Upcoming oral and compo exams")
                    .withTags("Oral", "Composition").withLesson(LESSONS_BERNICE.get(0)).build(),
            new TuteeBuilder().withName("Charlotte Oliveiro").withPhone("93210283")
                    .withSchool("Canberra Secondary School").withLevel("s2")
                    .withAddress("Blk 11 Ang Mo Kio Street 74, #11-04").withPayment("60", OVERDUE_PAYMENT_DATE)
                    .withRemark("Need to work on Human Geography").withTags("Holiday")
                    .withLesson(LESSONS_CHARLOTTE.get(0)).build(),
            new TuteeBuilder().withName("David Li").withPhone("91031282")
                    .withSchool("National Junior College").withLevel("j2")
                    .withAddress("Blk 436 Serangoon Gardens Street 26, #16-43").withPayment("70",
                            STANDARD_PAYMENT_DATE).withTags("Hamlet")
                    .withLesson(LESSONS_DAVID.get(0)).withLesson(LESSONS_DAVID.get(1)).build()};


    @Test
    void getSampleTuteesTest() {
        Tutee[] sampleTutees = SampleDataUtil.getSampleTutees();
        for (int i = 0; i < VALID_SAMPLE_TUTEES.length; i++) {
            assertEquals(sampleTutees[i], VALID_SAMPLE_TUTEES[i]);
        }
    }

    @Test
    void getSampleTrackOTest() {
        // Type-casting is safe as getSampleTrackO returns a TrackO object
        TrackO testTrackO = (TrackO) SampleDataUtil.getSampleTrackO();
        for (Tutee t : VALID_SAMPLE_TUTEES) {
            assertTrue(testTrackO.hasTutee(t));
        }
    }

}
