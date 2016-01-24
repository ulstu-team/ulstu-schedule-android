package ru.ulstu_team.ulstuschedule.injection.component;

import ru.ulstu_team.ulstuschedule.ui.main.MainActivity;
import ru.ulstu_team.ulstuschedule.adapters.FacultiesAdapter;

/**
 * This component inject dependencies to all Activities across the application
 */
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(FacultiesAdapter facultiesActivity);

}
