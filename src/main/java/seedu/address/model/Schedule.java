package seedu.address.model;

import java.util.List;
import java.util.TreeMap;

import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tutee.Tutee;

public class Schedule {

    private static final TreeMap<Lesson, String> sortedLessonsMap = new TreeMap<>();

    private final List<Tutee> tutees;

    public Schedule(List<Tutee> tutees) {
        this.tutees = tutees;
    }

    public void initSortedLessonsMap() throws ScheduleClashException {
        for (Tutee tutee : tutees) {
            List<Lesson> lessons = tutee.getLessons();
            String tuteeName = tutee.getName().toString();
            for (Lesson lesson : lessons) {
                add(lesson, tuteeName);
            }
        }
    }

    public static void add(Lesson lesson, String tuteeName) throws ScheduleClashException {
        if (isClash(lesson)) {
            throw new ScheduleClashException("Schedule clash for the lesson: " + lesson);
        }
        sortedLessonsMap.put(lesson, tuteeName);
    }

    private static boolean isClash(Lesson lesson) {
        return sortedLessonsMap.containsKey(lesson);
    }

    public static void remove(Lesson lesson, String tuteeName) {
        sortedLessonsMap.remove(lesson, tuteeName);
    }

}
