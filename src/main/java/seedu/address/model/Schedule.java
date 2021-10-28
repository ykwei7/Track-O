package seedu.address.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tutee.Tutee;

/**
 * Represents the user's Schedule of lessons for the week.
 */
public class Schedule {

    public static final String SCHEDULE_CLASH_MESSAGE = "Schedule clash for the lesson: %1$s";

    /* Stores a sorted map of Lessons to tutees' names */
    private TreeMap<Lesson, String> sortedLessonsMap = new TreeMap<>();

    /**
     * Initialises the map with data retrieved from the tutee list.
     *
     * @param tutees List of tutees to process to populate the map.
     * @throws ScheduleClashException When there is a clash in lessons in the tutee list.
     */
    public Schedule(List<Tutee> tutees) throws ScheduleClashException {
        initSortedLessonsMap(tutees);
    }

    private void initSortedLessonsMap(List<Tutee> tutees) throws ScheduleClashException {
        for (Tutee tutee : tutees) {
            List<Lesson> lessons = tutee.getLessons();
            String tuteeName = tutee.getName().toString();
            for (Lesson lesson : lessons) {
                add(lesson, tuteeName);
            }
        }
    }

    /**
     * Clears the Schedule.
     */
    public void clear() {
        sortedLessonsMap = new TreeMap<>();
    }

    /**
     * Gets the map stored in Schedule.
     *
     * @return A copy of the TreeMap.
     */
    public TreeMap<Lesson, String> getSortedLessonsMap() {
        return new TreeMap<>(sortedLessonsMap);
    }

    /**
     * Adds a key-value pair to the map.
     *
     * @param lesson The corresponding key.
     * @param tuteeName The corresponding value.
     * @throws ScheduleClashException When the lesson clashes with the Schedule.
     */
    public void add(Lesson lesson, String tuteeName) throws ScheduleClashException {
        if (isClash(lesson)) {
            throw new ScheduleClashException(String.format(SCHEDULE_CLASH_MESSAGE, lesson));
        }
        sortedLessonsMap.put(lesson, tuteeName);
    }

    private boolean isClash(Lesson lesson) {
        return sortedLessonsMap.containsKey(lesson);
    }

    /**
     * Removes a key-value pair from the map.
     *
     * @param lesson The corresponding key.
     * @param tuteeName The corresponding value.
     * @return True if the key-value pair is removed; false otherwise.
     */
    public boolean remove(Lesson lesson, String tuteeName) {
        return sortedLessonsMap.remove(lesson, tuteeName);
    }

    /**
     * Removes all the lessons in the lesson list from the map.
     * Precondition: Every lesson in the lesson list must be in the map.
     *
     * @param lessons The list of keys to be removed one-by-one from the map.
     */
    public void removeLessons(List<Lesson> lessons) {
        for (Lesson lesson: lessons) {
            assert sortedLessonsMap.containsKey(lesson);
            sortedLessonsMap.remove(lesson);
        }
    }

    /**
     * Sets the value of the lessons stored in the map to a new value.
     * Precondition: Every lesson in the lesson list must be in the map.
     *
     * @param lessons The list of keys to be updated one-by-one in the map.
     * @param tuteeName The new value to be assigned to each of the given keys.
     */
    public void updateWithNewTuteeName(List<Lesson> lessons, String tuteeName) {
        for (Lesson lesson: lessons) {
            assert sortedLessonsMap.containsKey(lesson);
            sortedLessonsMap.put(lesson, tuteeName);
        }
    }

    @Override
    public String toString() {
        Set<Map.Entry<Lesson, String>> entrySet = sortedLessonsMap.entrySet();

        if (entrySet.isEmpty()) {
            return "There are no lessons scheduled for the week.";
        }

        final StringBuilder builder = new StringBuilder();

        for (Map.Entry<Lesson, String> entry : entrySet) {
            Lesson lesson = entry.getKey();
            String tuteeName = entry.getValue();

            builder.append("\n\u2022 ")
                    .append(lesson.toCondensedString())
                    .append("(")
                    .append(tuteeName)
                    .append(")\n");
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && sortedLessonsMap.equals(((Schedule) other).getSortedLessonsMap())); // state check
    }

}
