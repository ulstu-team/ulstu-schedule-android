package ru.ulstu_team.ulstuschedule.injection.component

import dagger.Component
import ru.ulstu_team.ulstuschedule.injection.PerActivity
import ru.ulstu_team.ulstuschedule.injection.module.ActivityModule
import ru.ulstu_team.ulstuschedule.ui.old.cathedries.CathedriesActivity
import ru.ulstu_team.ulstuschedule.ui.old.faculties.FacultiesActivity
import ru.ulstu_team.ulstuschedule.ui.old.groups.GroupsActivity
import ru.ulstu_team.ulstuschedule.ui.old.main2.MainActivity2
import ru.ulstu_team.ulstuschedule.ui.old.ScheduleActivity
import ru.ulstu_team.ulstuschedule.ui.old.teachers.TeachersActivity

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity2: MainActivity2)
    fun inject(facultiesActivity: FacultiesActivity)
    fun inject(cathedriesActivity: CathedriesActivity)
    fun inject(groupsActivity: GroupsActivity)
    fun inject(scheduleActivity: ScheduleActivity)
    fun inject(teachersActivity: TeachersActivity)

}
