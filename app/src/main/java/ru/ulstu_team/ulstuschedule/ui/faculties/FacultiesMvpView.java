package ru.ulstu_team.ulstuschedule.ui.faculties;

import ru.ulstu_team.ulstuschedule.data.model.Faculty;
import ru.ulstu_team.ulstuschedule.ui.base.MvpView;

public interface FacultiesMvpView extends MvpView {

    void showFaculties(Faculty[] faculties);

    void showEmptyList();

    void showError();

}
