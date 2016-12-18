package ru.ulstu_team.ulstuschedule.ui.old.teachers

import ru.ulstu_team.ulstuschedule.data.model.Teacher
import ru.ulstu_team.ulstuschedule.ui.base.MvpView

interface  TeachersMvpView : MvpView {

    fun showTeachers(teachers: List<Teacher>)

    fun showEmptyList()

    fun showError()

}
