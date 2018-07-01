package silantyevmn.ru.weather.utils.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by silan on 30.06.2018.
 */
/*{
        "coord": {
        "lon": 46.03,
        "lat": 51.53
        },
        "weather": [
        {
        "id": 800,
        "main": "Clear",
        "description": "clear sky",
        "icon": "01n"
        }
        ],
        "base": "stations",
        "main": {
        "temp": 25,
        "pressure": 1009,
        "humidity": 50,
        "temp_min": 25,
        "temp_max": 25
        },
        "visibility": 10000,
        "wind": {
        "speed": 4,
        "deg": 80
        },
        "clouds": {
        "all": 0
        },
        "dt": 1530295200,
        "sys": {
        "type": 1,
        "id": 7343,
        "message": 0.0022,
        "country": "RU",
        "sunrise": 1530232914,
        "sunset": 1530292606
        },
        "id": 498677,
        "name": "Saratov",
        "cod": 200
        }*/
public class WeatherRequest {
    @SerializedName("weather")
    @Expose
    private Weather[] weathers;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private int cod;

    public Weather[] getWeathers() {
        return weathers;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Sys getSys() {
        return sys;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }
}

class Sys {
    /*    "sys": {
            "type": 1,
                    "id": 7343,
                    "message": 0.0022,
                    "country": "RU",
                    "sunrise": 1530232914,
                    "sunset": 1530292606*/
    @SerializedName("sunrise")
    @Expose
    private long sunrise;
    @SerializedName("sunset")
    @Expose
    private long sunset;
    @SerializedName("country")
    @Expose
    private String country;

    public String getCountry() {
        return country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}

class Wind {
    /*    "wind": {
            "speed": 4,
                    "deg": 80
        },*/
    @SerializedName("speed")
    @Expose
    private float speed;

    public float getSpeed() {
        return speed;
    }
}

class Weather {
    /*            "id": 800,
                        "main": "Clear",
                        "description": "clear sky",
                        "icon": "01n"*/
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("description")
    @Expose
    private String description;


    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }
}

class Main {
    /*//"main": {
    "temp": 25,
            "pressure": 1009,
            "humidity": 50,
            "temp_min": 25,
            "temp_max": 25
            },*/
    @SerializedName("temp")
    @Expose
    private float temp;
    @SerializedName("pressure")
    @Expose
    private float pressure;
    @SerializedName("humidity")
    @Expose
    private float humidity;

    public float getTemp() {
        return temp;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }
}
