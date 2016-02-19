package ru.ulstu_team.ulstuschedule.ui.faculties

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Faculty
import ru.ulstu_team.ulstuschedule.databinding.ActivityFacultiesBinding
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity
import javax.inject.Inject

class FacultiesActivity : BaseActivity(),
        NavigationView.OnNavigationItemSelectedListener, FacultiesMvpView {

    @Inject lateinit var mAdapter: FacultiesAdapter
    @Inject lateinit var mPresenter: FacultiesPresenter
    private lateinit var b: ActivityFacultiesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView<ActivityFacultiesBinding>(this, R.layout.faculties_content)
        activityComponent.inject(this)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = getString(R.string.faculties)
        setSupportActionBar(toolbar)

        b.rvFaculties.layoutManager = LinearLayoutManager(this)
        b.rvFaculties.itemAnimator = DefaultItemAnimator()
        b.rvFaculties.setHasFixedSize(true)

        mPresenter.attachView(this)
        mPresenter.loadFaculties()

        b.srlRefresh.setOnRefreshListener {
            mPresenter.loadFaculties()
        }
    }

    override fun showFaculties(faculties: List<Faculty>) {
        mAdapter.setFaculties(faculties)
        b.rvFaculties.adapter = mAdapter
        b.srlRefresh.isRefreshing = false
    }

    override fun showEmptyList() {
        b.srlRefresh.isRefreshing = false
    }

    override fun showError() {
        Snackbar.make(b.drawerLayout, "Возниикла ошибка", Snackbar.LENGTH_LONG)
                .setAction("Повторить") { mPresenter.loadFaculties() }
                .show()
        b.srlRefresh.isRefreshing = false
    }
}
