package ulstu.schedule.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ulstu.schedule.models.Cathedra;
import ulstu.schedule.models.Faculty;
import ulstu.schedule.models.Group;
import ulstu.schedule.models.Lesson;
import ulstu.schedule.models.Teacher;
import ulstu.schedule.models.TeacherLessons;

public class UlstuScheduleAPI {

    public static final String BROADCAST_ACTION = "ru.ulstu_team.ulstuschedule.api.request";

    private static final String URL_BASE_PART = "http://ulstuschedule.azurewebsites.net/ulstu/";

    private UlstuScheduleAPI() { }

    private Context mContext;
    private String mUrl;
    private ScheduleReceiver mReceiver;
    private String mKey;

    public static UlstuScheduleAPI with(Context context) {
        UlstuScheduleAPI ulstuScheduleAPI = new UlstuScheduleAPI();
        ulstuScheduleAPI.mContext = context;
        return ulstuScheduleAPI;
    }

    public UlstuScheduleAPI makeRequest(String key) {
        mUrl = URL_BASE_PART + key;
        mKey = key;
        return this;
    }

    public UlstuScheduleAPI makeRequest(String key, int id) {
        mUrl = URL_BASE_PART + key + "/" + id;
        mKey = key;
        return this;
    }

    public UlstuScheduleAPI setReceiver(ScheduleReceiver receiver) {
        mReceiver = receiver;
        return this;
    }

    public void request() {
        if (mContext == null || mUrl == null || mReceiver == null || mKey == null)
            throw new RequestNotCorrectException();

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String json = intent.getStringExtra("json");
                deliverResponseFromNetwork(json);
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        mContext.registerReceiver(br, intentFilter);

        Intent intent = new Intent(mContext, JsonDownloaderService.class)
                .putExtra("url", mUrl);
        mContext.startService(intent);
    }

    @SuppressWarnings("unchecked")
    private void deliverResponseFromNetwork(String json) {
        if (json == null || json.isEmpty()) {
            mReceiver.onDataReceived(null);
            clearFields();
            return;
        }

        Gson gson = new GsonBuilder().create();
        switch (mKey) {
            case Schedule.GROUP:
                mReceiver.onDataReceived(gson.fromJson(json, Group.class));
                break;
            case Schedule.GROUPS:
                mReceiver.onDataReceived(gson.fromJson(json, Group[].class));
                break;
            case Schedule.CATHEDRA:
                mReceiver.onDataReceived(gson.fromJson(json, Cathedra.class));
                break;
            case Schedule.CATHEDRIES:
                mReceiver.onDataReceived(gson.fromJson(json, Cathedra[].class));
                break;
            case Schedule.FACULTY:
                mReceiver.onDataReceived(gson.fromJson(json, Faculty.class));
                break;
            case Schedule.FACULTIES:
                mReceiver.onDataReceived(gson.fromJson(json, Faculty[].class));
                break;
            case Schedule.LESSON:
                mReceiver.onDataReceived(gson.fromJson(json, Lesson.class));
                break;
            case Schedule.LESSONS:
                mReceiver.onDataReceived(gson.fromJson(json, Lesson[].class));
                break;
            case Schedule.TEACHER:
                mReceiver.onDataReceived(gson.fromJson(json, Teacher.class));
                break;
            case Schedule.TEACHERS:
                mReceiver.onDataReceived(gson.fromJson(json, Teacher[].class));
                break;
            case Schedule.TEACHER_LESSONS:
                mReceiver.onDataReceived(gson.fromJson(json, TeacherLessons.class));
                break;
        }
        clearFields();
    }

    private boolean deliverResponseFromStorage() {
        switch (mKey) {
            case Schedule.GROUP:
                mReceiver.onDataReceived(gson.fromJson(json, Group.class));
                break;
            case Schedule.GROUPS:
                mReceiver.onDataReceived(gson.fromJson(json, Group[].class));
                break;
            case Schedule.CATHEDRA:
                mReceiver.onDataReceived(gson.fromJson(json, Cathedra.class));
                break;
            case Schedule.CATHEDRIES:
                mReceiver.onDataReceived(gson.fromJson(json, Cathedra[].class));
                break;
            case Schedule.FACULTY:
                mReceiver.onDataReceived(gson.fromJson(json, Faculty.class));
                break;
            case Schedule.FACULTIES:
                mReceiver.onDataReceived(gson.fromJson(json, Faculty[].class));
                break;
            case Schedule.LESSON:
                mReceiver.onDataReceived(gson.fromJson(json, Lesson.class));
                break;
            case Schedule.LESSONS:
                mReceiver.onDataReceived(gson.fromJson(json, Lesson[].class));
                break;
            case Schedule.TEACHER:
                mReceiver.onDataReceived(gson.fromJson(json, Teacher.class));
                break;
            case Schedule.TEACHERS:
                mReceiver.onDataReceived(gson.fromJson(json, Teacher[].class));
                break;
            case Schedule.TEACHER_LESSONS:
                mReceiver.onDataReceived(gson.fromJson(json, TeacherLessons.class));
                break;
        }
    }

    private void clearFields() {
        mContext = null;
        mReceiver = null;
        mUrl = null;
    }
}