package ru.ulstu_team.ulstuschedule.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class FontUtil {

    private static final String TAG = "FontUtil";

    private FontUtil() {}

    public static Typeface getFont(Context context, String fontName) {
        try {
            String pathBegin = "fonts/";
            if (!fontName.startsWith(pathBegin))
                fontName = pathBegin + fontName;
            return Typeface.createFromAsset(context.getAssets(), fontName);
        } catch (Exception ex) {
            Log.e(TAG, "getFont: ", ex);
        }
        return Typeface.DEFAULT;
    }
}
