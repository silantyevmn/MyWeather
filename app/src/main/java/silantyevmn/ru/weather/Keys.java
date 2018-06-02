package silantyevmn.ru.weather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by silan on 21.05.2018.
 */

public class Keys {
    public static final boolean HUMIDITY_DEFAULT = false;
    public static final boolean PRESSURE_DEFAULT = false;
    public static final boolean WIND_DEFAULT = false;
    public static final String KEY_HUMIDITY = "key_humidity";
    public static final String KEY_PRESSURE = "key_pressure";
    public static final String KEY_WIND = "key_wind";
    public static final String KEY_POSITION="position";
    public static final int POSITION_DEFAULT=0;

    public static int getPosition(Activity activity) {
        return activity.getPreferences(Context.MODE_PRIVATE).getInt(KEY_POSITION, POSITION_DEFAULT);
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
