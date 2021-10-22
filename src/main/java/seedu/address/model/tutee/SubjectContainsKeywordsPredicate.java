package seedu.address.model.tutee;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.lesson.Subject;

/**
 * Tests that a {@code Tutee}'s {@code Name} matches any of the keywords given.
 */
public class SubjectContainsKeywordsPredicate implements Predicate<Tutee> {
    private final List<String> keywords;

    public SubjectContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tutee tutee) {
        return keywords.stream()
                .anyMatch(keyword -> tutee.getLessons().stream()
                        .anyMatch(lesson -> StringUtil.containsWordIgnoreCase(lesson.getSubject().toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SubjectContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SubjectContainsKeywordsPredicate) other).keywords)); // state check
    }

}
