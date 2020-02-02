package uz.suhrob.sweather.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 30.01.2020.
 */

public class SharedPreferencesHelper {

    private final String fileName = "sweather";
    private final String cityId = "city_id";
    private final String firstRun = "first_run";
    private Context context;

    public SharedPreferencesHelper(Context context) {
        this.context = context;
    }

    public int getCityId() {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getInt(cityId, -1);
    }

    public void setCityId(int id) {
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putInt(cityId, id);
        editor.apply();
    }

    public boolean isFirstRun() {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        int x = preferences.getInt(firstRun, 0);
        if (x == 0) {
            SharedPreferences.Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
            editor.putInt(firstRun, 1);
            editor.apply();
            return true;
        }
        return false;
    }

}
