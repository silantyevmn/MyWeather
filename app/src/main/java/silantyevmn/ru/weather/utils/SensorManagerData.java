package silantyevmn.ru.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by silan on 20.06.2018.
 */

public class SensorManagerData {
    private SensorManager sensorManager;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;
    private int temperature=0;
    private int humidity=0;

    public SensorManagerData(Context context){
        // Менеджер датчиков
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
       /* //Получить все датчики, какие есть
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);*/
        //Датчик температуры  и влажности
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumidity=sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
    }

    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public Sensor getSensorTemperature() {
        return sensorTemperature;
    }

    public Sensor getSensorHumidity() {
        return sensorHumidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }



}
