package silantyevmn.ru.weather;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        //считываем значения из активити
        String currentCity = getIntent().getStringExtra(WeatherPreferencer.KEY_CURRENT_CITY);
        boolean isHumidity = getIntent().getBooleanExtra(WeatherPreferencer.KEY_HUMIDITY, WeatherPreferencer.SWITCH_HUMIDITY_DEFAULT);
        boolean isPressure = getIntent().getBooleanExtra(WeatherPreferencer.KEY_PRESSURE, WeatherPreferencer.SWITCH_PRESSURE_DEFAULT);
        boolean isWind = getIntent().getBooleanExtra(WeatherPreferencer.KEY_WIND, WeatherPreferencer.SWITCH_WIND_DEFAULT);
        //WeatherFragment weatherFragment= (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_weather);
        WeatherFragment weatherFragment = WeatherFragment.newInstance(currentCity, isHumidity, isPressure, isWind);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_weather, weatherFragment)
                .commit();
    }
}
