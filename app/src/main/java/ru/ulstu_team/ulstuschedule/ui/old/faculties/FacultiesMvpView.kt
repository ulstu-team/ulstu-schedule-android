package ru.ulstu_team.ulstuschedule.ui.old.faculties

import ru.ulstu_team.ulstuschedule.data.model.Faculty
import ru.ulstu_team.ulstuschedule.ui.base.MvpView

interface FacultiesMvpView : MvpView {

    fun showFaculties(faculties: List<Faculty>)

    fun showEmptyList()

    fun showError()

}
