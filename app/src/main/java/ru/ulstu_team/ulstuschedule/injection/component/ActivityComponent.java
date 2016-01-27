package ru.ulstu_team.ulstuschedule.injection.component;

import dagger.Component;
import ru.ulstu_team.ulstuschedule.injection.PerActivity;
import ru.ulstu_team.ulstuschedule.injection.module.ActivityModule;
import ru.ulstu_team.ulstuschedule.ui.faculties.FacultiesActivity;
import ru.ulstu_team.ulstuschedule.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(FacultiesActivity facultiesActivity);

}
