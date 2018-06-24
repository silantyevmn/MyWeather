package silantyevmn.ru.weather.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import silantyevmn.ru.weather.R;
import silantyevmn.ru.weather.fragment.DetailsFragment;
import silantyevmn.ru.weather.utils.CityPreference;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        //добавляем в АппБар кнопку назад
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        //считываем значения из активити
        int position = getIntent().getIntExtra(CityPreference.KEY_POSITION,CityPreference.POSITION_DEFAULT);
        //наполняем и показываем фрагмент
        DetailsFragment fragment = DetailsFragment.newInstance(position);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_details, fragment)
                .commit();
    }

}
