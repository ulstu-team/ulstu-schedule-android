package ru.ulstu_team.ulstuschedule.data.model;

import java.util.Comparator;

public class LessonComparator implements Comparator<Lesson> {
    @Override
    public int compare(Lesson lhs, Lesson rhs) {
        return getRate(rhs) - getRate(lhs);
    }

    private int getRate(Lesson lesson) {
        int rate = 0;
        rate += (3 - lesson.getNumberOfWeek()) * 100;
        rate += (7 - lesson.getDayOfWeek()) * 10;
        rate += (9 - lesson.getNumber());
        return rate;
    }
}
