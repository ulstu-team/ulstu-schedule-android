package ru.ulstu_team.ulstuschedule.ui.cathedries;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.data.model.Cathedra;
import ru.ulstu_team.ulstuschedule.databinding.ActivityCathedriesBinding;
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity;

public class CathedriesActivity extends BaseActivity implements CathedriesMvpView {

    @Inject CathedriesPresenter mPresenter;
    @Inject CathedriesAdapter mAdapter;
    private ActivityCathedriesBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_cathedries);
        getActivityComponent().inject(this);

        b.rvCathedries.setLayoutManager(new LinearLayoutManager(this));
        b.rvCathedries.setHasFixedSize(true);

        mPresenter.attachView(this);
        mPresenter.loadCathedries();

        b.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadCathedries();
                b.srlRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void showCathedries(Cathedra[] cathedries) {
        mAdapter.setCathedries(cathedries);
        b.rvCathedries.setAdapter(mAdapter);
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
                        mPresenter.loadCathedries();
                    }
                }).show();
    }
}
