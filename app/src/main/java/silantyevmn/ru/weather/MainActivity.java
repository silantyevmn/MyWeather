package silantyevmn.ru.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WeatherPreferencer weatherPreferencer;
    private TextView textViewDescription;
    private EditText editTextCity;
    private Switch humiditySwitch, pressureSwitch, windSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (CityEmmiter.getCities() == null) {
            // Расчитываем погоду:)
            CityEmmiter.initNewCityParam(MainActivity.this, 5, 30);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherPreferencer = new WeatherPreferencer(MainActivity.this, savedInstanceState);
        // Объявляем и находим ресурсы
        Button button = findViewById(R.id.button_show_description);
        textViewDescription = findViewById(R.id.textview_description);
        editTextCity = findViewById(R.id.edit_text_city);
        humiditySwitch = findViewById(R.id.switch_humidity);
        pressureSwitch = findViewById(R.id.switch_pressure);
        windSwitch = findViewById(R.id.switch_wind);
        // Устанавливаем слушатель нажатий
        button.setOnClickListener(this);
        //если первый запуск,считаваем показатели чекбоксов из памяти
        if (savedInstanceState == null) {
            load();
        }
    }

    @Override
    public void onClick(View view) {
        int position = 0;
        if (view.getId() == R.id.button_show_description) {
            position = CityEmmiter.getPositionFindCity(editTextCity.getText().toString());
            if (position == -1) {
                textViewDescription.setText(R.string.text_city_not_found);
                return;
            }
            showWeatherNewActivity(position);
        }
    }

    private void showWeatherNewActivity(int position) {
        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
        //передаем позицию ид в новую активити
        intent.putExtra(WeatherActivity.EXTRA_POSITION_ID, position);
        //записываем показатели чекбоксов
        CityEmmiter.setIsHumidity(humiditySwitch.isChecked());
        CityEmmiter.setIsPressure(pressureSwitch.isChecked());
        CityEmmiter.setIsWind(windSwitch.isChecked());
        //запускаем активити
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    private void load() {
        //считываем показатели
        if (humiditySwitch != null && pressureSwitch != null && windSwitch != null) {
            humiditySwitch.setChecked(weatherPreferencer.getIsHumidity());
            pressureSwitch.setChecked(weatherPreferencer.getIsPressure());
            windSwitch.setChecked(weatherPreferencer.getIsWind());
        }
    }

    private void save() {
        //записываем показатели
        if (humiditySwitch != null && pressureSwitch != null && windSwitch != null) {
            weatherPreferencer.save(humiditySwitch.isChecked(), pressureSwitch.isChecked(), windSwitch.isChecked());
        }
    }

}
