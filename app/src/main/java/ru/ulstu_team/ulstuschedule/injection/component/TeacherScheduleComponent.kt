package ru.ulstu_team.ulstuschedule.injection.component

import dagger.Component
import ru.ulstu_team.ulstuschedule.injection.PerActivity
import ru.ulstu_team.ulstuschedule.injection.module.TeacherScheduleModule
import ru.ulstu_team.ulstuschedule.ui.common.teacher.TeacherScheduleFragment

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(TeacherScheduleModule::class))
interface TeacherScheduleComponent {

    fun inject(scheduleFragment: TeacherScheduleFragment)
}
