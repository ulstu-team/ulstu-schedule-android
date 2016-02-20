package ru.ulstu_team.ulstuschedule.ui.common.teacher

import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.ui.base.MvpView

interface TeacherScheduleMvpView : MvpView {

    fun showSchedule(lessons: List<Lesson>)

    fun showEmptySchedule()

    fun showError()

}
