package silantyevmn.ru.weather.utils;

import java.util.ArrayList;

/**
 * Created by silan on 17.05.2018.
 */

public class CityEmmiter {
    private static ArrayList<City> cities;

    public static ArrayList<City> getCities() {
        return cities;
    }

    public static void initNewCityParam(String[] arrCity) {
        cities = new ArrayList<>();
        for (int i = 0; i < arrCity.length; i++) {
            String name = arrCity[i].toString();
            cities.add(getNewCity(name));
        }
    }

    private static City getNewCity(String name) {
        int temperature = getRandomMinMax(10, 30);
        int humidity = getRandomMinMax(10, 100);
        int pressure = getRandomMinMax(500, 800);
        int wind = getRandomMinMax(1, 20);
        return new City(name, temperature, humidity, pressure, wind);
    }

    private static int getRandomMinMax(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public static void setAddNewCity(String name, int position) {
        cities.add(getNewCity(name));
    }

    public static void setDeleteCity(int position) {
        cities.remove(position);
    }

    public static void setEditCity(String name, int position) {
        cities.get(position).setName(name);
    }
}
