package silantyevmn.ru.weather.database;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by silan on 04.07.2018.
 */

public class DataBase{
    private static DataBase dataBase=null;
    private final String DATABASE_NAME="cities";
    private final String BASE_CITY="Moscow";
    private CityDataBase cityDataBase;

    public static DataBase initDataBase(Context context){
        if(dataBase==null){
            dataBase=new DataBase(context);
        }
        return dataBase;
    }
    private DataBase(Context context){
        cityDataBase = Room.databaseBuilder(context,CityDataBase.class,DATABASE_NAME).allowMainThreadQueries().build();
        //добавляем начальный город
        cityDataBase.dreamDioctionaryDAO().insert(new CityEntity(BASE_CITY));
    }

    public CityDao getCityDataBase(){
        return cityDataBase.dreamDioctionaryDAO();
    }

}
