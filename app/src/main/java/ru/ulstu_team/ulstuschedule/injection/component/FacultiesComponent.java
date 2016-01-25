package ru.ulstu_team.ulstuschedule.injection.component;

import dagger.Component;
import ru.ulstu_team.ulstuschedule.adapters.FacultiesAdapter;
import ru.ulstu_team.ulstuschedule.injection.PerActivity;
import ru.ulstu_team.ulstuschedule.injection.module.ActivityModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface FacultiesComponent {

    void inject(FacultiesAdapter facultiesActivity);

}
