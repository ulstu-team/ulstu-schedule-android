package ru.ulstu_team.ulstuschedule.ui.common.student;

import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.ui.base.MvpView;

public interface StudentScheduleMvpView extends MvpView{

    void showSchedule(Lesson[] lessons);

    void showEmptySchedule();

    void showError();

    void reload();

}
