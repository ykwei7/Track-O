package seedu.address.model.tutee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Time;
import seedu.address.testutil.TuteeBuilder;

public class CollectivePredicateTest {
    private static final List<String> EMPTY_KEYWORD_LIST = Collections.emptyList();

    private static final Subject VALID_SUBJECT_1 = new Subject("Math");
    private static final Time VALID_TIME_1 = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(18, 0));
    private static final double VALID_COST = 45.0;
    private static final Lesson VALID_LESSON_1 = new Lesson(VALID_SUBJECT_1, VALID_TIME_1, VALID_COST);

    private static final Subject VALID_SUBJECT_2 = new Subject("Chemistry");
    private static final Time VALID_TIME_2 = new Time(DayOfWeek.SATURDAY, LocalTime.NOON, LocalTime.of(18, 0));
    private static final Lesson VALID_LESSON_2 = new Lesson(VALID_SUBJECT_2, VALID_TIME_2, VALID_COST);

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CollectivePredicate firstPredicate = new CollectivePredicate(firstPredicateKeywordList,
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        CollectivePredicate secondPredicate = new CollectivePredicate(secondPredicateKeywordList,
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CollectivePredicate firstPredicateCopy = new CollectivePredicate(firstPredicateKeywordList,
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tutee -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        CollectivePredicate predicate1 = new CollectivePredicate(Collections.singletonList("Alice"),
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertTrue(predicate1.test(new TuteeBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        CollectivePredicate predicate2 = new CollectivePredicate(Arrays.asList("Alice", "Bob"),
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertTrue(predicate2.test(new TuteeBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        CollectivePredicate predicate3 = new CollectivePredicate(Arrays.asList("aLIce", "bOB"),
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertTrue(predicate3.test(new TuteeBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        CollectivePredicate predicate1 = new CollectivePredicate(Arrays.asList("Carol"),
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertFalse(predicate1.test(new TuteeBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        CollectivePredicate predicate2 = new CollectivePredicate(Arrays.asList("Street"),
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertFalse(predicate2.test(new TuteeBuilder().withName("Alice").withPhone("12345678")
                .withLevel("p2").withAddress("Main Street").build()));

        // Only one matching keyword
        CollectivePredicate predicate3 = new CollectivePredicate(Arrays.asList("Bob", "Carol"),
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertFalse(predicate3.test(new TuteeBuilder().withName("Alice Carol").build()));
    }

    @Test
    public void test_nameOnlyOneContainKeywords_returnsFalse() {
        // Only one matching keyword
        CollectivePredicate predicate1 = new CollectivePredicate(Arrays.asList("Bob", "Carol"),
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertFalse(predicate1.test(new TuteeBuilder().withName("Alice Carol").build()));
    }

    @Test
    public void test_levelContainsKeywords_returnsTrue() {
        // One keyword
        CollectivePredicate predicate1 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                Collections.singletonList("p5"), EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertTrue(predicate1.test(new TuteeBuilder().withLevel("p5").build()));
    }

    @Test
    public void test_levelDoesNotContainsKeywords_returnsFalse() {
        // Non-matching keyword
        CollectivePredicate predicate1 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                Collections.singletonList("p4"), EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertFalse(predicate1.test(new TuteeBuilder().withLevel("p5").build()));

        // Keyword matches name but not level
        CollectivePredicate predicate2 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                Collections.singletonList("p5"), EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST);
        assertFalse(predicate2.test(new TuteeBuilder().withName("p4").build()));
    }

    @Test
    public void test_subjectContainsKeywords_returnsTrue() {
        // One keyword
        CollectivePredicate predicate1 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, Collections.singletonList("Math"), EMPTY_KEYWORD_LIST);
        assertTrue(predicate1.test(new TuteeBuilder().withLesson(VALID_LESSON_1).build()));

        // Two keyword
        CollectivePredicate predicate2 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, Arrays.asList("Math", "Chemistry"), EMPTY_KEYWORD_LIST);
        assertTrue(predicate2.test(new TuteeBuilder().withLesson(VALID_LESSON_1).withLesson(VALID_LESSON_2).build()));

        // Mixed-case keyword
        CollectivePredicate predicate3 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, Collections.singletonList("math"), EMPTY_KEYWORD_LIST);
        assertTrue(predicate3.test(new TuteeBuilder().withLesson(VALID_LESSON_1).build()));
    }

    @Test
    public void test_subjectDoesNotContainsKeywords_returnsFalse() {
        // Non-matching keyword
        CollectivePredicate predicate1 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, Collections.singletonList("Math"), EMPTY_KEYWORD_LIST);
        assertFalse(predicate1.test(new TuteeBuilder().withLesson(VALID_LESSON_2).build()));

        // Only 1 subject matches
        CollectivePredicate predicate2 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, Arrays.asList("Chemistry", "Math"), EMPTY_KEYWORD_LIST);
        assertFalse(predicate2.test(new TuteeBuilder().withLesson(VALID_LESSON_2).build()));

        // Keyword matches name but not level
        CollectivePredicate predicate3 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, Collections.singletonList("Math"), EMPTY_KEYWORD_LIST);
        assertFalse(predicate3.test(new TuteeBuilder().withName("chemistry").withLesson(VALID_LESSON_2).build()));

        // Tutee has no lessons
        CollectivePredicate predicate4 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, Collections.singletonList("Math"), EMPTY_KEYWORD_LIST);
        assertFalse(predicate4.test(new TuteeBuilder().build()));
    }

    @Test
    public void test_overdueContainsKeywords_returnsTrue() {
        // One keyword return true
        CollectivePredicate predicate1 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, Collections.singletonList("true"));
        assertTrue(predicate1.test(new TuteeBuilder().withPayment("100", LocalDate.now().minusDays(1)).build()));

        // Mixed-case keyword return true
        CollectivePredicate predicate2 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, Collections.singletonList("tRue"));
        assertTrue(predicate2.test(new TuteeBuilder().withPayment("100", LocalDate.now().minusDays(1)).build()));
    }

    @Test
    public void test_overdueDoesNotContainsKeywords_returnsFalse() {
        // One keyword return false
        CollectivePredicate predicate1 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, Collections.singletonList("true"));
        assertFalse(predicate1.test(new TuteeBuilder().withPayment("100", LocalDate.now().plusDays(1)).build()));

        // 2 keywords return false
        CollectivePredicate predicate2 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, Arrays.asList("true", "false"));
        assertFalse(predicate2.test(new TuteeBuilder().withPayment("100", LocalDate.now().minusDays(1)).build()));

        CollectivePredicate predicate3 = new CollectivePredicate(EMPTY_KEYWORD_LIST,
                EMPTY_KEYWORD_LIST, EMPTY_KEYWORD_LIST, Arrays.asList("true", "false"));
        assertFalse(predicate3.test(new TuteeBuilder().withPayment("100", null).build()));
    }
}
