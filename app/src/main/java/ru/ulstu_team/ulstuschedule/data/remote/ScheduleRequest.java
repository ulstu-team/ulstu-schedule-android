package ru.ulstu_team.ulstuschedule.data.remote;

public class ScheduleRequest {

    private String mKey;
    private int mId;
    private Class mClass;
    private Callbacks mCallbacks;

    public ScheduleRequest(String key, Class clazz, Callbacks callbacks) {
        mKey = key;
        mClass = clazz;
        mCallbacks = callbacks;
    }

    public ScheduleRequest(String key, int id, Class clazz, Callbacks callbacks) {
        mKey = key;
        mId = id;
        mClass = clazz;
        mCallbacks = callbacks;
    }

    public String getKey() {
        return mKey;
    }

    public int getId() {
        return mId;
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
