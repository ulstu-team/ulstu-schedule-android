package ru.ulstu_team.ulstuschedule.ui.old.common.teacher

import javax.inject.Inject

import io.realm.RealmQuery
import ru.ulstu_team.ulstuschedule.data.DataManager
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.data.remote.RequestCallbacks
import ru.ulstu_team.ulstuschedule.data.remote.Schedule
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter

class TeacherSchedulePresenter
@Inject
constructor(private val mDataManager: DataManager) : BasePresenter<TeacherScheduleMvpView>() {


    fun loadScheduleForCurrentTeacher() {
        checkViewAttached()

        val lessons = mDataManager.getLessonsForCurrentTeacher()
        if (lessons.isNotEmpty())
            mvpView!!.showSchedule(lessons)
        else
            mDataManager.loadLessonsForCurrentTeacher(getCurrentTeacherRequestCallbacks())
    }

    fun loadScheduleForTeacher(teacherId: Int) {
        checkViewAttached()

        val lessons = mDataManager.getLessonsForTeacher(teacherId)
        if (lessons.isNotEmpty())
            mvpView!!.showSchedule(lessons)
        else
            mDataManager.loadLessonsForTeacher(teacherId, getTeacherRequestCallbacks(teacherId))
    }

    private fun getCurrentTeacherRequestCallbacks(): RequestCallbacks =
            object: RequestCallbacks {
                override fun onSuccess() {
                    val lessons = mDataManager.getLessonsForCurrentTeacher()
                    if (lessons.isNotEmpty())
                        mvpView!!.showSchedule(lessons)
                    else mvpView!!.showEmptySchedule()
                }

                override fun onError(e: Exception) {
                    mvpView!!.showError()
                }
            }

    private fun getTeacherRequestCallbacks(teacherId: Int): RequestCallbacks =
            object: RequestCallbacks {
                override fun onSuccess() {
                    val lessons = mDataManager.getLessonsForTeacher(teacherId)
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
