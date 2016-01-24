package ulstu.schedule.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import ru.ulstu_team.ulstuschedule.data.JsonDownloadService;

public class UlstuScheduleAPI {

    public static final String BROADCAST_ACTION = "ru.ulstu_team.ulstuschedule.api.request";

    private static final String URL_BASE_PART = "http://ulstuschedule.azurewebsites.net/ulstu/";
    private BroadcastReceiver broadcastReceiver;

    private UlstuScheduleAPI() {
    }

    private Context mContext;
    private Realm mRealm;

    private Class<? extends RealmObject> mClass;
    private ScheduleReceiver mReceiver;
    private Boolean mIsOneModel;
    private int mId = -1;
    private String mKey;
    private String mUrl;

    public static UlstuScheduleAPI with(Context context, Realm realm) {
        UlstuScheduleAPI ulstuScheduleAPI = new UlstuScheduleAPI();
        ulstuScheduleAPI.mContext = context;
        ulstuScheduleAPI.mRealm = realm;
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
        mId = id;
        return this;
    }

    public UlstuScheduleAPI setReceiver(ScheduleReceiver receiver) {
        mReceiver = receiver;
        return this;
    }

    public UlstuScheduleAPI get(Class<? extends RealmObject> dataType) {
        mClass = dataType;
        return this;
    }

    public UlstuScheduleAPI one() {
        mIsOneModel = true;
        return this;
    }

    public UlstuScheduleAPI many() {
        mIsOneModel = false;
        return this;
    }

    public void request() {
        if (mContext == null || mUrl == null || mReceiver == null
                || mKey == null || mClass == null || mIsOneModel == null)
            throw new RequestNotCorrectException();

        boolean containsInStorage = requestFromStorageIfContains();
        if (containsInStorage)
            return;

        requestDataFromServer();
    }

    @SuppressWarnings("unchecked")
    private boolean requestFromStorageIfContains() {
        if (mIsOneModel) {
            RealmObject model = mRealm.where(mClass).equalTo("Id", mId).findFirst();
            if (model == null) {
                return false;
            } else {
                mReceiver.onDataReceived(model);
            }
        } else {
            RealmResults models = mRealm.where(mClass).findAll();
            if (models.size() < 1) {
                return false;
            } else {
                mReceiver.onDataReceived(models);
            }
        }
        resetRequestState();
        return true;
    }


    private void requestDataFromServer() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String json = intent.getStringExtra("json");
                deliverResponseFromNetwork(json);
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        mContext.registerReceiver(broadcastReceiver, intentFilter);

        Intent intent = new Intent(mContext, JsonDownloadService.class)
                .putExtra("url", mUrl)
                .putExtra("broadcast_action", BROADCAST_ACTION);

        mContext.startService(intent);
    }

    @SuppressWarnings("unchecked")
    private void deliverResponseFromNetwork(final String json) {
        if (json == null || json.isEmpty()) {
            mReceiver.onDataReceived(null);
            resetRequestState();
            return;
        }

        mRealm.executeTransaction(new Realm.Transaction() {

              @Override
              public void execute(Realm realm) {
                  if (mIsOneModel) {
                      realm.createOrUpdateObjectFromJson(mClass, json);
                  } else {
                      realm.createOrUpdateAllFromJson(mClass, json);
                  }
              }
          }, new Realm.Transaction.Callback() {
              @Override
              public void onSuccess() {
                  if (mIsOneModel) {
                      mReceiver.onDataReceived(
                              mRealm.where(mClass).equalTo("Id", mId).findFirstAsync()
                      );
                  } else {
                      mReceiver.onDataReceived(
                              mRealm.where(mClass).findAll()
                      );
                  }
                  resetRequestState();
              }

              @Override
              public void onError(Exception e) {
                  // transaction is automatically rolled-back, do any cleanup here
                  resetRequestState();
              }
          }
        );
    }

    private void resetRequestState() {
        mContext = null;
        mReceiver = null;
        mUrl = null;
        mClass = null;
        mId = -1;

        //noinspection ConstantConditions
        mContext.unregisterReceiver(broadcastReceiver);
    }
}