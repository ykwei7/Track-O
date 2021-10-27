package seedu.address.model.tutee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Tutee}'s {@code Name} matches any of the keywords given.
 */
public class CollectivePredicate implements Predicate<Tutee> {
    private final List<String> nameKeywords;
    private final List<String> levelKeywords;
    private final List<String> subjectKeywords;
    private final List<String> overdueKeyword;

    /**
     * Constructs a {@code CollectivePredicate} using a list of keywords for each search criteria.
     *
     * @param nameKeywords list of name keywords.
     * @param levelKeywords list of level keyword.
     * @param subjectKeywords list of level keyword.
     * @param isOverdue list of overdue keyword.
     */
    public CollectivePredicate(List<String> nameKeywords, List<String> levelKeywords,
                               List<String> subjectKeywords, List<String> isOverdue) {
        this.nameKeywords = nameKeywords;
        this.levelKeywords = levelKeywords;
        this.subjectKeywords = subjectKeywords;
        this.overdueKeyword = isOverdue;
    }

    @Override
    public boolean test(Tutee tutee) {
        return Arrays.stream(initialiseTests(tutee)).allMatch(bool -> bool)
                && Arrays.stream(initialiseTests(tutee)).count() > 0;
    }

    private Boolean[] initialiseTests(Tutee tutee) {
        List<Boolean> activeTests = new ArrayList<>();

        boolean nameTest = nameKeywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(tutee.getName().fullName, keyword));
        boolean levelTest = levelKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tutee.getLevel().getValue(), keyword));
        boolean subjectTest = subjectKeywords.stream()
                .allMatch(keyword -> tutee.getLessons().stream()
                        .anyMatch(lesson ->
                                StringUtil.containsWordIgnoreCase(lesson.getSubject().toString(), keyword)));
        boolean overdueTest = overdueKeyword.stream()
                .allMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(Boolean.toString(tutee.getPayment().isOverdue), keyword));

        if (nameKeywords.size() > 0) {
            activeTests.add(nameTest);
        }
        if (levelKeywords.size() > 0) {
            activeTests.add(levelTest);
        }
        if (subjectKeywords.size() > 0) {
            activeTests.add(subjectTest);
        }
        if (overdueKeyword.size() > 0) {
            activeTests.add(overdueTest);
        }
        return activeTests.toArray(new Boolean[0]);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CollectivePredicate // instanceof handles nulls
                && (nameKeywords.equals(((CollectivePredicate) other).nameKeywords)
                    && levelKeywords.equals(((CollectivePredicate) other).levelKeywords)
                    && subjectKeywords.equals(((CollectivePredicate) other).subjectKeywords))); // state check
    }

}
