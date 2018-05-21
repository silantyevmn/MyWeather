package silantyevmn.ru.weather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;


/**
 * Created by silan on 21.05.2018.
 */

public class WeatherPreferencer {
    private SharedPreferences sharedPreferences;
    private final boolean SWITCH_HUMIDITY_DEFAULT = false;
    private final boolean SWITCH_PRESSURE_DEFAULT = false;
    private final boolean SWITCH_WIND_DEFAULT = false;
    private final String KEY_HUMIDITY = "key_humidity";
    private final String KEY_PRESSURE = "key_pressure";
    private final String KEY_WIND = "key_wind";
    private Bundle bundle;

    public WeatherPreferencer(Activity activity, Bundle bundle) {
        this.sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
        this.bundle = bundle;
    }

    public void save(boolean isHumidity, boolean isPressure, boolean isWind) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //записываем показатели
        editor.putBoolean(KEY_HUMIDITY, isHumidity);
        editor.putBoolean(KEY_PRESSURE, isPressure);
        editor.putBoolean(KEY_WIND, isWind);
        editor.apply();
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
