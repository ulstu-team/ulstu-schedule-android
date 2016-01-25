package ru.ulstu_team.ulstuschedule.ui.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.databinding.ScheduleBinding;
import ru.ulstu_team.ulstuschedule.ui.base.BaseFragment;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class TeacherScheduleFragment extends BaseFragment implements TeacherScheduleMvpView {

    public static final String TAG = "TeacherScheduleFragment";

    @Inject StickyListTeacherAdapter mAdapter;
    @Inject TeacherSchedulePresenter mPresenter;
    private View mView;
    private ScheduleBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getTeacherScheduleComponent().inject(this);
        mView = inflater.inflate(R.layout.schedule, container, false);

        binding = ScheduleBinding.bind(mView);
        mPresenter.attachView(this);
        mPresenter.loadSchedule();

        return mView;
    }

    @Override
    public void showSchedule(List<Lesson> lessons) {
        mAdapter.setLessons(lessons);
        mAdapter.notifyDataSetChanged();

        binding.slTeacherLessons.setAdapter(mAdapter);
    }

    @Override
    public void showEmptySchedule() {
        mAdapter.setLessons(Collections.<Lesson>emptyList());
        mAdapter.notifyDataSetChanged();
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
    }
}
