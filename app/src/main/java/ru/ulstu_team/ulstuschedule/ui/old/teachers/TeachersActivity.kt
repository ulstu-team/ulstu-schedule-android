package ru.ulstu_team.ulstuschedule.ui.old.teachers

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_layout.*
import kotlinx.android.synthetic.main.teachers_content.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Teacher
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject

class TeachersActivity : BaseActivity(), TeachersMvpView {

    @Inject lateinit internal var mPresenter: TeachersPresenter
    @Inject lateinit internal var mAdapter: TeachersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_layout)
        contentLayout = R.layout.teachers_content
        toolbarLayout = R.layout.toolbar
        toolbar.title = getString(R.string.teachers)
        setSupportActionBar(toolbar)

        rvTeachers.layoutManager = LinearLayoutManager(this)
        rvTeachers.itemAnimator = DefaultItemAnimator()
        rvTeachers.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
        loadTeachers()

        srlRefresh.setOnRefreshListener { loadTeachers() }
    }

    private fun loadTeachers(){
        val cathedraId = intent.getIntExtra("cathedra_id", 0)
        if (cathedraId <= 0)
            mPresenter.loadTeachers()
        else
            mPresenter.loadCathedraTeachers(cathedraId)
    }

    override fun showTeachers(teachers: List<Teacher>) {
        mAdapter.setTeachers(teachers)
        rvTeachers.adapter = mAdapter
        srlRefresh.isRefreshing = false
    }

    override fun showEmptyList() {
        mAdapter.setTeachers(ArrayList<Teacher>())
        srlRefresh.isRefreshing = false
    }

    override fun showError() {
        Snackbar.make(drawer_layout, R.string.loading_error, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry) { loadTeachers() }
                .show()
        srlRefresh.isRefreshing = false
    }
}
