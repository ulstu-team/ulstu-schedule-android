package ru.ulstu_team.ulstuschedule.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ru.ulstu_team.ulstuschedule.data.remote.JsonDownloadService;
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager;
import ru.ulstu_team.ulstuschedule.data.remote.VolleySingleton;
import ru.ulstu_team.ulstuschedule.injection.ApplicationContext;
import ru.ulstu_team.ulstuschedule.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(JsonDownloadService jsonDownloadService);

    @ApplicationContext
    Context context();
    Application application();
    JsonDownloadService jsonDownloadService();
    PrefsManager prefsManager();
    VolleySingleton volleySingleton();
}
