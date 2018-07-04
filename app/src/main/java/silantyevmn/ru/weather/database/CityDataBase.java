package silantyevmn.ru.weather.database;

import android.arch.persistence.room.*;

/**
 * Created by silan on 04.07.2018.
 */
@Database(entities = {CityEntity.class},version = 1,exportSchema = false)
public abstract class CityDataBase extends RoomDatabase{
    public abstract CityDao dreamDioctionaryDAO();
}

