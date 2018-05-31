package silantyevmn.ru.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements MainFragment.onSelectedButtonListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (CityEmmiter.getCities() == null) {
            // Расчитываем погоду:)
            CityEmmiter.initNewCityParam(MainActivity.this, 5, 30);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // запускаем фрагменты и начинаем транзакцию
        if (savedInstanceState == null) {
           /* MainFragment fragment1 = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_main, fragment1)
                    .commit();*/
            FrameLayout fragment2 = findViewById(R.id.fragment_weather);
            WeatherPreferencer weatherPreferencer = WeatherPreferencer.getInstance(MainActivity.this);
            if (fragment2 != null) {
                WeatherFragment weatherFragment = WeatherFragment.newInstance(
                        weatherPreferencer.getEditTextCity(),
                        weatherPreferencer.getIsHumidity(),
                        weatherPreferencer.getIsPressure(),
                        weatherPreferencer.getIsWind());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_weather, weatherFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onClickButton(String currentCity, boolean isHumidity, boolean isPressure, boolean isWind) {
        // Получаем ссылку на второй фрагмент по ID
        FrameLayout fragment = findViewById(R.id.fragment_weather);
        // если фрагмента не существует
        if (fragment == null) {
            // запускаем активность, если город нашелся
            if (CityEmmiter.getPositionFindCity(currentCity) != -1) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                intent.putExtra(WeatherPreferencer.KEY_CURRENT_CITY, currentCity);
                intent.putExtra(WeatherPreferencer.KEY_HUMIDITY, isHumidity);
                intent.putExtra(WeatherPreferencer.KEY_PRESSURE, isPressure);
                intent.putExtra(WeatherPreferencer.KEY_WIND, isWind);
                startActivity(intent);
            }
        } else {
            // Выводим 2-фрагмент
            WeatherFragment weatherFragment = WeatherFragment.newInstance(currentCity, isHumidity, isPressure, isWind);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_weather, weatherFragment)
                    .commit();
        }
    }

}
