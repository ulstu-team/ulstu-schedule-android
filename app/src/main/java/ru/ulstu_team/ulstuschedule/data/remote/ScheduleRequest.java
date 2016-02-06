package ru.ulstu_team.ulstuschedule.data.remote;

import com.android.volley.RequestQueue;

import io.realm.Realm;
import io.realm.RealmQuery;

public class ScheduleRequest {

    private String mKey;
    private int mId;
    private Class mClass;
    private RealmQuery mRealmQuery;
    private Callbacks mCallbacks;

    public ScheduleRequest(String key, Class clazz, RealmQuery query, Callbacks callbacks) {
        mKey = key;
        mClass = clazz;
        mRealmQuery = query;
        mCallbacks = callbacks;
    }

    public ScheduleRequest(String key, int id, Class clazz, RealmQuery query, Callbacks callbacks) {
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

    public Class getDataType() {
        return mClass;
    }

    public Callbacks getCallbacks() {
        return mCallbacks;
    }

    public interface Callbacks {
        void onSuccess();
        void onError(Exception e);
    }
}
