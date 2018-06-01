package silantyevmn.ru.weather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by silan on 21.05.2018.
 */

public class DataPreferences {
    public static final boolean HUMIDITY_DEFAULT = false;
    public static final boolean PRESSURE_DEFAULT = false;
    public static final boolean WIND_DEFAULT = false;
    public static final String KEY_HUMIDITY = "key_humidity";
    public static final String KEY_PRESSURE = "key_pressure";
    public static final String KEY_WIND = "key_wind";
    public static final String KEY_CURRENT_CITY = "current_city";
    public static final String CURRENT_CITY_DEFAULT = "";

    public static void save(Activity activity,String editTextCity, boolean isHumidity, boolean isPressure, boolean isWind) {
        SharedPreferences.Editor editor = activity.getPreferences(Context.MODE_PRIVATE).edit();
        //записываем показатели
        editor.putString(KEY_CURRENT_CITY, editTextCity);
        editor.putBoolean(KEY_HUMIDITY, isHumidity);
        editor.putBoolean(KEY_PRESSURE, isPressure);
        editor.putBoolean(KEY_WIND, isWind);
        editor.apply();
    }

    public static String getEditTextCity(Activity activity) {
        return activity.getPreferences(Context.MODE_PRIVATE).getString(KEY_CURRENT_CITY, CURRENT_CITY_DEFAULT);
    }

    public static boolean getIsHumidity(Activity activity) {
        return activity.getPreferences(Context.MODE_PRIVATE).getBoolean(KEY_HUMIDITY, HUMIDITY_DEFAULT);
    }

    public static boolean getIsPressure(Activity activity) {
        return activity.getPreferences(Context.MODE_PRIVATE).getBoolean(KEY_PRESSURE, PRESSURE_DEFAULT);
    }

    public static boolean getIsWind(Activity activity) {
        return activity.getPreferences(Context.MODE_PRIVATE).getBoolean(KEY_WIND, WIND_DEFAULT);
    }
}
