package ru.ulstu_team.ulstuschedule.ui.cathedries

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_layout.*
import kotlinx.android.synthetic.main.cathedries_content.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Cathedra
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject

class CathedriesActivity : BaseActivity(), CathedriesMvpView {

    @Inject internal lateinit var mPresenter: CathedriesPresenter
    @Inject internal lateinit var mAdapter: CathedriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_layout)
        contentLayout = R.layout.cathedries_content
        toolbarLayout = R.layout.toolbar
        toolbar.title = getString(R.string.cathedries)
        setSupportActionBar(toolbar)

        rvCathedries.layoutManager = LinearLayoutManager(this)
        rvCathedries.itemAnimator = DefaultItemAnimator()
        rvCathedries.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
        loadCathedries()

        srlRefresh.setOnRefreshListener { loadCathedries() }
    }

    private fun loadCathedries() {
        val facultyId = intent.getIntExtra("FacultyId", -1)
        if (facultyId <= 0)
            mPresenter.loadCathedries()
        else
            mPresenter.loadCathedries(facultyId)
    }

    override fun showCathedries(cathedries: List<Cathedra>) {
        mAdapter.setCathedries(cathedries)
        rvCathedries.adapter = mAdapter
        srlRefresh.isRefreshing = false
    }

    override fun showEmptyList() {
        mAdapter.setCathedries(ArrayList<Cathedra>())
        srlRefresh.isRefreshing = false
    }

    override fun showError() {
        Snackbar.make(drawer_layout, "Возниикла ошибка", Snackbar.LENGTH_LONG)
                .setAction("Повторить") { loadCathedries() }
                .show()
        srlRefresh.isRefreshing = false
    }
}
