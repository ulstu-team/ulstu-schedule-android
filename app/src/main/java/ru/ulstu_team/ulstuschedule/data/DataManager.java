package ru.ulstu_team.ulstuschedule.data;

import android.content.Context;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.data.local.PrefsKeys;
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;
import ulstu.schedule.api.UlstuScheduleAPI;

public class DataManager {

    private Context mContext;
    private PrefsManager mPrefsManager;

    @Inject
    public DataManager(Context context, PrefsManager prefsManager) {
        mContext = context;
        mPrefsManager = prefsManager;
    }

    public int getUserId() {
        return mPrefsManager.getInt(PrefsKeys.USER_ID);
    }

    public String getUserName() {
        return mPrefsManager.getString(PrefsKeys.USER_NAME);
    }

    public void requestScheduleData(ScheduleRequest request) {
        UlstuScheduleAPI.request(mContext, request);
    }
}
