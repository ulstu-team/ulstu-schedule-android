package ru.ulstu_team.ulstuschedule.ui.faculties

import javax.inject.Inject

import io.realm.RealmQuery
import ru.ulstu_team.ulstuschedule.data.DataManager
import ru.ulstu_team.ulstuschedule.data.model.Faculty
import ru.ulstu_team.ulstuschedule.data.remote.Schedule
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter

class FacultiesPresenter
@Inject
constructor(private val mDataManager: DataManager) : BasePresenter<FacultiesMvpView>() {

    fun loadFaculties() {
        checkViewAttached()

        val faculties = realmQuery.findAll()
        if (!faculties.isEmpty()) {
            mvpView?.showFaculties(faculties)
        } else {
            mDataManager.executeRequest(request)
        }
    }

    private val realmQuery: RealmQuery<Faculty>
        get() = mRealm.where(Faculty::class.java)

    val request: ScheduleRequest
        get() = ScheduleRequest(Schedule.FACULTIES, Faculty::class.java, realmQuery,
                object : ScheduleRequest.Callbacks {
                    override fun onSuccess() {
                        val faculties = realmQuery.findAll()
                        mvpView?.showFaculties(faculties)
                    }

                    override fun onError(e: Exception) {
                        mvpView?.showError()
                    }
                })
}
