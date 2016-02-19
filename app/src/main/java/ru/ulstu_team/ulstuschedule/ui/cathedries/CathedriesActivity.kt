package ru.ulstu_team.ulstuschedule.ui.cathedries

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Cathedra
import ru.ulstu_team.ulstuschedule.databinding.ActivityCathedriesBinding
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity
import javax.inject.Inject

class CathedriesActivity : BaseActivity(), CathedriesMvpView {

    @Inject internal lateinit var mPresenter: CathedriesPresenter
    @Inject internal lateinit var mAdapter: CathedriesAdapter
    private lateinit var b: ActivityCathedriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView<ActivityCathedriesBinding>(this, R.layout.cathedries_content)
        activityComponent.inject(this)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        b.rvCathedries.layoutManager = LinearLayoutManager(this)
        b.rvCathedries.itemAnimator = DefaultItemAnimator()
        b.rvCathedries.setHasFixedSize(true)

        mPresenter.attachView(this)
        mPresenter.loadCathedries(facultyId)

        b.srlRefresh.setOnRefreshListener {
            mPresenter.loadCathedries()
        }
    }

    val facultyId: Int
        get() = intent.getIntExtra("FacultyId", 0)

    override fun showCathedries(cathedries: List<Cathedra>) {
        mAdapter.setCathedries(cathedries)
        b.rvCathedries.adapter = mAdapter
        b.srlRefresh.isRefreshing = false
    }

    override fun showEmptyList() {
        b.srlRefresh.isRefreshing = false
    }

    override fun showError() {
        Snackbar.make(b.drawerLayout, "Возниикла ошибка", Snackbar.LENGTH_LONG)
                .setAction("Повторить") {
                    mPresenter.loadCathedries(facultyId)
                }.show()
        b.srlRefresh.isRefreshing = false
    }
}
