package ru.ulstu_team.ulstuschedule.injection.module;

import android.app.Fragment;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.injection.ApplicationContext;
import ru.ulstu_team.ulstuschedule.injection.PerActivity;
import ru.ulstu_team.ulstuschedule.ui.common.StickyListScheduleAdapter;
import ru.ulstu_team.ulstuschedule.ui.common.group.StudentSchedulePresenter;

@Module
public class TeacherScheduleModule {

    protected final Fragment mScheduleFragment;

    public TeacherScheduleModule(Fragment scheduleFragment) {
        mScheduleFragment = scheduleFragment;
    }

    @Provides
    Fragment provideFragment() {
        return mScheduleFragment;
    }

    @Provides
    Context provideContext() {
        return mScheduleFragment.getActivity();
    }

    @PerActivity
    @Provides
    StickyListScheduleAdapter provideStickyListScheduleAdapter(@ApplicationContext Context context) {
        return new StickyListScheduleAdapter(context);
    }

    @PerActivity
    @Provides
    StudentSchedulePresenter provideStudentSchedulePresenter(DataManager dataManager){
        return new StudentSchedulePresenter(dataManager);
    }
}