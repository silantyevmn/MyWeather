package silantyevmn.ru.weather.database.city;

import android.arch.persistence.room.*;

import org.jetbrains.annotations.NotNull;

/**
 * Created by silan on 04.07.2018.
 */

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class CityEntity {

    @PrimaryKey
    @NotNull
    @ColumnInfo( name = "name")
    private String name;

    @ColumnInfo
    private int temperature;

    @ColumnInfo
    private int humidity; //влажность

    @ColumnInfo
    private int pressure; //давление

    @ColumnInfo
    private int wind; //скорость ветра

    @ColumnInfo
    private String countryCode; //ru,en...

    @ColumnInfo
    private String icon; //01d,02n...

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public CityEntity(@NotNull String name) {
        this.name = name;
    }

    public String getTemperature(String prefix) {
        if (temperature > 0) {
            return "+" + temperature + prefix;
        } else if (temperature < 0) {
            return "-" + temperature + prefix;
        } else return temperature + prefix;
    }

    public String getHumidity(String prefix) {
        //String prefix=context.getResources().getString(R.string.text_prefix_humidity);
        return humidity + prefix;
    }

    public String getPressure(String prefix) {
        //String prefix=context.getResources().getString(R.string.text_prefix_pressure);
        return pressure + prefix;
    }

    public String getWind(String prefix) {
        //String prefix=context.getResources().getString(R.string.text_prefix_wind);
        return wind + prefix;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public int getWind() {
        return wind;
    }

    public String getIcon() {
        return icon;
    }
}
