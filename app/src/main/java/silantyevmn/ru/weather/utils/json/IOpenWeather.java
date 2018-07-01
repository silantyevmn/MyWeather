package silantyevmn.ru.weather.utils.json;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by silan on 30.06.2018.
 */

public interface IOpenWeather {
    //@GET("data/2.5/weather")
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry,
                                     @Query("appid") String keyApi,
                                     @Query("units") String units);

}
