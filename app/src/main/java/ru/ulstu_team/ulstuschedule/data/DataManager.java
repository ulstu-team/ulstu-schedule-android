package ru.ulstu_team.ulstuschedule.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.ulstu_team.ulstuschedule.data.local.PrefsKeys;
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager;
import ru.ulstu_team.ulstuschedule.data.remote.DownloadException;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;
import ru.ulstu_team.ulstuschedule.data.remote.VolleySingleton;

public class DataManager {

    private static final String URL_BASE_PART = "http://ulstuschedule.azurewebsites.net/ulstu/";

    private Context mContext;
    private PrefsManager mPrefsManager;
    private VolleySingleton mVolley;

    @Inject
    public DataManager(Context context, PrefsManager prefsManager, VolleySingleton volley) {
        mContext = context;
        mPrefsManager = prefsManager;
        mVolley = volley;
    }

    public int getUserId() {
        return mPrefsManager.getInt(PrefsKeys.USER_ID);
    }

    public String getUserName() {
        return mPrefsManager.getString(PrefsKeys.USER_NAME);
    }

    private String getUrl(String key, int id) {
        String url = URL_BASE_PART + key;
        url = id != 0 ? url + id : url;
        return url;
    }

    public Context getContext() {
        return mContext;
    }

    public void executeRequest(final ScheduleRequest request) {
        Request volleyRequest = new StringRequest(getUrl(request.getKey(), request.getId()),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        saveInDatabase(response, request);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        request.getCallbacks().onError(error);
                    }
                }
        ).setRetryPolicy(new RetryPolicy() {
            @Override public int getCurrentTimeout() { return 3000;}
            @Override public int getCurrentRetryCount() { return 2;}
            @Override public void retry(VolleyError error) throws VolleyError { }
        });
        mVolley.addToRequestQueue(volleyRequest);
    }

    private void saveInDatabase(final String json, final ScheduleRequest request) {
        if (json == null || json.isEmpty()) {
            request.getCallbacks().onError(new DownloadException());
            return;
        }
        final Realm mRealm = Realm.getDefaultInstance();

        // If json starts with '{' then it is a JSONObject and model is one
        final boolean mIsOneModel = json.trim().charAt(0) == '{';
        final Class clazz = request.getDataType();

        mRealm.beginTransaction();

        RealmResults objects = request.getRealmQuery().findAll();
        objects.clear();

        if (mIsOneModel) {
            mRealm.createOrUpdateObjectFromJson(clazz, json);
        } else {
            mRealm.createOrUpdateAllFromJson(clazz, json);
        }

        mRealm.commitTransaction();
        request.getCallbacks().onSuccess();

//        mRealm.executeTransaction(
//                new Realm.Transaction() {
//
//                    @Override
//                    public void execute(Realm realm) {
//                        RealmResults objects = request.getRealmQuery().findAll();
//                        objects.clear();
//
//                        if (mIsOneModel) {
//                            mRealm.createOrUpdateObjectFromJson(clazz, json);
//                        } else {
//                            mRealm.createOrUpdateAllFromJson(clazz, json);
//                        }
//                    }
//                }, new Realm.Transaction.Callback() {
//                    @Override
//                    public void onSuccess() {
//                        request.getCallbacks().onSuccess();
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        request.getCallbacks().onError(e);
//                    }
//                }
//        );
        mRealm.close();
    }
}
