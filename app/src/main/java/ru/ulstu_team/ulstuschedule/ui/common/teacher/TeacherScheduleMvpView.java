package ru.ulstu_team.ulstuschedule.ui.common.teacher;

import java.util.List;

import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.ui.base.MvpView;

public interface TeacherScheduleMvpView extends MvpView {

    void showSchedule(Lesson[] lessons);

    void showEmptySchedule();

    void showError();

    void reload();

}
