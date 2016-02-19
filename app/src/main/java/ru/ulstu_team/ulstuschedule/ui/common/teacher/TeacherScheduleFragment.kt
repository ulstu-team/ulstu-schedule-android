package ru.ulstu_team.ulstuschedule.ui.common.teacher

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.schedule.*

import java.util.ArrayList

import javax.inject.Inject

import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.ui.base.BaseFragment
import ru.ulstu_team.ulstuschedule.ui.common.StickyListScheduleAdapter

class TeacherScheduleFragment : BaseFragment(), TeacherScheduleMvpView {

    @Inject
    internal lateinit var mAdapter: StickyListScheduleAdapter
    @Inject
    internal lateinit var mPresenter: TeacherSchedulePresenter
    private lateinit  var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        getTeacherScheduleComponent().inject(this)
        mView = inflater.inflate(R.layout.schedule, container, false)

        mPresenter.attachView(this)
        mPresenter.loadSchedule()

        srlRefresh.setOnRefreshListener({
            mPresenter.loadSchedule()
            srlRefresh.isRefreshing = false
        })
        return mView
    }

    override fun showSchedule(lessons: List<Lesson>) {
        mAdapter.setLessons(lessons, true)
        slTeacherLessons.adapter = mAdapter
    }

    override fun showEmptySchedule() {
        mAdapter.setLessons(ArrayList<Lesson>(), true)
    }

    override fun showError() {
        Snackbar.make(mView, "Возниикла ошибка", Snackbar.LENGTH_LONG)
                .setAction("Повторить", { reload() })
                .show()
    }

    override fun reload() {
        mPresenter.reload()
    }

    companion object {
        val TAG = "TeacherScheduleFragment"
    }
}
