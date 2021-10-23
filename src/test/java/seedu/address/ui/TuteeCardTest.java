package seedu.address.ui;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TuteeCardTest {

    private static final int VALID_INDEX = 1;

    @Test
    public void constructor_appNotInitializedInvalidTutee_throwsExceptionInInitializerError() {
        assertThrows(ExceptionInInitializerError.class, () -> new TuteeCard(null, VALID_INDEX));
    }

}
