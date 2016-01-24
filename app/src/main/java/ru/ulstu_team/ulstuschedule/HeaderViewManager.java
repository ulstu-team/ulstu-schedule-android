package ru.ulstu_team.ulstuschedule;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.data.local.PrefsKeys;
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager;

public class HeaderViewManager {

    private final Context mContext;
    private final ImageView mImageView;

    private Timer timer;
    private boolean isScheduled;
    private final View navHeaderView;
    private final PrefsManager mPrefsManager;

    @Inject
    public HeaderViewManager(Context context, PrefsManager prefsManager) {
        mContext = context;
        mPrefsManager = prefsManager;

        LayoutInflater inflater = LayoutInflater.from(context);
        navHeaderView = inflater.inflate(R.layout.nav_header_main, null, false);
        mImageView = (ImageView) navHeaderView.findViewById(R.id.headerImage);

        TextView username = (TextView) navHeaderView.findViewById(R.id.headerUsername);
        username.setText(prefsManager.getString(PrefsKeys.USER_NAME));
    }

    public View getNavHeaderView() {
        return navHeaderView;
    }

    public void start() {
        if (isScheduled)
            return;

        timer = new Timer();
        final Handler uiHandler = new Handler();
        final TimerTask changeTask = new TimerTask() {
            @Override
            public void run() {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            NavHeaderImageHelper.setRandomHeaderImage(mContext, mImageView);
                        } catch (Exception e) {
                            stop();
                        }
                    }
                });
            }
        };
        timer.schedule(changeTask, 0, 15000);
        NavHeaderImageHelper.setRandomHeaderImage(mContext, mImageView);

        isScheduled = true;
    }

    public void stop() {
        timer.cancel();
        isScheduled = false;
    }


    private static class NavHeaderImageHelper {

        private static final String TAG = "NavHeaderImageHelper";

        private NavHeaderImageHelper() {
        }

        private static String[] imagePaths;
        private static Random random = new Random(new Date().getTime());

        public static void setRandomHeaderImage(Context context, ImageView imageView) {
            getImagePaths(context);
            String path = "file:///android_asset/headerImages/" + imagePaths[random.nextInt(imagePaths.length)];
            Glide.with(context)
                    .load(path)
                    .centerCrop()
                    .skipMemoryCache(true)
                    .crossFade(1200)
                    .into(imageView);
        }

        private static String[] getImagePaths(Context context) {
            if (imagePaths != null)
                return imagePaths;

            AssetManager manager = context.getAssets();
            try {
                imagePaths = manager.list("headerImages");
            } catch (IOException e) {
                // TODO: report about bug
                Log.e(TAG, "getImagePaths: " + e.getMessage());
                imagePaths = new String[]{"0.jpg"};
            }

            return imagePaths;
        }
    }
}
