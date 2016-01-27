package ru.ulstu_team.ulstuschedule.ui.faculties;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.data.model.Faculty;
import ru.ulstu_team.ulstuschedule.data.remote.Schedule;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter;
import ru.ulstu_team.ulstuschedule.util.GsonUtil;

public class FacultiesPresenter extends BasePresenter<FacultiesMvpView> {

    private DataManager mDataManager;

    @Inject
    public FacultiesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void loadFaculties() {
        checkViewAttached();
        mDataManager.requestScheduleData(getRequest());
    }

    public ScheduleRequest getRequest() {
        return new ScheduleRequest(Schedule.FACULTIES, Faculty.class,
                new ScheduleRequest.Callbacks() {
                    @Override
                    public void onSuccess(String json) {
                        Faculty[] faculties = GsonUtil.getGsonInstance().fromJson(json, Faculty[].class);
                        getMvpView().showFaculties(faculties);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }
}
