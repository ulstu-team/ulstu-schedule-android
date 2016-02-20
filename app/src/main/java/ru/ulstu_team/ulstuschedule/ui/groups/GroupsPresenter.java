package ru.ulstu_team.ulstuschedule.ui.groups;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmQuery;
import ru.ulstu_team.ulstuschedule.data.DataManager;
import ru.ulstu_team.ulstuschedule.data.model.Group;
import ru.ulstu_team.ulstuschedule.data.remote.Schedule;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;
import ru.ulstu_team.ulstuschedule.ui.base.BasePresenter;

public class GroupsPresenter extends BasePresenter<GroupsMvpView> {

    private DataManager mDataManager;

    @Inject
    public GroupsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void loadGroups() {
        checkViewAttached();

        List<Group> groups = getRealmQuery().findAll();
        if (groups.size() > 50) {
            getMvpView().showGroups(groups);
        } else {
            //mDataManager.executeRequest(getRequest());
        }
    }

    public void forceLoad() {
        checkViewAttached();
        //mDataManager.executeRequest(getRequest());
    }

    private RealmQuery<Group> getRealmQuery() {
        return getMRealm().where(Group.class);
    }

//    private ScheduleRequest getRequest() {
//        return new ScheduleRequest(Schedule.GROUPS, Group.class, getRealmQuery(),
//                new ScheduleRequest.Callbacks() {
//                    @Override
//                    public void onSuccess() {
//                        List<Group> groups = getRealmQuery().findAll();
//                        if (groups.size() > 50)
//                            getMvpView().showGroups(groups);
//                        else
//                            getMvpView().showEmptyList();
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        getMvpView().showError();
//                    }
//                });
//    }
}
