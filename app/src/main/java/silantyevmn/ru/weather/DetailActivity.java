package silantyevmn.ru.weather;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        //считываем значения из активити
        String currentCity = getIntent().getStringExtra(DataPreferences.KEY_CURRENT_CITY);
        boolean isHumidity = getIntent().getBooleanExtra(DataPreferences.KEY_HUMIDITY, DataPreferences.HUMIDITY_DEFAULT);
        boolean isPressure = getIntent().getBooleanExtra(DataPreferences.KEY_PRESSURE, DataPreferences.PRESSURE_DEFAULT);
        boolean isWind = getIntent().getBooleanExtra(DataPreferences.KEY_WIND, DataPreferences.WIND_DEFAULT);
        //наполняем и показываем фрагмент
        DetailsFragment fragment = DetailsFragment.newInstance(currentCity, isHumidity, isPressure, isWind);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_details, fragment)
                .commit();
    }
}
