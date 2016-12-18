package ru.ulstu_team.ulstuschedule.ui.old.groups

import ru.ulstu_team.ulstuschedule.data.model.Group
import ru.ulstu_team.ulstuschedule.ui.base.MvpView

interface GroupsMvpView : MvpView {

    fun showGroups(groups: List<Group>)

    fun showEmptyList()

    fun showError()

}
