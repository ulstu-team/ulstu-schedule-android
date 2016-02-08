package ru.ulstu_team.ulstuschedule.ui.groups;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.data.model.Group;
import ru.ulstu_team.ulstuschedule.databinding.ActivityGroupsBinding;
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity;

public class GroupsActivity extends BaseActivity implements GroupsMvpView {

    @Inject GroupsPresenter mPresenter;
    @Inject GroupsAdapter mAdapter;
    private ActivityGroupsBinding b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        b = DataBindingUtil.setContentView(this, R.layout.activity_groups);

        b.rvGroups.setLayoutManager(new LinearLayoutManager(this));
        b.rvGroups.setItemAnimator(new DefaultItemAnimator());
        b.rvGroups.setHasFixedSize(true);

        mPresenter.attachView(this);
        mPresenter.loadGroups();

        b.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.forceLoad();
            }
        });
    }

    @Override
    public void showGroups(List<Group> groups) {
        mAdapter.setGroups(groups);
        b.rvGroups.setAdapter(mAdapter);
        b.srlRefresh.setRefreshing(false);
    }

    @Override
    public void showEmptyList() {
        mAdapter.setGroups(new ArrayList<Group>());
        b.srlRefresh.setRefreshing(false);
    }

    @Override
    public void showError() {
        b.srlRefresh.setRefreshing(false);
    }
}
