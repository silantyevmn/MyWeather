package silantyevmn.ru.weather.utils.json;

import android.util.Log;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import silantyevmn.ru.weather.R;
import silantyevmn.ru.weather.utils.City;
import silantyevmn.ru.weather.utils.CityEmmiter;

/**
 * Created by silan on 30.06.2018.
 */

public class OpenWeatherRetrofit {
    private IOpenWeather iOpenWeather;
    private final String OPEN_WEATHER_KEY_API = "14f34cd242746f2d76bb04739d7485fe";
    //private final String OPEN_WEATHER_MAP_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
    private final String OPEN_WEATHER_MAP_URL = "https://api.openweathermap.org/";
    private final String UNITS = "metric";
    private final int OPEN_WEATHER_COD_GOOD = 200;
    private onShowWeather listener;

    public OpenWeatherRetrofit(onShowWeather listener) {
        this.listener = listener;
        initRetorfit();
    }

    public interface onShowWeather {
        void onShowCity(City city);

        void onShowError(Throwable t);

        void onSetIcon(int icon);

        void onSetImageIcon(int imageResource);
    }

    private void initRetorfit() {
        Retrofit retrofit = new Retrofit.Builder()
                // Базовая часть адреса
                .baseUrl(OPEN_WEATHER_MAP_URL)
                // Конвертер, необходимый для преобразования JsonData в объекты
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Создаем объект, при помощи которого будем выполнять запросы
        iOpenWeather = retrofit.create(IOpenWeather.class);
    }

    public void requestRetrofit(String city, final int position) {
        iOpenWeather.loadWeather(city, OPEN_WEATHER_KEY_API, UNITS)
                .enqueue(new Callback<WeatherRequest>() {

                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        setWeatherToCity(response, position);
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        listener.onShowError(t);
                    }
                });
    }

    private void setWeatherToCity(Response<WeatherRequest> response, int position) {
        if (response.body() != null && response.body().getCod() == OPEN_WEATHER_COD_GOOD) {
            City city = CityEmmiter.getCities().get(position);
            city.setTemperature((int) response.body().getMain().getTemp());
            city.setHumidity((int) response.body().getMain().getHumidity());
            city.setPressure((int) response.body().getMain().getPressure());
            city.setWind((int) response.body().getWind().getSpeed());
            city.setCountryCode(response.body().getSys().getCountry().toLowerCase());
            setWeatherIcon(response.body().getWeathers()[0].getId(), response.body().getSys().getSunrise() * 1000, response.body().getSys().getSunset() * 1000);
            listener.onShowCity(city);
        } else {
            String error = "";
            if (response.body() == null) {
                error = "Response.body = null";
            } else if (response.body().getCod() != OPEN_WEATHER_COD_GOOD) {
                error = "Cod=" + response.body().getCod();
            }
            listener.onShowError(new Throwable(error));
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        int icon = 0;

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = R.string.weather_sunny;
                listener.onSetImageIcon(R.drawable.appbar_day);
            } else {
                icon = R.string.weather_clear_night;
                listener.onSetImageIcon(R.drawable.appbar_night);
            }

        } else {
            Log.d("Json_id", "id" + id);
            switch (id) {
                case 2: {
                    icon = R.string.weather_thunder;
                    break;
                }
                case 3: {
                    icon = R.string.weather_drizzle;
                    break;
                }
                case 5: {
                    icon = R.string.weather_rainy;
                    break;
                }
                case 6: {
                    icon = R.string.weather_snowy;
                    break;
                }
                case 7: {
                    icon = R.string.weather_foggy;
                    break;
                }
                case 8: {
                    icon = R.string.weather_cloudy;
                    break;
                }
                default:
                    break;

            }
        }
        listener.onSetIcon(icon);
    }

}
