package ru.ulstu_team.ulstuschedule.injection.component

import dagger.Component
import ru.ulstu_team.ulstuschedule.injection.PerActivity
import ru.ulstu_team.ulstuschedule.injection.module.StudentScheduleModule
import ru.ulstu_team.ulstuschedule.ui.common.group.GroupScheduleFragment

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(StudentScheduleModule::class))
interface StudentScheduleComponent {

    fun inject(scheduleFragment: GroupScheduleFragment)

}
