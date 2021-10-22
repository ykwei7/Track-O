package seedu.address.model.tutee;

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
    private final List<String> isOverdue;

    public CollectivePredicate(List<String> nameKeywords, List<String> levelKeywords,
                               List<String> subjectKeywords, List<String> isOverdue) {
        this.nameKeywords = nameKeywords;
        this.levelKeywords = levelKeywords;
        this.subjectKeywords = subjectKeywords;
        this.isOverdue = isOverdue;
    }

    @Override
    public boolean test(Tutee tutee) {
        boolean nameTest = nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tutee.getName().fullName, keyword));
        boolean levelTest = levelKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tutee.getLevel().getValue(), keyword));
        boolean subjectTest = subjectKeywords.stream()
                .anyMatch(keyword -> tutee.getLessons().stream()
                        .anyMatch(lesson -> StringUtil.containsWordIgnoreCase(lesson.getSubject().toString(), keyword)));
        boolean overdueTest = isOverdue.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(Boolean.toString(tutee.getPayment().isOverdue), keyword));
        return nameTest || levelTest || subjectTest ||overdueTest;
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
