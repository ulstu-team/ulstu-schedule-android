package ru.ulstu_team.ulstuschedule.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.ulstu_team.ulstuschedule.data.JsonDownloadService;
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager;
import ru.ulstu_team.ulstuschedule.injection.ApplicationContext;


/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    JsonDownloadService provideJsonDownloaderService() {
        return new JsonDownloadService();
    }

    @Provides
    @Singleton
    PrefsManager providePrefsManager(@ApplicationContext Context context) { return new PrefsManager(context); }
}
