package ulstu.schedule.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    private static final String FILE_NAME = "NonStopNailCache";
    private Context context;

    public PrefsManager(Context context){
        this.context = context;
    }

    public String getString(String key){
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, 0);
        return prefs.getString(key, "");
    }

    public void putString(String key, String value){
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public int getInt(String key){
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, 0);
        return prefs.getInt(key, 0);
    }

    public void putInt(String key, int value){
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public boolean contains (String key) {
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, 0);
        return prefs.contains(key);
    }

    public void delete (String key) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, 0);
        preferences.edit().remove(key).apply();
    }
}