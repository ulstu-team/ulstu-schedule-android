package ru.ulstu_team.ulstuschedule.ui.cathedries;

import java.util.List;

import ru.ulstu_team.ulstuschedule.data.model.Cathedra;
import ru.ulstu_team.ulstuschedule.ui.base.MvpView;

public interface CathedriesMvpView extends MvpView {

    void showCathedries(List<Cathedra> cathedries);

    void showEmptyList();

    void showError();

}
