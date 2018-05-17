package silantyevmn.ru.weather;

import android.content.Context;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by silan on 17.05.2018.
 */

public class CityEmmiter {
    private static ArrayList<City> cities;

    public static ArrayList<City> getCities() {
        return cities;
    }

    public static void initNewCityParam(Context context,int minTemperature,int maxTemperature){
        String[] arrCity=context.getResources().getStringArray(R.array.city_selection);
        cities=new ArrayList<>();
        for (int i = 0; i < arrCity.length; i++) {
            String name=arrCity[i].toString();
            int temperature=getRandomMinMax(minTemperature,maxTemperature);
            cities.add(i,new City(name,temperature));
        }
    }

    private static int getRandomMinMax(int min,int max){
        return (int)(Math.random()*(max-min))+min;
    }
}
