package ru.ulstu_team.ulstuschedule.ui.faculties;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.data.model.Faculty;
import ru.ulstu_team.ulstuschedule.databinding.ActivityFacultiesBinding;
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity;

public class FacultiesActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, FacultiesMvpView {

    @Inject FacultiesAdapter mAdapter;
    @Inject FacultiesPresenter mPresenter;
    private ActivityFacultiesBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_faculties);
        getActivityComponent().inject(this);

        b.rvFaculties.setLayoutManager(new LinearLayoutManager(this));
        b.rvFaculties.setItemAnimator(new DefaultItemAnimator());
        b.rvFaculties.setHasFixedSize(true);

        mPresenter.attachView(this);
        mPresenter.loadFaculties();

        b.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadFaculties();
                b.srlRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void showFaculties(List<Faculty> faculties) {
        mAdapter.setFaculties(faculties);
        b.rvFaculties.setAdapter(mAdapter);
    }

    @Override
    public void showEmptyList() {

    }

    @Override
    public void showError() {
        Snackbar.make(b.drawerLayout, "Возниикла ошибка", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.loadFaculties();
                    }
                }).show();
    }
}
