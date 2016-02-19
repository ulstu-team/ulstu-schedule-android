package ru.ulstu_team.ulstuschedule.ui.cathedries

import io.realm.RealmQuery
import ru.ulstu_team.ulstuschedule.data.DataManager
import ru.ulstu_team.ulstuschedule.data.model.Cathedra
import ru.ulstu_team.ulstuschedule.data.remote.Schedule
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter
import javax.inject.Inject

class CathedriesPresenter
@Inject
constructor(private val mDataManager: DataManager) : BasePresenter<CathedriesMvpView>() {

    fun loadCathedries() {
        checkViewAttached()

        val cathedries = getAllCathedriesQuery().findAll()
        if (!cathedries.isEmpty()) {
            mvpView?.showCathedries(cathedries)
        } else {
            mDataManager.executeRequest(getRequest(getAllCathedriesQuery()))
        }
    }

    fun loadCathedries(facultyId: Int) {
        checkViewAttached()

        val cathedries =
            if (facultyId <= 0) getAllCathedriesQuery().findAll()
            else getFacultyCathedriesQuery(facultyId).findAll()

        if (!cathedries.isEmpty())
            mvpView?.showCathedries(cathedries)
        else
            mDataManager.executeRequest(getRequest(getFacultyCathedriesQuery(facultyId)))
    }

    private fun getAllCathedriesQuery(): RealmQuery<Cathedra> =
            mRealm.where(Cathedra::class.java)

    private fun getFacultyCathedriesQuery(facultyId: Int): RealmQuery<Cathedra> =
            mRealm.where(Cathedra::class.java).equalTo("FacultyId", facultyId)

    private fun getRequest(query: RealmQuery<Cathedra>): ScheduleRequest {
        return ScheduleRequest(Schedule.CATHEDRIES, Cathedra::class.java, query,
                object : ScheduleRequest.Callbacks {
                    override fun onSuccess() {
                        val cathedries = query.findAll()
                        mvpView?.showCathedries(cathedries)
                    }

                    override fun onError(e: Exception) {
                        mvpView?.showError()
                    }
                })
    }
}
