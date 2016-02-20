package ru.ulstu_team.ulstuschedule.ui.common.group

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.schedule.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Lesson
import ru.ulstu_team.ulstuschedule.ui.base.BaseFragment
import ru.ulstu_team.ulstuschedule.ui.common.StickyListScheduleAdapter
import java.util.*
import javax.inject.Inject

class GroupScheduleFragment() : BaseFragment(), GroupScheduleMvpView {

    @Inject
    internal lateinit var mAdapter: StickyListScheduleAdapter
    @Inject
    internal lateinit var mPresenter: StudentSchedulePresenter
    private var mGroupId = -1

    constructor(groupId: Int):this() {
        mGroupId = groupId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        getStudentScheduleComponent().inject(this)
        return inflater.inflate(R.layout.schedule, container, false)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
        loadSchedule()

        srlRefresh.setOnRefreshListener { loadSchedule() }
    }

    private fun loadSchedule() {
        if (mGroupId <= 0)
            mPresenter.loadScheduleForCurrentGroup()
        else
            mPresenter.loadGroupSchedule(mGroupId)
    }

    override fun showSchedule(lessons: List<Lesson>) {
        mAdapter.setLessons(lessons, false)
        slTeacherLessons.adapter = mAdapter
        srlRefresh.isRefreshing = false
    }

    override fun showEmptySchedule() {
        mAdapter.setLessons(ArrayList<Lesson>(), false)
        srlRefresh.isRefreshing = false
    }

    override fun showError() {
        Snackbar.make(view, "Возниикла ошибка", Snackbar.LENGTH_LONG)
                .setAction("Повторить", { loadSchedule() })
                .show()
    }

    companion object {
        val TAG = "StudentScheduleFragment"
    }
}
