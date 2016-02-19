package ru.ulstu_team.ulstuschedule.injection.component

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Component
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager
import ru.ulstu_team.ulstuschedule.data.remote.VolleySingleton
import ru.ulstu_team.ulstuschedule.injection.ApplicationContext
import ru.ulstu_team.ulstuschedule.injection.module.ApplicationModule

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application
    fun prefsManager(): PrefsManager
    fun volleySingleton(): VolleySingleton
}
