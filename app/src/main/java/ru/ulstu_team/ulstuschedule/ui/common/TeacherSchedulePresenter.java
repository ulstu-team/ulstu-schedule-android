package ru.ulstu_team.ulstuschedule.ui.common;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.realm.RealmQuery;
import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter;
import ru.ulstu_team.ulstuschedule.data.remote.Schedule;
import ru.ulstu_team.ulstuschedule.util.GsonUtil;

public class TeacherSchedulePresenter extends BasePresenter<TeacherScheduleMvpView> {

    private DataManager mDataManager;

    @Inject
    public TeacherSchedulePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    public void loadSchedule() {
        checkViewAttached();

        mDataManager.requestScheduleData(getRequest());
        //List<Lesson> lessons = getRealmQuery().findAll();

        //if (lessons.size() == 0)
//        else
//            getMvpView().showSchedule(lessons);
    }

    private RealmQuery<Lesson> getRealmQuery() {
        return mRealm.where(Lesson.class).equalTo("TeacherId", mDataManager.getUserId());
    }

    private ScheduleRequest getRequest() {
        return new ScheduleRequest(Schedule.TEACHER_LESSONS,
                mDataManager.getUserId(), Lesson.class,
                new ScheduleRequest.Callbacks() {
                    @Override
                    public void onSuccess(String json) {
                        Lesson[] lessons = GsonUtil.getGsonInstance().fromJson(json, Lesson[].class);

                        if (lessons.length > 0)
                            getMvpView().showSchedule(Arrays.asList(lessons));
                        else
                            getMvpView().showEmptySchedule();
                    }

                    @Override
                    public void onError(Exception e) {
                        getMvpView().showError();
                    }
                }
        );
    }

    public void reload() {
        loadSchedule();
    }
}
