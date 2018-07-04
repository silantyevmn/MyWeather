package silantyevmn.ru.weather;

import android.app.Application;

import silantyevmn.ru.weather.database.DataBaseSource;
import silantyevmn.ru.weather.utils.CityPreference;

/**
 * Created by silan on 04.07.2018.
 */

public class Start extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //иницилизируем базу данных
        DataBaseSource.initDataBase(this);
        CityPreference.getPreference(this);
    }
}
