package silantyevmn.ru.weather.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import silantyevmn.ru.weather.R;
import silantyevmn.ru.weather.database.city.CityEntity;
import silantyevmn.ru.weather.database.history.HistoryEntity;
import silantyevmn.ru.weather.utils.DataPreference;

/**
 * Created by silan on 04.07.2018.
 */

public final class DataBaseSource {
    private final String DATABASE_NAME = "cities.db";
    private DataBase dataBase;
    private static DataBaseSource dataBaseSource;

    private DataBaseSource(Context context) {
        dataBase = Room.databaseBuilder(context, DataBase.class, DATABASE_NAME).allowMainThreadQueries().build();
        //если список пустой
        if (getListCityEntity().size() == 0) {
            //добавляем в список текущее местоположение
            CityEntity cityEntity = new CityEntity(context.getString(R.string.main_recucler_text_current_location));
            //устанавливаем location истина
            cityEntity.setLocation(true);
            dataBase.cityDao().insert(cityEntity);
        }
    }

    public List<CityEntity> getListCityEntity() {
        return dataBase.cityDao().getAll();
    }

    public List<HistoryEntity> getHistory() {
        return dataBase.historyDao().getAll();
    }

    public void insert(CityEntity cityEntity) {
        dataBase.cityDao().insert(cityEntity);
        dataBase.historyDao().insert(new HistoryEntity(cityEntity.getName(), "insert", DataPreference.getTime()));
    }

    public void delete(CityEntity cityEntity) {
        dataBase.cityDao().delete(cityEntity);
        dataBase.historyDao().insert(new HistoryEntity(cityEntity.getName(), "delete", DataPreference.getTime()));
    }

    public void update(CityEntity cityEntity) {
        dataBase.cityDao().update(cityEntity);
        dataBase.historyDao().insert(new HistoryEntity(cityEntity.getName(), "update", DataPreference.getTime()));
    }

    public CityEntity getCityByName(String name) {
        return dataBase.cityDao().getCityByName(name);
    }

    public static void init(Context context) {
        if (dataBaseSource == null) {
            dataBaseSource = new DataBaseSource(context);
        }
    }

    public static DataBaseSource getInstance() {
        return dataBaseSource;
    }
}
