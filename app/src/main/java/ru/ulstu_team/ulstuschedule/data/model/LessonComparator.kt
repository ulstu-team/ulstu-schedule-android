package ru.ulstu_team.ulstuschedule.data.model

import java.util.Comparator

class LessonComparator : Comparator<Lesson> {
    override fun compare(lhs: Lesson, rhs: Lesson): Int {
        return getRate(rhs) - getRate(lhs)
    }

    private fun getRate(lesson: Lesson): Int {
        var rate = 0
        rate += (3 - lesson.numberOfWeek) * 100
        rate += (7 - lesson.dayOfWeek) * 10
        rate += 9 - lesson.number
        return rate
    }
}
