package silantyevmn.ru.weather;

import android.app.Activity;
import android.content.SharedPreferences;


/**
 * Created by silan on 21.05.2018.
 */

public class WeatherPreferencer {
    private static WeatherPreferencer weatherPreferencer = null;
    private SharedPreferences sharedPreferences;
    public static final boolean SWITCH_HUMIDITY_DEFAULT = false;
    public static final boolean SWITCH_PRESSURE_DEFAULT = false;
    public static final boolean SWITCH_WIND_DEFAULT = false;
    public static final String KEY_HUMIDITY = "key_humidity";
    public static final String KEY_PRESSURE = "key_pressure";
    public static final String KEY_WIND = "key_wind";
    public static final String KEY_CURRENT_CITY = "current_city";
    public static final String CURRENT_CITY_DEFAULT = "";

    /* public WeatherPreferencer(Activity activity) {
         this.sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
     }*/
    private WeatherPreferencer(Activity activity) {
        sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public static WeatherPreferencer getInstance(Activity activity) {
        if (weatherPreferencer == null) {
            weatherPreferencer = new WeatherPreferencer(activity);
        }
        return weatherPreferencer;
    }

    public void save(String editTextCity, boolean isHumidity, boolean isPressure, boolean isWind) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //записываем показатели
        editor.putString(KEY_CURRENT_CITY, editTextCity);
        editor.putBoolean(KEY_HUMIDITY, isHumidity);
        editor.putBoolean(KEY_PRESSURE, isPressure);
        editor.putBoolean(KEY_WIND, isWind);
        editor.apply();
    }

    public String getEditTextCity() {
        return sharedPreferences.getString(KEY_CURRENT_CITY, CURRENT_CITY_DEFAULT);
    }

    public boolean getIsHumidity() {
        return sharedPreferences.getBoolean(KEY_HUMIDITY, SWITCH_HUMIDITY_DEFAULT);
    }

    public boolean getIsPressure() {
        return sharedPreferences.getBoolean(KEY_PRESSURE, SWITCH_PRESSURE_DEFAULT);
    }

    public boolean getIsWind() {
        return sharedPreferences.getBoolean(KEY_WIND, SWITCH_WIND_DEFAULT);
    }
}
