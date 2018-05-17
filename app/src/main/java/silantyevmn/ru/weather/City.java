package silantyevmn.ru.weather;

import java.util.Locale;

/**
 * Created by silan on 17.05.2018.
 */

public class City {
    private String name;
    private int temperature;

    public City(String name, int temperature) {
        this.name = name;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public String getTemperature() {
        return temperature>0?"+"+temperature:"-"+temperature;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(),"%s %s",name,temperature);
    }
}
