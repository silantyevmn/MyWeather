package silantyevmn.ru.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION_ID = "position_id";
    private final String SEPARATOR = ": ";
    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //считываем переданную позицию
        int position = getIntent().getIntExtra(EXTRA_POSITION_ID, 0);
        //иницилизируем класс Сити
        city = CityEmmiter.getCities().get(position);
        //находим текстовые поля устанавливаем и выводим значения
        TextView textViewCityName = findViewById(R.id.text_view_city);
        textViewCityName.setText(textViewCityName.getText().toString() + SEPARATOR + city.getName());
        showScreenValue(R.id.layout_temperature, R.id.text_view_temperature, city.getTemperature(getString(R.string.text_prefix_temperature)));
        if (CityEmmiter.getIsHumidity())
            showScreenValue(R.id.layout_humidity, R.id.text_view_humidity, city.getHumidity(getString(R.string.text_prefix_humidity)));
        if (CityEmmiter.getIsPressure())
            showScreenValue(R.id.layout_pressure, R.id.text_view_pressure, city.getPressure(getString(R.string.text_prefix_pressure)));
        if (CityEmmiter.getIsWind())
            showScreenValue(R.id.layout_wind, R.id.text_view_wind, city.getWind(getString(R.string.text_prefix_wind)));

    }

    private void showScreenValue(int idLayout, int idTextView, String value) {
        LinearLayout linearLayout = findViewById(idLayout);
        linearLayout.setVisibility(View.VISIBLE);
        TextView textView = findViewById(idTextView);
        textView.setText(value);
    }
}
