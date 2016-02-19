package ru.ulstu_team.ulstuschedule.ui.cathedries

import ru.ulstu_team.ulstuschedule.data.model.Cathedra
import ru.ulstu_team.ulstuschedule.ui.base.MvpView

interface CathedriesMvpView : MvpView {

    fun showCathedries(cathedries: List<Cathedra>)

    fun showEmptyList()

    fun showError()

}
