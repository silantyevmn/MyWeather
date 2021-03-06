package silantyevmn.ru.weather.utils.json.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by silan on 30.06.2018.
 */

public interface IOpenWeather {
    //@GET("data/2.5/weather")
    @GET("data/2.5/weather")
    Call<WeatherRequestOneDay> loadWeather(@Query("q") String cityCountry,
                                           @Query("appid") String keyApi,
                                           @Query("units") String units);

    @GET("data/2.5/weather")
    Call<WeatherRequestOneDay> loadWeatherLocation(@Query("lat") double lat,
                                                   @Query("lon") double lon,
                                                   @Query("appid") String keyApi,
                                                   @Query("units") String units);
}
