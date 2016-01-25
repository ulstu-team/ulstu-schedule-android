package ru.ulstu_team.ulstuschedule.injection.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.ulstu_team.ulstuschedule.HeaderViewManager;
import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager;
import ru.ulstu_team.ulstuschedule.injection.PerActivity;

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
}
