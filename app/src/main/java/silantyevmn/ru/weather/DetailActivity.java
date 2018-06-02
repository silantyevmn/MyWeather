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
        int position = getIntent().getIntExtra(Keys.KEY_POSITION, Keys.POSITION_DEFAULT);
        boolean isHumidity = getIntent().getBooleanExtra(Keys.KEY_HUMIDITY, Keys.HUMIDITY_DEFAULT);
        boolean isPressure = getIntent().getBooleanExtra(Keys.KEY_PRESSURE, Keys.PRESSURE_DEFAULT);
        boolean isWind = getIntent().getBooleanExtra(Keys.KEY_WIND, Keys.WIND_DEFAULT);
        //наполняем и показываем фрагмент
        DetailsFragment fragment = DetailsFragment.newInstance(position, isHumidity, isPressure, isWind);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_details, fragment)
                .commit();
    }

}
