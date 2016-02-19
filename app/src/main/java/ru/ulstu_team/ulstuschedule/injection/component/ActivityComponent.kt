package ru.ulstu_team.ulstuschedule.injection.component

import dagger.Component
import ru.ulstu_team.ulstuschedule.injection.PerActivity
import ru.ulstu_team.ulstuschedule.injection.module.ActivityModule
import ru.ulstu_team.ulstuschedule.ui.cathedries.CathedriesActivity
import ru.ulstu_team.ulstuschedule.ui.faculties.FacultiesActivity
import ru.ulstu_team.ulstuschedule.ui.groups.GroupsActivity
import ru.ulstu_team.ulstuschedule.ui.main.MainActivity

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(facultiesActivity: FacultiesActivity)
    fun inject(cathedriesActivity: CathedriesActivity)
    fun inject(groupsActivity: GroupsActivity)

}
