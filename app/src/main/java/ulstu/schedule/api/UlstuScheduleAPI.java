package ulstu.schedule.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import io.realm.Realm;
import ru.ulstu_team.ulstuschedule.data.JsonDownloadService;
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleRequest;

public class UlstuScheduleAPI {

    public static final String BROADCAST_ACTION = "ru.ulstu_team.ulstuschedule.api.request";

    private static final String URL_BASE_PART = "http://ulstuschedule.azurewebsites.net/ulstu/";
    private static BroadcastReceiver broadcastReceiver;

    private static Context mContext;
    private static ScheduleRequest mRequest;

    private UlstuScheduleAPI() {
    }


    public static void request(Context context, ScheduleRequest request) {
        if (request.getKey() == null || request.getDataType() == null
                || request.getCallbacks() == null)
            throw new RequestNotCorrectException();

        mContext = context;
        mRequest = request;

        requestDataFromServer();
    }

    private static void requestDataFromServer() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String json = intent.getStringExtra("json");
                deliverResponseFromNetwork(json);
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        mContext.registerReceiver(broadcastReceiver, intentFilter);

        mContext.startService(
                new Intent(mContext, JsonDownloadService.class)
                        .putExtra("url", getUrl())
                        .putExtra("broadcast_action", BROADCAST_ACTION));
    }

    private static String getUrl() {
        return (mRequest.getId() == 0) ?
                URL_BASE_PART + mRequest.getKey() :
                URL_BASE_PART + mRequest.getKey() + "/" + mRequest.getId();
    }

    @SuppressWarnings("unchecked")
    private static void deliverResponseFromNetwork(final String json) {
        if (json == null || json.isEmpty()) {
            return;
        }
        final Realm mRealm = Realm.getDefaultInstance();

        // If json starts with '{' then it is a JSONObject and model is one
        final boolean mIsOneModel = json.trim().charAt(0) == '{';
        final Class mClass = mRequest.getDataType();

        mRealm.executeTransaction(
                new Realm.Transaction() {

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
                        mRequest.getCallbacks().onSuccess();
                    }

                    @Override
                    public void onError(Exception e) {
                        mRequest.getCallbacks().onError(e);
                    }
                }
        );

        mRealm.close();
        mContext.unregisterReceiver(broadcastReceiver);
    }
}