package ru.ulstu_team.ulstuschedule.ui.faculties;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.data.model.Faculty;
import ru.ulstu_team.ulstuschedule.databinding.ActivityFacultiesBinding;
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity;

public class FacultiesActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, FacultiesMvpView {

    @Inject FacultiesAdapter mAdapter;
    @Inject FacultiesPresenter mPresenter;
    private ActivityFacultiesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faculties);
        getActivityComponent().inject(this);
        configureNavigationView();

        binding.rvFaculties.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.attachView(this);
        mPresenter.loadFaculties();

        binding.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadFaculties();
                binding.srlRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void showFaculties(Faculty[] faculties) {
        mAdapter.setFaculties(faculties);
        mAdapter.notifyDataSetChanged();
        binding.rvFaculties.setAdapter(mAdapter);
    }

    @Override
    public void showEmptyList() {

    }

    @Override
    public void showError() {

    }
}
