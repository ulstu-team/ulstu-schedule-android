package ru.ulstu_team.ulstuschedule.ui.teachers

import ru.ulstu_team.ulstuschedule.data.DataManager
import ru.ulstu_team.ulstuschedule.data.remote.RequestCallbacks
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter
import javax.inject.Inject


class TeachersPresenter
@Inject
constructor(private val mDataManager: DataManager) : BasePresenter<TeachersMvpView>() {

    fun loadTeachers() {
        checkViewAttached()

        val teachers = mDataManager.getTeachers()
        if (teachers.isNotEmpty())
            mvpView!!.showTeachers(teachers)
        else
            mDataManager.loadTeachers(getTeachersRequestCallbacks())
    }

    fun loadCathedraTeachers(cathedraId: Int) {
        checkViewAttached()

        val teachers = mDataManager.getCathedraTeachers(cathedraId)
        if (teachers.isNotEmpty())
            mvpView!!.showTeachers(teachers)
        else
            mDataManager.loadCathedraTeachers(cathedraId,
                    getCathedraTeachersRequestCallbacks(cathedraId))
    }

    private fun getTeachersRequestCallbacks() =
            object: RequestCallbacks {
                override fun onSuccess() {
                    val teachers = mDataManager.getTeachers()
                    if (teachers.isNotEmpty())
                        mvpView!!.showTeachers(teachers)
                    else
                        mvpView!!.showEmptyList()
                }

                override fun onError(e: Exception) {
                    mvpView!!.showError()
                }
            }

    private fun getCathedraTeachersRequestCallbacks(cathedraId: Int) =
            object : RequestCallbacks {
                override fun onSuccess() {
                    val teachers = mDataManager.getCathedraTeachers(cathedraId)
                    if (teachers.isNotEmpty())
                        mvpView!!.showTeachers(teachers)
                    else
                        mvpView!!.showEmptyList()
                }

                override fun onError(e: Exception) {
                    mvpView!!.showError()
                }
            }
}

