package ru.ulstu_team.ulstuschedule.ui.old.common.group

import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.ui.base.MvpView

interface GroupScheduleMvpView : MvpView {

    fun showSchedule(lessons: List<Lesson>)

    fun showEmptySchedule()

    fun showError()

}
