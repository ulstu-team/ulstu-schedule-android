package ru.ulstu_team.ulstuschedule.injection.module;

import android.app.Fragment;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.ulstu_team.ulstuschedule.injection.ApplicationContext;
import ru.ulstu_team.ulstuschedule.injection.PerActivity;
import ru.ulstu_team.ulstuschedule.ui.common.StickyListScheduleAdapter;

@Module
public class StudentScheduleModule {

    protected final Fragment mScheduleFragment;

    public StudentScheduleModule(Fragment scheduleFragment) {
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
}
