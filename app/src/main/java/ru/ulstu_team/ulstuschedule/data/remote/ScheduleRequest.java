package ru.ulstu_team.ulstuschedule.data.remote;

import io.realm.RealmObject;
import io.realm.RealmQuery;

public class ScheduleRequest {

    private String mKey;
    private int mId;
    private Class<? extends RealmObject> mClass;
    private RealmQuery mRealmQuery;
    private RequestCallbacks mCallbacks;

    public ScheduleRequest(String key, Class<? extends RealmObject> clazz,
                           RealmQuery query, RequestCallbacks callbacks) {
        if (key == null || clazz == null || callbacks == null || query == null)
            throw new RequestNotCorrectException();

        mKey = key;
        mClass = clazz;
        mRealmQuery = query;
        mCallbacks = callbacks;
    }

    public ScheduleRequest(String key, int id, Class<? extends RealmObject> clazz,
                           RealmQuery query, RequestCallbacks callbacks) {
        this(key, clazz, query, callbacks);
        mId = id;
    }

    public String getKey() {
        return mKey;
    }

    public int getId() {
        return mId;
    }

    public RealmQuery getRealmQuery() {
        return mRealmQuery;
    }

    public Class<? extends RealmObject> getDataType() {
        return mClass;
    }

    public RequestCallbacks getCallbacks() {
        return mCallbacks;
    }
}
