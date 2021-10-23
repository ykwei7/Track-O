package seedu.address.model.tutee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
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
    private List<String> emptyKeywordList = Collections.emptyList();

    private Subject subject = new Subject("Math");
    private Time time = new Time(DayOfWeek.FRIDAY, LocalTime.NOON, LocalTime.of(18, 0));
    private double cost = 45.0;
    private Lesson lesson = new Lesson(subject, time, cost);

    private Subject subject2 = new Subject("Chemistry");
    private Time time2 = new Time(DayOfWeek.SATURDAY, LocalTime.NOON, LocalTime.of(18, 0));
    private Lesson lesson2 = new Lesson(subject2, time2, cost);

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CollectivePredicate firstPredicate = new CollectivePredicate(firstPredicateKeywordList,
                emptyKeywordList, emptyKeywordList, emptyKeywordList);
        CollectivePredicate secondPredicate = new CollectivePredicate(secondPredicateKeywordList,
                emptyKeywordList, emptyKeywordList, emptyKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CollectivePredicate firstPredicateCopy = new CollectivePredicate(firstPredicateKeywordList,
                emptyKeywordList, emptyKeywordList, emptyKeywordList);
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
        CollectivePredicate predicate = new CollectivePredicate(Collections.singletonList("Alice"),
                emptyKeywordList, emptyKeywordList, emptyKeywordList);
        assertTrue(predicate.test(new TuteeBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new CollectivePredicate(Arrays.asList("Alice", "Bob"),
                emptyKeywordList, emptyKeywordList, emptyKeywordList);
        assertTrue(predicate.test(new TuteeBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new CollectivePredicate(Arrays.asList("Bob", "Carol"),
                emptyKeywordList, emptyKeywordList, emptyKeywordList);
        assertFalse(predicate.test(new TuteeBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new CollectivePredicate(Arrays.asList("aLIce", "bOB"),
                emptyKeywordList, emptyKeywordList, emptyKeywordList);
        assertTrue(predicate.test(new TuteeBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        CollectivePredicate predicate = new CollectivePredicate(emptyKeywordList,
                emptyKeywordList, emptyKeywordList, emptyKeywordList);
        predicate = new CollectivePredicate(Arrays.asList("Carol"),
                emptyKeywordList, emptyKeywordList, emptyKeywordList);
        assertFalse(predicate.test(new TuteeBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new CollectivePredicate(Arrays.asList("Street"),
                emptyKeywordList, emptyKeywordList, emptyKeywordList);
        assertFalse(predicate.test(new TuteeBuilder().withName("Alice").withPhone("12345")
                .withLevel("p2").withAddress("Main Street").build()));
    }

    @Test
    public void test_levelContainsKeywords_returnsTrue() {
        // One keyword
        CollectivePredicate predicate = new CollectivePredicate(emptyKeywordList,
                Collections.singletonList("p5"), emptyKeywordList, emptyKeywordList);
        assertTrue(predicate.test(new TuteeBuilder().withLevel("p5").build()));

        // Non-matching keyword
        predicate = new CollectivePredicate(emptyKeywordList,
                Collections.singletonList("p5"), emptyKeywordList, emptyKeywordList);
        assertFalse(predicate.test(new TuteeBuilder().withLevel("p4").build()));
    }

    @Test
    public void test_levelDoesNotContainsKeywords_returnsFalse() {
        // Non-matching keyword
        CollectivePredicate predicate = new CollectivePredicate(emptyKeywordList,
                Collections.singletonList("p4"), emptyKeywordList, emptyKeywordList);
        assertFalse(predicate.test(new TuteeBuilder().withLevel("p5").build()));

        // Keyword matches name but not level
        predicate = new CollectivePredicate(emptyKeywordList,
                Collections.singletonList("p5"), emptyKeywordList, emptyKeywordList);
        assertFalse(predicate.test(new TuteeBuilder().withName("p4").build()));
    }

    @Test
    public void test_nubjectContainsKeywords_returnsTrue() {
        // One keyword
        CollectivePredicate predicate = new CollectivePredicate(emptyKeywordList,
                emptyKeywordList, Collections.singletonList("Math"), emptyKeywordList);
        assertTrue(predicate.test(new TuteeBuilder().withLesson(lesson).build()));

        // Two keyword
        predicate = new CollectivePredicate(emptyKeywordList,
                emptyKeywordList, Arrays.asList("Math", "Chemistry"), emptyKeywordList);
        assertTrue(predicate.test(new TuteeBuilder().withLesson(lesson).withLesson(lesson2).build()));

        // Mixed-case keyword
        predicate = new CollectivePredicate(emptyKeywordList,
                emptyKeywordList, Collections.singletonList("math"), emptyKeywordList);
        assertTrue(predicate.test(new TuteeBuilder().withLesson(lesson).build()));
    }

    @Test
    public void test_subjectDoesNotContainsKeywords_returnsFalse() {
        // Non-matching keyword
        CollectivePredicate predicate = new CollectivePredicate(emptyKeywordList,
                emptyKeywordList, Collections.singletonList("Math"), emptyKeywordList);
        assertFalse(predicate.test(new TuteeBuilder().withLesson(lesson2).build()));

        // Keyword matches name but not level
        predicate = new CollectivePredicate(emptyKeywordList,
                emptyKeywordList, Collections.singletonList("Math"), emptyKeywordList);
        assertFalse(predicate.test(new TuteeBuilder().withName("chemistry").withLesson(lesson2).build()));

        // Tutee has no lessons
        predicate = new CollectivePredicate(emptyKeywordList,
                emptyKeywordList, Collections.singletonList("Math"), emptyKeywordList);
        assertFalse(predicate.test(new TuteeBuilder().build()));
    }
}
