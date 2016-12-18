package ru.ulstu_team.ulstuschedule.ui.old.groups

import ru.ulstu_team.ulstuschedule.data.DataManager
import ru.ulstu_team.ulstuschedule.data.remote.RequestCallbacks
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter
import javax.inject.Inject

class GroupsPresenter
@Inject
constructor(private val mDataManager: DataManager) : BasePresenter<GroupsMvpView>() {

    fun loadGroups() {
        checkViewAttached()

        val groups = mDataManager.getGroups()
        if (groups.size > 50)
            mvpView!!.showGroups(groups)
        else
            mDataManager.loadGroups(getGroupsRequestCallbacks())
    }

    private fun getGroupsRequestCallbacks(): RequestCallbacks =
            object : RequestCallbacks {
                override fun onSuccess() {
                    val groups = mDataManager.getGroups()
                    if (groups.isNotEmpty())
                        mvpView!!.showGroups(groups)
                    else
                        mvpView!!.showEmptyList()
                }

                override fun onError(e: Exception) {
                    mvpView!!.showError()
                }
            }
}
