package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutees.BENSON;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import seedu.address.model.lesson.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.TuteeBuilder;


public class TuteeCardTest {

    private static final int INVALID_INDEX = -1;
    private static final int VALID_INDEX = 1;

    private static final Tutee VALID_TUTEE = BENSON;
    private static final Tutee VALID_TUTEE_NO_TAGS = new TuteeBuilder().withName("Test Tutee")
            .withPhone("94824423").withSchool("acsj").withLevel("p1").withAddress("3rd street").withPayment("65",
                    LocalDate.of(2023, 10, 20)).build();

    private static boolean isJavaFxCompatible = true;

    @Test
    public void constructor_invalidTutee_throwsAssertionError() {
        if (isJavaFxCompatible && initToolkitSuccess()) {
            assertThrows(AssertionError.class, () -> new TuteeCard(null, VALID_INDEX));
        }
    }

    @Test
    public void constructor_invalidIndex_throwsAssertionError() {
        if (isJavaFxCompatible && initToolkitSuccess()) {
            assertThrows(AssertionError.class, () -> new TuteeCard(VALID_TUTEE, INVALID_INDEX));
        }
    }

    @Test
    public void constructor_validInput_success() {
        if (isJavaFxCompatible && initToolkitSuccess()) {
            TuteeCard validTuteeCard = new TuteeCard(VALID_TUTEE, VALID_INDEX);
        }
    }

    @Test
    public void addSubjectToTagTest_nullSubject_throwsNullPointerException() {
        if (isJavaFxCompatible && initToolkitSuccess()) {
            assertThrows(NullPointerException.class, () ->
                    new TuteeCard(VALID_TUTEE, VALID_INDEX).addSubjectToTag(null));
        }
    }

    @Test
    public void addSubjectToTagTest_invalidSubject_throwsIllegalArgumentException() {
        if (isJavaFxCompatible && initToolkitSuccess()) {
            assertThrows(IllegalArgumentException.class, () -> new TuteeCard(VALID_TUTEE, VALID_INDEX)
                    .addSubjectToTag(new Subject("math*")));
        }
    }

    @Test
    public void addSubjectToTagTest_validSubject_success() {
        if (isJavaFxCompatible && initToolkitSuccess()) {
            TuteeCard validTuteeCard = new TuteeCard(VALID_TUTEE_NO_TAGS, VALID_INDEX);
            Subject validSubject = new Subject("Math");
            validTuteeCard.addSubjectToTag(validSubject);

            Subject validSubject2 = new Subject("Science");
            validTuteeCard.addSubjectToTag(validSubject2);

            List<Tag> subjectsAsTags = validTuteeCard.getTags();
            assertEquals(validSubject.toString(), subjectsAsTags.get(0).tagName);
            assertEquals(validSubject2.toString(), subjectsAsTags.get(1).tagName);
        }
    }

    // Solution to initialise JavaFX toolkit below adapted from https://stackoverflow.com/a/53760312
    private boolean initToolkitSuccess() {
        try {
            Platform.startup(() -> {});
            isJavaFxCompatible = true;
            return true;
        } catch (UnsupportedOperationException | IllegalStateException e) {
            if (e instanceof UnsupportedOperationException) {
                // build (ubuntu-latest) on Codecov cannot run Platform.startup, this disables the testing for that run
                isJavaFxCompatible = false;
                return false;
            } else {
                // Attempting to run JavaFX toolkit after it has been initialised
                isJavaFxCompatible = true;
                return true;
            }
        }
    }
}
