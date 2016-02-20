package ru.ulstu_team.ulstuschedule.ui.faculties

import javax.inject.Inject

import io.realm.RealmQuery
import ru.ulstu_team.ulstuschedule.data.DataManager
import ru.ulstu_team.ulstuschedule.data.model.Faculty
import ru.ulstu_team.ulstuschedule.data.remote.RequestCallbacks
import ru.ulstu_team.ulstuschedule.data.remote.Schedule
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter

class FacultiesPresenter
@Inject
constructor(private val mDataManager: DataManager) : BasePresenter<FacultiesMvpView>() {

    fun loadFaculties() {
        checkViewAttached()

        val faculties = mDataManager.getFaculties()
        if (!faculties.isEmpty()) {
            mvpView?.showFaculties(faculties)
        } else {
            mDataManager.loadFaculties(callbacks)
        }
    }

    private val callbacks: RequestCallbacks
        get() = object: RequestCallbacks{
            override fun onSuccess() {
                val faculties = mDataManager.getFaculties()
                if (faculties.isNotEmpty())
                    mvpView?.showFaculties(faculties)
                else
                    mvpView?.showEmptyList()
            }

            override fun onError(e: Exception) {
                mvpView?.showError()
            }

        }
}
