package silantyevmn.ru.weather.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private boolean isHumidity;
    private boolean isPressure;
    private boolean isWind;
    private String countryCode; //ru,en...
    private String icon; //01d,02n...

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setTemperature(int temperature){
        this.temperature=temperature;
    }
    public boolean isHumidity() {
        return isHumidity;
    }

    public void setIsHumidity(boolean humidity) {
        isHumidity = humidity;
    }

    public boolean isPressure() {
        return isPressure;
    }

    public void setIsPressure(boolean pressure) {
        isPressure = pressure;
    }

    public boolean isWind() {
        return isWind;
    }

    public void setIsWind(boolean wind) {
        isWind = wind;
    }

    public String getCurrentDate(String format){
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(Calendar.getInstance().getTime());
    }

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

    public void setName(String name){
        this.name=name;
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

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s %s", name, temperature);
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setPressure(int pressure) {
        this.pressure=pressure;
    }

    public void setWind(int wind) {
        this.wind=wind;
    }
}
