package ru.ulstu_team.ulstuschedule;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.ulstu_team.ulstuschedule.injection.component.ApplicationComponent;
import ru.ulstu_team.ulstuschedule.injection.component.DaggerApplicationComponent;
import ru.ulstu_team.ulstuschedule.injection.module.ApplicationModule;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import ru.ulstu_team.ulstuschedule.data.local.PrefsKeys;
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager;

public class App extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .name("schedule_realm.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);

        PrefsManager prefsManager = new PrefsManager(this);
        prefsManager.putString(PrefsKeys.USER_NAME, "Новосельцева Н. Н.");
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }
}
