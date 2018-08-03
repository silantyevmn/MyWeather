package silantyevmn.ru.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import silantyevmn.ru.weather.Start;

/**
 * Created by silan on 23.06.2018.
 */

public class CityPreference {
    private final String SHARED_PREFERENCES_NAME = "setting";
    public static final boolean HUMIDITY_DEFAULT = true;
    public static final boolean PRESSURE_DEFAULT = true;
    public static final boolean WIND_DEFAULT = true;
    public static final String KEY_HUMIDITY = "humidity";
    public static final String KEY_PRESSURE = "pressure";
    public static final String KEY_WIND = "wind";
    public static final String KEY_POSITION = "position";
    public static final int POSITION_DEFAULT = 0;

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedSettings;
    private static CityPreference cityPreference;

    private CityPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        sharedSettings= PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getPosition() {
        return sharedPreferences.getInt(KEY_POSITION, POSITION_DEFAULT);
    }

    public void setPosition(int position) {
        sharedPreferences.edit().putInt(KEY_POSITION, position).apply();
    }

    public boolean getIsHumidity() {
        return sharedSettings.getBoolean(KEY_HUMIDITY, HUMIDITY_DEFAULT);
    }

    public boolean getIsPressure() {
        return sharedSettings.getBoolean(KEY_PRESSURE, PRESSURE_DEFAULT);
    }

    public boolean getIsWind() {
        return sharedSettings.getBoolean(KEY_WIND, WIND_DEFAULT);
    }

    public static CityPreference getInstance() {
        return cityPreference;
    }

    public static void init(Context context) {
        if(cityPreference==null){
            cityPreference=new CityPreference(context);
        }
    }

}
