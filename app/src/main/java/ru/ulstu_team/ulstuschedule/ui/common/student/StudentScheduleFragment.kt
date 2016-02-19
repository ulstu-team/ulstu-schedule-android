package ru.ulstu_team.ulstuschedule.ui.common.student

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.databinding.ScheduleBinding
import ru.ulstu_team.ulstuschedule.ui.base.BaseFragment
import ru.ulstu_team.ulstuschedule.ui.common.StickyListScheduleAdapter
import java.util.*
import javax.inject.Inject

class StudentScheduleFragment() : BaseFragment(), StudentScheduleMvpView {

    @Inject
    internal lateinit var mAdapter: StickyListScheduleAdapter
    @Inject
    internal lateinit var mPresenter: StudentSchedulePresenter
    private var b: ScheduleBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        getStudentScheduleComponent().inject(this)
        b = DataBindingUtil.inflate(inflater, R.layout.schedule, container, false)
        mPresenter.attachView(this)
        mPresenter.loadSchedule()

        b!!.srlRefresh.setOnRefreshListener { mPresenter.loadSchedule() }

        return b!!.root
    }

    override fun showSchedule(lessons: List<Lesson>) {
        mAdapter.setLessons(lessons, false)
        b!!.slTeacherLessons.adapter = mAdapter
        b!!.srlRefresh.isRefreshing = false
    }

    override fun showEmptySchedule() {
        mAdapter.setLessons(ArrayList<Lesson>(), false)
        b!!.srlRefresh.isRefreshing = false
    }

    override fun showError() {
        Snackbar.make(b!!.root, "Возниикла ошибка", Snackbar.LENGTH_LONG)
                .setAction("Повторить", { reload() })
                .show()
    }

    override fun reload() {
        mPresenter.reload()
    }

    companion object {
        val TAG = "StudentScheduleFragment"
    }
}
