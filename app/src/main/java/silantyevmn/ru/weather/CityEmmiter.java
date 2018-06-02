package silantyevmn.ru.weather;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by silan on 17.05.2018.
 */

public class CityEmmiter {
    private static ArrayList<City> cities;

    public static ArrayList<City> getCities() {
        return cities;
    }

    public static void initNewCityParam(Context context) {
        String[] arrCity = context.getResources().getStringArray(R.array.city_selection);
        cities = new ArrayList<>();
        for (int i = 0; i < arrCity.length; i++) {
            String name = arrCity[i].toString();
            int temperature = getRandomMinMax(10, 30);
            int humidity = getRandomMinMax(10, 100);
            int pressure = getRandomMinMax(500, 800);
            int wind = getRandomMinMax(1, 20);
            cities.add(i, new City(name, temperature, humidity, pressure, wind));
        }
    }

    private static int getRandomMinMax(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }
}
