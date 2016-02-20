package ru.ulstu_team.ulstuschedule.ui.common.student

import ru.ulstu_team.ulstuschedule.data.DataManager
import ru.ulstu_team.ulstuschedule.data.remote.RequestCallbacks
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter
import javax.inject.Inject

class StudentSchedulePresenter
@Inject
constructor(private val mDataManager: DataManager) : BasePresenter<StudentScheduleMvpView>() {
    private var mGroupId: Int = 0

    fun loadScheduleForCurrentGroup() {
        checkViewAttached()
        val lessons = mDataManager.getLessonsForCurrentGroup()
        if (lessons.isNotEmpty())
            mvpView!!.showSchedule(lessons)
        else
            mDataManager.loadLessonsForCurrentGroup(getCurrentGroupRequestCallbacks())
    }

    fun loadGroupSchedule(groupId: Int) {
        checkViewAttached()
        mGroupId = groupId

        val lessons = mDataManager.getLessonsForGroup(groupId)
        if (lessons.isNotEmpty())
            mvpView!!.showSchedule(lessons)
        else
            mDataManager.loadLessonsForGroup(groupId, getGroupRequestCallbacks(groupId))
    }

    private fun getCurrentGroupRequestCallbacks(): RequestCallbacks =
            object : RequestCallbacks {
                override fun onSuccess() {
                    val lessons = mDataManager.getLessonsForCurrentGroup()
                    if (lessons.isNotEmpty())
                        mvpView!!.showSchedule(lessons)
                    else
                        mvpView!!.showEmptySchedule()
                }

                override fun onError(e: Exception) {
                    mvpView!!.showError()
                }
            }

    private fun getGroupRequestCallbacks(groupId: Int) =
            object : RequestCallbacks {
                override fun onSuccess() {
                    val lessons = mDataManager.getLessonsForGroup(groupId)
                    if (lessons.isNotEmpty())
                        mvpView!!.showSchedule(lessons)
                    else
                        mvpView!!.showEmptySchedule()
                }

                override fun onError(e: Exception) {
                    mvpView!!.showError()
                }
            }
}
