package ru.ulstu_team.ulstuschedule.injection.component;

import dagger.Component;
import ru.ulstu_team.ulstuschedule.injection.PerActivity;
import ru.ulstu_team.ulstuschedule.injection.module.TeacherScheduleModule;
import ru.ulstu_team.ulstuschedule.ui.common.TeacherScheduleFragment;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = TeacherScheduleModule.class)
public interface TeacherScheduleComponent {

    void inject(TeacherScheduleFragment teacherScheduleFragment);
}
