package silantyevmn.ru.weather.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

/**
 * Created by silan on 04.07.2018.
 */

public final class DataBaseSource {
    private static DataBaseSource dataBaseSource =null;
    private final String DATABASE_NAME="cities";
    private final String BASE_CITY="Moscow";
    private static CityDataBase cityDataBase;

    public static DataBaseSource initDataBase(Context context){
        if(dataBaseSource ==null){
            dataBaseSource =new DataBaseSource(context);
        }
        return dataBaseSource;
    }
    private DataBaseSource(Context context){
        cityDataBase = Room.databaseBuilder(context,CityDataBase.class,DATABASE_NAME).allowMainThreadQueries().build();
        //добавляем начальный город
        cityDataBase.dreamDioctionaryDAO().insert(new CityEntity(BASE_CITY));
    }

    public static List<CityEntity> getListCityEntity(){
        return cityDataBase.dreamDioctionaryDAO().getAll();
    }

    public static void insert(CityEntity cityEntity){
        cityDataBase.dreamDioctionaryDAO().insert(cityEntity);
    }

    public static void delete(CityEntity cityEntity){
        cityDataBase.dreamDioctionaryDAO().delete(cityEntity);
    }

    public static void update(CityEntity cityEntity){
        cityDataBase.dreamDioctionaryDAO().update(cityEntity);
    }

    public static CityEntity getCityByName(String name){
        return cityDataBase.dreamDioctionaryDAO().getCityByName(name);
    }

}
