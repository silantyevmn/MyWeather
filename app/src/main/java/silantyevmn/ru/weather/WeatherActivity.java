package silantyevmn.ru.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION_ID = "position_id";
    private final String SEPARATOR = ": ";
    private City city;
    private WeatherPreferencer weatherPreferencer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //считываем переданную позицию
        int position = getIntent().getIntExtra(EXTRA_POSITION_ID, 0);
        //иницилизируем класс Сити
        city = CityEmmiter.getCities().get(position);
        //находим текстовые поля
        TextView textViewCityName = findViewById(R.id.text_view_city);
        TextView textViewTemperature = findViewById(R.id.text_view_temperature);
        TextView textViewHumidity = findViewById(R.id.text_view_humidity);
        TextView textViewPressure = findViewById(R.id.text_view_pressure);
        TextView textViewWind = findViewById(R.id.text_view_wind);
        //устанавливаем и выводим значения
        showTextView(textViewCityName, city.getName());
        showTextView(textViewTemperature, city.getTemperature(getString(R.string.text_prefix_temperature)));
        if (CityEmmiter.getIsHumidity())
            showTextView(textViewHumidity, city.getHumidity(getString(R.string.text_prefix_humidity)));
        if (CityEmmiter.getIsPressure())
            showTextView(textViewPressure, city.getPressure(getString(R.string.text_prefix_pressure)));
        if (CityEmmiter.getIsWind())
            showTextView(textViewWind, city.getWind(getString(R.string.text_prefix_wind)));

    }

    private void showTextView(TextView v, String value) {
        String text = v.getText().toString();
        v.setVisibility(View.VISIBLE);
        v.setText(text + SEPARATOR + value);
    }
}
