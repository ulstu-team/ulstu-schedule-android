package ru.ulstu_team.ulstuschedule.ui.common;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmQuery;
import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter;
import ulstu.schedule.api.Schedule;

public class TeacherSchedulePresenter extends BasePresenter<TeacherScheduleMvpView> {

    private DataManager mDataManager;

    private RealmQuery<Lesson> mQuery =
            mRealm.where(Lesson.class)
                    .equalTo("TeacherId", mDataManager.getUserId());

    private ScheduleRequest mRequest =
            new ScheduleRequest(Schedule.TEACHER_LESSONS,
                    mDataManager.getUserId(), Lesson.class,
                    new ScheduleRequest.Callbacks() {
                        @Override
                        public void onSuccess() {
                            List<Lesson> lessons = mQuery.findAll();
                            if (lessons.size() > 0)
                                getMvpView().showSchedule(lessons);
                            else
                                getMvpView().showEmptySchedule();
                        }

                        @Override
                        public void onError(Exception e) {
                            getMvpView().showError();
                        }
                    }
            );


    @Inject
    public TeacherSchedulePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void loadSchedule() {
        List<Lesson> lessons = mQuery.findAll();

        if (lessons.size() == 0)
            mDataManager.requestScheduleData(mRequest);
        else
            getMvpView().showSchedule(lessons);
    }
}
