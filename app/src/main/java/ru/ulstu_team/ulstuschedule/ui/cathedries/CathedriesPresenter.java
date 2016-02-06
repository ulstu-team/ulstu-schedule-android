package ru.ulstu_team.ulstuschedule.ui.cathedries;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmQuery;
import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.data.model.Cathedra;
import ru.ulstu_team.ulstuschedule.data.remote.Schedule;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter;
import ru.ulstu_team.ulstuschedule.util.GsonUtil;

public class CathedriesPresenter extends BasePresenter<CathedriesMvpView> {

    private DataManager mDataManager;

    @Inject
    public CathedriesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void loadCathedries() {
        checkViewAttached();

        mDataManager.executeRequest(getRequest());
    }

    private RealmQuery<Cathedra> getRealmQuery() {
        return mRealm.where(Cathedra.class);
    }

    private ScheduleRequest getRequest() {
        return new ScheduleRequest(Schedule.CATHEDRIES, Cathedra.class, getRealmQuery(),
                new ScheduleRequest.Callbacks() {
                    @Override
                    public void onSuccess() {
                        List<Cathedra> cathedries = getRealmQuery().findAll();
                        getMvpView().showCathedries(cathedries.toArray(new Cathedra[cathedries.size()]));
                    }

                    @Override
                    public void onError(Exception e) {
                        getMvpView().showError();
                    }
                });
    }
}
