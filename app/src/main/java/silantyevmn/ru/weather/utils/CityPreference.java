package silantyevmn.ru.weather.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by silan on 23.06.2018.
 */

public class CityPreference {
    public static final boolean HUMIDITY_DEFAULT = false;
    public static final boolean PRESSURE_DEFAULT = false;
    public static final boolean WIND_DEFAULT = false;
    public static final String KEY_HUMIDITY = "humidity";
    public static final String KEY_PRESSURE = "pressure";
    public static final String KEY_WIND = "wind";
    public static final String KEY_POSITION = "position";
    public static final int POSITION_DEFAULT = 0;

    private SharedPreferences sharedPreferences;

    public CityPreference(Activity activity){
        sharedPreferences=activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public int getPosition(){
        return sharedPreferences.getInt(KEY_POSITION,POSITION_DEFAULT);
    }

    public void setPosition(int position){
        sharedPreferences.edit().putInt(KEY_POSITION,position).apply();
    }

}
