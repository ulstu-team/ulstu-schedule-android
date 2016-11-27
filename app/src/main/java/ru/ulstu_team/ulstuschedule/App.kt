package ru.ulstu_team.ulstuschedule

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import io.fabric.sdk.android.Fabric

import io.realm.Realm
import io.realm.RealmConfiguration
import ru.ulstu_team.ulstuschedule.injection.component.ApplicationComponent
import ru.ulstu_team.ulstuschedule.injection.component.DaggerApplicationComponent
import ru.ulstu_team.ulstuschedule.injection.module.ApplicationModule
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import ru.ulstu_team.ulstuschedule.data.local.PrefsKeys
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager

class App : Application() {

    internal var mApplicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics(), Answers())

        CalligraphyConfig.initDefault(
                CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build())

        val config = RealmConfiguration.Builder(applicationContext)
                .name("schedule_realm.realm")
                .schemaVersion(1)
                .build()
        Realm.setDefaultConfiguration(config)

        val prefsManager = PrefsManager(this)
        prefsManager.putInt(PrefsKeys.USER_ID, 109)
        prefsManager.putString(PrefsKeys.USER_NAME, "ПИбд-31")
        prefsManager.putString(PrefsKeys.USER_TYPE, "student")
    }

    val component: ApplicationComponent
        get() {
            if (mApplicationComponent == null) {
                mApplicationComponent = DaggerApplicationComponent.builder()
                        .applicationModule(ApplicationModule(this))
                        .build()
            }
            return mApplicationComponent!!
        }

    companion object {

        operator fun get(context: Context): App {
            return context.applicationContext as App
        }
    }
}
