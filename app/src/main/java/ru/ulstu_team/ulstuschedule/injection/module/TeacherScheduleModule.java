package ru.ulstu_team.ulstuschedule.injection.module;

import android.app.Fragment;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.ulstu_team.ulstuschedule.injection.ApplicationContext;
import ru.ulstu_team.ulstuschedule.injection.PerActivity;
import ru.ulstu_team.ulstuschedule.ui.common.StickyListTeacherAdapter;

@Module
public class TeacherScheduleModule {

    protected final Fragment mTeacherScheduleFragment;

    public TeacherScheduleModule(Fragment teacherScheduleFragment) {
        mTeacherScheduleFragment = teacherScheduleFragment;
    }

    @Provides
    Fragment provideFragment() {
        return mTeacherScheduleFragment;
    }

    @Provides
    Context provideContext() {
        return mTeacherScheduleFragment.getActivity();
    }

    @PerActivity
    @Provides
    StickyListTeacherAdapter provideStickyListTeacherAdapter(@ApplicationContext Context context) {
        return new StickyListTeacherAdapter(context);
    }
}
