package ru.ulstu_team.ulstuschedule.ui.faculties

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_layout.*
import kotlinx.android.synthetic.main.faculties_content.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Faculty
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity
import javax.inject.Inject

class FacultiesActivity : BaseActivity(),
        NavigationView.OnNavigationItemSelectedListener, FacultiesMvpView {

    @Inject lateinit var mAdapter: FacultiesAdapter
    @Inject lateinit var mPresenter: FacultiesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_layout)
        contentLayout = R.layout.faculties_content
        toolbarLayout = R.layout.toolbar
        toolbar.title = getString(R.string.faculties)
        setSupportActionBar(toolbar)

        rvFaculties.layoutManager = LinearLayoutManager(this)
        rvFaculties.itemAnimator = DefaultItemAnimator()
        rvFaculties.setHasFixedSize(true)

        mPresenter.attachView(this)
        mPresenter.loadFaculties()

        srlRefresh.setOnRefreshListener { mPresenter.loadFaculties() }
    }

    override fun showFaculties(faculties: List<Faculty>) {
        mAdapter.setFaculties(faculties)
        rvFaculties.adapter = mAdapter
        srlRefresh.isRefreshing = false
    }

    override fun showEmptyList() {
        srlRefresh.isRefreshing = false
    }

    override fun showError() {
        Snackbar.make(drawer_layout, "Возниикла ошибка", Snackbar.LENGTH_LONG)
                .setAction("Повторить") { mPresenter.loadFaculties() }
                .show()
        srlRefresh.isRefreshing = false
    }
}
