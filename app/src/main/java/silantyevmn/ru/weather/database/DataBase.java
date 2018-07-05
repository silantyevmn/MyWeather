package silantyevmn.ru.weather.database;

import android.arch.persistence.room.*;

import silantyevmn.ru.weather.database.city.CityDao;
import silantyevmn.ru.weather.database.city.CityEntity;
import silantyevmn.ru.weather.database.history.HistoryDao;
import silantyevmn.ru.weather.database.history.HistoryEntity;

/**
 * Created by silan on 04.07.2018.
 */
@Database(entities = {CityEntity.class, HistoryEntity.class},version = 1,exportSchema = false)
public abstract class DataBase extends RoomDatabase{
    public abstract CityDao cityDao();
    public abstract HistoryDao historyDao();
}

