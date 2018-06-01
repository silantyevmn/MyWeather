package silantyevmn.ru.weather;

import java.util.Locale;

/**
 * Created by silan on 17.05.2018.
 */

public class City {
    private String name;
    private int temperature;
    private int humidity; //влажность
    private int pressure; //давление
    private int wind; //скорость ветра

    public City(String name, int temperature, int humidity, int pressure, int wind) {
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public String getTemperature(String prefix) {
        if (temperature > 0) {
            return "+" + temperature + prefix;
        } else if (temperature < 0) {
            return "-" + temperature + prefix;
        } else return temperature + prefix;
    }

    public String getHumidity(String prefix) {
        return humidity + prefix;
    }

    public String getPressure(String prefix) {
        return pressure + prefix;
    }

    public String getWind(String prefix) {
        return wind + prefix;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s %s", name, temperature);
    }
}
