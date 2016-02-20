package ru.ulstu_team.ulstuschedule.ui.common.student

import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.ui.base.MvpView

interface StudentScheduleMvpView : MvpView {

    fun showSchedule(lessons: List<Lesson>)

    fun showEmptySchedule()

    fun showError()

}
