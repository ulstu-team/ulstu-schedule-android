package ru.ulstu_team.ulstuschedule.ui.common.student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.databinding.ScheduleBinding;
import ru.ulstu_team.ulstuschedule.ui.base.BaseFragment;
import ru.ulstu_team.ulstuschedule.ui.common.StickyListScheduleAdapter;

public class StudentScheduleFragment extends BaseFragment implements StudentScheduleMvpView {
    public static final String TAG = "StudentScheduleFragment";

    @Inject
    StickyListScheduleAdapter mAdapter;
    @Inject
    StudentSchedulePresenter mPresenter;
    private View mView;
    private ScheduleBinding b;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getStudentScheduleComponent().inject(this);
        mView = inflater.inflate(R.layout.schedule, container, false);

        b = ScheduleBinding.bind(mView);
        mPresenter.attachView(this);
        mPresenter.loadSchedule();

        b.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadSchedule();
                b.srlRefresh.setRefreshing(false);
            }
        });

        return mView;
    }

    @Override
    public void showSchedule(Lesson[] lessons) {
        mAdapter.setLessons(lessons, false);

        b.slTeacherLessons.setAdapter(mAdapter);
    }

    @Override
    public void showEmptySchedule() {
        mAdapter.setLessons(new Lesson[0], false);
    }

    @Override
    public void showError() {
        Snackbar.make(mView, "Возниикла ошибка", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reload();
                    }
                }).show();
    }

    @Override
    public void reload() {
        mPresenter.reload();
    }}