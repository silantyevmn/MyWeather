package silantyevmn.ru.weather.database.city;

import android.arch.persistence.room.*;

import silantyevmn.ru.weather.database.city.CityDao;
import silantyevmn.ru.weather.database.history.HistoryDao;

/**
 * Created by silan on 04.07.2018.
 */
@Database(entities = {CityEntity.class},version = 1,exportSchema = false)
public abstract class CityDataBase extends RoomDatabase{
    public abstract CityDao cityDao();
    public abstract HistoryDao historyDao();
}

