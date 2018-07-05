package silantyevmn.ru.weather.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import silantyevmn.ru.weather.database.city.CityEntity;
import silantyevmn.ru.weather.database.history.HistoryEntity;
import silantyevmn.ru.weather.utils.DataPreference;

/**
 * Created by silan on 04.07.2018.
 */

public final class DataBaseSource {
    private static DataBaseSource dataBaseSource =null;
    private final String DATABASE_NAME="cities.db";
    private final String BASE_CITY="Moscow";
    private static DataBase dataBase;

    public static DataBaseSource initDataBase(Context context){
        if(dataBaseSource ==null){
            dataBaseSource =new DataBaseSource(context);
        }
        return dataBaseSource;
    }
    private DataBaseSource(Context context){
        dataBase = Room.databaseBuilder(context,DataBase.class,DATABASE_NAME).allowMainThreadQueries().build();
        //добавляем начальный город
        dataBase.cityDao().insert(new CityEntity(BASE_CITY));
    }

    public static List<CityEntity> getListCityEntity(){
        return dataBase.cityDao().getAll();
    }
    public static List<HistoryEntity> getHistory(){
        return dataBase.historyDao().getAll();
    }

    public static void insert(CityEntity cityEntity){
        dataBase.cityDao().insert(cityEntity);
        dataBase.historyDao().insert(new HistoryEntity(cityEntity.getName(),"insert", DataPreference.getTime()));
    }

    public static void delete(CityEntity cityEntity){
        dataBase.cityDao().delete(cityEntity);
        dataBase.historyDao().insert(new HistoryEntity(cityEntity.getName(),"delete",DataPreference.getTime()));
    }

    public static void update(CityEntity cityEntity){
        dataBase.cityDao().update(cityEntity);
        dataBase.historyDao().insert(new HistoryEntity(cityEntity.getName(),"update",DataPreference.getTime()));
    }

    public static CityEntity getCityByName(String name){
        return dataBase.cityDao().getCityByName(name);
    }

}
