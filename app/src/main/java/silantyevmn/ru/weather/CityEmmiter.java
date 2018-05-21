package silantyevmn.ru.weather;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by silan on 17.05.2018.
 */

public class CityEmmiter {
    private static ArrayList<City> cities;
    private static boolean isHumidity, isPressure, isWind;

    public static void setIsHumidity(boolean isHumidity) {
        CityEmmiter.isHumidity = isHumidity;
    }

    public static void setIsPressure(boolean isPressure) {
        CityEmmiter.isPressure = isPressure;
    }

    public static void setIsWind(boolean isWind) {
        CityEmmiter.isWind = isWind;
    }

    public static boolean getIsHumidity() {

        return isHumidity;
    }

    public static boolean getIsPressure() {
        return isPressure;
    }

    public static boolean getIsWind() {
        return isWind;
    }

    public static ArrayList<City> getCities() {
        return cities;
    }

    public static void initNewCityParam(Context context, int minTemperature, int maxTemperature) {
        String[] arrCity = context.getResources().getStringArray(R.array.city_selection);
        cities = new ArrayList<>();
        for (int i = 0; i < arrCity.length; i++) {
            String name = arrCity[i].toString();
            int temperature = getRandomMinMax(minTemperature, maxTemperature);
            int humidity = getRandomMinMax(10, 100);
            int pressure = getRandomMinMax(500, 800);
            int wind = getRandomMinMax(1, 20);
            cities.add(i, new City(name, temperature, humidity, pressure, wind));
        }
    }

    private static int getRandomMinMax(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public static int getPositionFindCity(String value) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().toUpperCase().equals(value.toUpperCase())) {
                return i;
            }
        }
        return -1;
    }
}
