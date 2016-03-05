package ru.ulstu_team.ulstuschedule.injection.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.ulstu_team.ulstuschedule.HeaderViewManager;
import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.injection.PerActivity;
import ru.ulstu_team.ulstuschedule.ui.cathedries.CathedriesAdapter;
import ru.ulstu_team.ulstuschedule.ui.faculties.FacultiesAdapter;
import ru.ulstu_team.ulstuschedule.ui.groups.GroupsAdapter;
import ru.ulstu_team.ulstuschedule.ui.teachers.TeachersAdapter;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    Context provideContext() {
        return mActivity;
    }

    @PerActivity
    @Provides
    HeaderViewManager provideHeaderViewManager(Context context, DataManager dataManager) {
        return new HeaderViewManager(context, dataManager);
    }

    @PerActivity
    @Provides
    FacultiesAdapter provideFacultiesAdapter() {
        return new FacultiesAdapter();
    }

    @PerActivity
    @Provides
    CathedriesAdapter provideCathedriesAdapter() {
        return new CathedriesAdapter();
    }

    @PerActivity
    @Provides
    GroupsAdapter provideGroupsAdapter() {
        return new GroupsAdapter();
    }

    @PerActivity
    @Provides
    TeachersAdapter provideTeacherAdapter() {
        return new TeachersAdapter();
    }
}