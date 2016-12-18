package ru.ulstu_team.ulstuschedule.ui.old.groups

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_layout.*
import kotlinx.android.synthetic.main.groups_content.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.model.Group
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject

class GroupsActivity : BaseActivity(), GroupsMvpView {

    @Inject lateinit internal var mPresenter: GroupsPresenter
    @Inject lateinit internal var mAdapter: GroupsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_layout)
        contentLayout = R.layout.groups_content
        toolbarLayout = R.layout.toolbar
        toolbar.title = getString(R.string.groups)
        setSupportActionBar(toolbar)

        rvGroups.layoutManager = LinearLayoutManager(this)
        rvGroups.itemAnimator = DefaultItemAnimator()
        rvGroups.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
        mPresenter.loadGroups()

        srlRefresh.setOnRefreshListener { mPresenter.loadGroups() }
    }

    override fun showGroups(groups: List<Group>) {
        mAdapter.setGroups(groups)
        rvGroups.adapter = mAdapter
        srlRefresh.isRefreshing = false
    }

    override fun showEmptyList() {
        mAdapter.setGroups(ArrayList<Group>())
        srlRefresh.isRefreshing = false
    }

    override fun showError() {
        Snackbar.make(drawer_layout, R.string.loading_error, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry) { mPresenter.loadGroups() }
                .show()
        srlRefresh.isRefreshing = false
    }
}
