package seedu.address.model.tutee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tutee.exceptions.DuplicateTuteeException;
import seedu.address.model.tutee.exceptions.TuteeNotFoundException;

/**
 * A list of tutees that enforces uniqueness between its elements and does not allow nulls.
 * A tutee is considered unique by comparing using {@code Tutee#isSameTutee(Tutee)}. As such, adding and updating of
 * tutees uses Tutee#isSameTutee(Tutee) for equality so as to ensure that the tutee being added or updated is
 * unique in terms of identity in the UniqueTuteeList. However, the removal of a tutee uses Tutee#equals(Object) so
 * as to ensure that the tutee with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Tutee#isSameTutee(Tutee)
 */
public class UniqueTuteeList implements Iterable<Tutee> {

    private final ObservableList<Tutee> internalList = FXCollections.observableArrayList();
    private final ObservableList<Tutee> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent tutee as the given argument.
     */
    public boolean contains(Tutee toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTutee);
    }

    /**
     * Adds a tutee to the list.
     * The tutee must not already exist in the list.
     */
    public void add(Tutee toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTuteeException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the tutee {@code target} in the list with {@code editedTutee}.
     * {@code target} must exist in the list.
     * The tutee identity of {@code editedTutee} must not be the same as another existing tutee in the list.
     */
    public void setTutee(Tutee target, Tutee editedTutee) {
        requireAllNonNull(target, editedTutee);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TuteeNotFoundException();
        }

        if (!target.isSameTutee(editedTutee) && contains(editedTutee)) {
            throw new DuplicateTuteeException();
        }

        internalList.set(index, editedTutee);
    }

    /**
     * Removes the equivalent tutee from the list.
     * The tutee must exist in the list.
     */
    public void remove(Tutee toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TuteeNotFoundException();
        }
    }

    public void setTutees(UniqueTuteeList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tutees}.
     * {@code tutees} must not contain duplicate tutees.
     */
    public void setTutees(List<Tutee> tutees) {
        requireAllNonNull(tutees);
        if (!tuteesAreUnique(tutees)) {
            throw new DuplicateTuteeException();
        }

        internalList.setAll(tutees);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tutee> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Tutee> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTuteeList // instanceof handles nulls
                        && internalList.equals(((UniqueTuteeList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tutees} contains only unique tutees.
     */
    private boolean tuteesAreUnique(List<Tutee> tutees) {
        for (int i = 0; i < tutees.size() - 1; i++) {
            for (int j = i + 1; j < tutees.size(); j++) {
                if (tutees.get(i).isSameTutee(tutees.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
