package ru.ulstu_team.ulstuschedule.ui.common.student;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmQuery;
import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.data.remote.Schedule;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter;

public class StudentSchedulePresenter extends BasePresenter<StudentScheduleMvpView>  {
    private DataManager mDataManager;

    @Inject
    public StudentSchedulePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void loadSchedule() {
        checkViewAttached();

        List<Lesson> lessons = getRealmQuery().findAll();

        if (lessons.size() == 0) {
            mDataManager.executeRequest(getRequest());
        }
        else {
            getMvpView().showSchedule(lessons.toArray(new Lesson[lessons.size()]));
        }
    }

    private RealmQuery<Lesson> getRealmQuery() {
        return mRealm.where(Lesson.class).equalTo("GroupId", mDataManager.getUserId());
    }

    private ScheduleRequest getRequest() {
        return new ScheduleRequest(Schedule.GROUP_LESSONS,
                mDataManager.getUserId(), Lesson.class, getRealmQuery(),
                new ScheduleRequest.Callbacks() {
                    @Override
                    public void onSuccess() {
                        List<Lesson> lessons = getRealmQuery().findAll();
                        if (!lessons.isEmpty())
                            getMvpView().showSchedule(lessons.toArray(new Lesson[lessons.size()]));
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
