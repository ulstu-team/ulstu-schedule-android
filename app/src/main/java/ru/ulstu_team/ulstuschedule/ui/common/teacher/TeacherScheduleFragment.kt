package ru.ulstu_team.ulstuschedule.ui.common.teacher

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

class TeacherScheduleFragment() : BaseFragment(), TeacherScheduleMvpView {

    @Inject
    internal lateinit var mAdapter: StickyListScheduleAdapter
    @Inject
    internal lateinit var mPresenter: TeacherSchedulePresenter
    private var mTeacherId = -1

    constructor(teacherId: Int) : this() {
        mTeacherId = teacherId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        getTeacherScheduleComponent().inject(this)
        return inflater.inflate(R.layout.schedule, container, false)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
        loadSchedule()

        srlRefresh.setOnRefreshListener { loadSchedule() }
    }

    private fun loadSchedule() {
        if (mTeacherId <= 0)
            mPresenter.loadScheduleForCurrentTeacher()
        else
            mPresenter.loadScheduleForTeacher(mTeacherId)
    }

    override fun showSchedule(lessons: List<Lesson>) {
        mAdapter.setLessons(lessons, true)
        slTeacherLessons.adapter = mAdapter
    }

    override fun showEmptySchedule() {
        mAdapter.setLessons(ArrayList<Lesson>(), true)
    }

    override fun showError() {
        Snackbar.make(view, "Возниикла ошибка", Snackbar.LENGTH_LONG)
                .setAction("Повторить", { loadSchedule() })
                .show()
    }

    companion object {
        val TAG = "TeacherScheduleFragment"
    }
}
