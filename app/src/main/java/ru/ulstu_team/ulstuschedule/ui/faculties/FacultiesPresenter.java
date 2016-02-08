package ru.ulstu_team.ulstuschedule.ui.faculties;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmQuery;
import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.data.model.Faculty;
import ru.ulstu_team.ulstuschedule.data.remote.Schedule;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter;

public class FacultiesPresenter extends BasePresenter<FacultiesMvpView> {

    private DataManager mDataManager;

    @Inject
    public FacultiesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void loadFaculties() {
        checkViewAttached();

        List<Faculty> faculties = getRealmQuery().findAll();
        if (!faculties.isEmpty()) {
            getMvpView().showFaculties(faculties);
        } else {
            mDataManager.executeRequest(getRequest());
        }
    }

    private RealmQuery<Faculty> getRealmQuery() {
        return mRealm.where(Faculty.class);
    }

    public ScheduleRequest getRequest() {
        return new ScheduleRequest(Schedule.FACULTIES, Faculty.class, getRealmQuery(),
                new ScheduleRequest.Callbacks() {
                    @Override
                    public void onSuccess() {
                        List<Faculty> faculties = getRealmQuery().findAll();
                        getMvpView().showFaculties(faculties);
                    }

                    @Override
                    public void onError(Exception e) {
                        getMvpView().showError();
                    }
                });
    }
}
