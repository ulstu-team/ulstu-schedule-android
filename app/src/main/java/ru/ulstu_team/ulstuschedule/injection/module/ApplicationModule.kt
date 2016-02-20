package ru.ulstu_team.ulstuschedule.injection.module

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager
import ru.ulstu_team.ulstuschedule.data.remote.VolleySingleton
import ru.ulstu_team.ulstuschedule.injection.ApplicationContext


/**
 * Provide application-level dependencies.
 */
@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return mApplication
    }

    @Provides
    @Singleton
    fun providePrefsManager(@ApplicationContext context: Context): PrefsManager {
        return PrefsManager(context)
    }

    @Provides
    @Singleton
    fun provideVolleySingleton(@ApplicationContext context: Context): VolleySingleton {
        return VolleySingleton.getInstance(context)
    }
}
