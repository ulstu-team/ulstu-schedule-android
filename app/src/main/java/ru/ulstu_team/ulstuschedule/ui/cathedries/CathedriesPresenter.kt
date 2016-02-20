package ru.ulstu_team.ulstuschedule.ui.cathedries

import ru.ulstu_team.ulstuschedule.data.DataManager
import ru.ulstu_team.ulstuschedule.data.remote.RequestCallbacks
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter
import javax.inject.Inject

class CathedriesPresenter
@Inject
constructor(private val mDataManager: DataManager) : BasePresenter<CathedriesMvpView>() {

    fun loadCathedries() {
        checkViewAttached()

        val cathedries = mDataManager.getCathedries()
        if (cathedries.isNotEmpty())
            mvpView!!.showCathedries(cathedries)
        else
            mDataManager.loadCathedries(getCathedriesRequestCallbacks())
    }

    fun loadCathedries(facultyId: Int) {
        checkViewAttached()

        val cathedries = mDataManager.getFacultyCathedries(facultyId)
        if (!cathedries.isEmpty())
            mvpView!!.showCathedries(cathedries)
        else
            mDataManager.loadFacultyCathedries(facultyId,
                    getFacultyCathedriesRequestCallbacks(facultyId))
    }

    private fun getCathedriesRequestCallbacks(): RequestCallbacks =
            object: RequestCallbacks {
                override fun onSuccess() {
                    val cathedries = mDataManager.getCathedries()
                    if (cathedries.isNotEmpty())
                        mvpView!!.showCathedries(cathedries)
                    else
                        mvpView!!.showEmptyList()
                }

                override fun onError(e: Exception) {
                    mvpView!!.showError()
                }
            }

    private fun getFacultyCathedriesRequestCallbacks(facultyId: Int): RequestCallbacks =
            object: RequestCallbacks {
                override fun onSuccess() {
                    val cathedries = mDataManager.getFacultyCathedries(facultyId)
                    if (cathedries.isNotEmpty())
                        mvpView!!.showCathedries(cathedries)
                    else
                        mvpView!!.showEmptyList()
                }

                override fun onError(e: Exception) {
                    mvpView!!.showError()
                }
            }
}
