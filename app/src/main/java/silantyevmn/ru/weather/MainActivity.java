package silantyevmn.ru.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainFragment.onSelectedButtonListener {
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (CityEmmiter.getCities() == null) {
            // Расчитываем погоду:)
            CityEmmiter.initNewCityParam(MainActivity.this, 5, 30);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSelectedButton(int position) {
        this.position=position;
        // подключаем FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Получаем ссылку на второй фрагмент по ID
        WeatherFragment weatherFragment = (WeatherFragment) fragmentManager.findFragmentById(R.id.fragment_weather);
        // если фрагмента не существует или он невидим
        if (weatherFragment == null || !weatherFragment.isVisible()) {
            // запускаем активность
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            intent.putExtra(WeatherActivity.EXTRA_POSITION_ID, position);
            startActivity(intent);
        } else {
            // Выводим нужную информацию
            weatherFragment.setDescription(position);
        }
    }

    @Override
    public void onSelectedSwitch(boolean humiditySwitch, boolean pressureSwitch, boolean windSwitch) {
        //записываем показатели чекбоксов
        CityEmmiter.setIsHumidity(humiditySwitch);
        CityEmmiter.setIsPressure(pressureSwitch);
        CityEmmiter.setIsWind(windSwitch);

    }
}
