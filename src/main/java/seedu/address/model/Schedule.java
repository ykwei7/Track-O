package seedu.address.model;

import java.util.List;
import java.util.TreeMap;

import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tutee.Tutee;

/**
 * Represents the user's Schedule of lessons for the week.
 */
public class Schedule {

    /* Stores a sorted map of Lessons to tutees' names */
    private static TreeMap<Lesson, String> sortedLessonsMap = new TreeMap<>();

    /**
     * Initialises the map with data retrieved from the tutee list.
     *
     * @param tutees List of tutees to process to populate the map.
     * @throws ScheduleClashException When there is a clash in lessons in the tutee list.
     */
    public static void initSortedLessonsMap(List<Tutee> tutees) throws ScheduleClashException {
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
    public static void clear() {
        sortedLessonsMap = new TreeMap<>();
    }

    /**
     * Gets the map stored in Schedule.
     *
     * @return A copy of the TreeMap.
     */
    public static TreeMap<Lesson, String> getSortedLessonsMap() {
        return new TreeMap<>(sortedLessonsMap);
    }

    /**
     * Adds a key-value pair to the map.
     *
     * @param lesson The corresponding key.
     * @param tuteeName The corresponding value.
     * @throws ScheduleClashException When the lesson clashes with the Schedule.
     */
    public static void add(Lesson lesson, String tuteeName) throws ScheduleClashException {
        if (isClash(lesson)) {
            throw new ScheduleClashException("Schedule clash for the lesson: " + lesson);
        }
        sortedLessonsMap.put(lesson, tuteeName);
    }

    private static boolean isClash(Lesson lesson) {
        return sortedLessonsMap.containsKey(lesson);
    }

    /**
     * Removes a key-value pair from the map.
     *
     * @param lesson The corresponding key.
     * @param tuteeName The corresponding value.
     * @return True if the key-value pair is removed; false otherwise.
     */
    public static boolean remove(Lesson lesson, String tuteeName) {
        return sortedLessonsMap.remove(lesson, tuteeName);
    }

}
