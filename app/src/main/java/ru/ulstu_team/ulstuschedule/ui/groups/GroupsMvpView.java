package ru.ulstu_team.ulstuschedule.ui.groups;

import java.util.List;

import ru.ulstu_team.ulstuschedule.data.model.Group;
import ru.ulstu_team.ulstuschedule.ui.base.MvpView;

public interface GroupsMvpView extends MvpView {

    void showGroups(List<Group> groups);

    void showEmptyList();

    void showError();

}
