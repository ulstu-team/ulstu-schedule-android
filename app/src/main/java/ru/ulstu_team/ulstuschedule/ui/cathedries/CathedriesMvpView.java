package ru.ulstu_team.ulstuschedule.ui.cathedries;

import ru.ulstu_team.ulstuschedule.data.model.Cathedra;
import ru.ulstu_team.ulstuschedule.ui.base.MvpView;

public interface CathedriesMvpView extends MvpView {

    void showCathedries(Cathedra[] cathedries);

    void showEmptyList();

    void showError();

}
