package ru.ulstu_team.ulstuschedule.ui.common.student

import javax.inject.Inject

import io.realm.RealmQuery
import ru.ulstu_team.ulstuschedule.data.DataManager
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.data.remote.Schedule
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter

class StudentSchedulePresenter
@Inject
constructor(private val mDataManager: DataManager) : BasePresenter<StudentScheduleMvpView>() {
    private var mGroupId: Int = 0

    fun loadSchedule(groupId: Int = 0) {
        checkViewAttached()
        mGroupId = groupId

        val lessons = realmQuery.findAll()
        if (!lessons.isEmpty()) {
            mvpView!!.showSchedule(lessons)
        } else {
            //mDataManager.executeRequest(request)
        }
    }

    private val realmQuery: RealmQuery<Lesson>
        get() = if (mGroupId <= 0)
            mRealm.where(Lesson::class.java).equalTo("GroupId", mDataManager.userId)
        else
            mRealm.where(Lesson::class.java).equalTo("GroupId", mGroupId)

//    private val request: ScheduleRequest
//        get() = ScheduleRequest(Schedule.GROUP_LESSONS,
//                mDataManager.userId, Lesson::class.java, realmQuery,
//                object : ScheduleRequest.Callbacks {
//                    override fun onSuccess() {
//                        val lessons = realmQuery.findAll()
//                        if (!lessons.isEmpty())
//                            mvpView!!.showSchedule(lessons)
//                        else
//                            mvpView!!.showEmptySchedule()
//                    }
//
//                    override fun onError(e: Exception) {
//                        mvpView!!.showError()
//                    }
//                })

    fun reload() {
        loadSchedule()
    }
}
