package ru.ulstu_team.ulstuschedule.ui.common.teacher;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmQuery;
import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.data.remote.Schedule;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter;

public class TeacherSchedulePresenter extends BasePresenter<TeacherScheduleMvpView> {

    private DataManager mDataManager;

    @Inject
    public TeacherSchedulePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    public void loadSchedule() {
        checkViewAttached();

        List<Lesson> lessons = getRealmQuery().findAll();
        if (lessons.isEmpty()) {
            //mDataManager.executeRequest(getRequest());
        } else {
            getMvpView().showSchedule(lessons);
        }
    }

    private RealmQuery<Lesson> getRealmQuery() {
        return getMRealm().where(Lesson.class).equalTo("TeacherId", mDataManager.getUserId());
    }

//    private ScheduleRequest getRequest() {
//        return new ScheduleRequest(Schedule.TEACHER_LESSONS,
//                mDataManager.getUserId(), Lesson.class, getRealmQuery(),
//                new ScheduleRequest.Callbacks() {
//                    @Override
//                    public void onSuccess() {
//                        List<Lesson> lessons = getRealmQuery().findAll();
//                        if (!lessons.isEmpty())
//                            getMvpView().showSchedule(lessons);
//                        else
//                            getMvpView().showEmptySchedule();
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        getMvpView().showError();
//                    }
//                }
//        );
//    }

    public void reload() {
        loadSchedule();
    }
}
