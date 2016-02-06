package ru.ulstu_team.ulstuschedule.injection.component;

import dagger.Component;
import ru.ulstu_team.ulstuschedule.injection.PerActivity;
import ru.ulstu_team.ulstuschedule.injection.module.StudentScheduleModule;
import ru.ulstu_team.ulstuschedule.ui.common.student.StudentScheduleFragment;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = StudentScheduleModule.class)
public interface StudentScheduleComponent {

    void inject(StudentScheduleFragment scheduleFragment);

}
