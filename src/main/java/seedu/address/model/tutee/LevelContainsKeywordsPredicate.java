package seedu.address.model.tutee;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Tutee}'s {@code Name} matches any of the keywords given.
 */
public class LevelContainsKeywordsPredicate implements Predicate<Tutee> {
    private final List<String> keywords;

    public LevelContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tutee tutee) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tutee.getLevel().getValue(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LevelContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((LevelContainsKeywordsPredicate) other).keywords)); // state check
    }

}
