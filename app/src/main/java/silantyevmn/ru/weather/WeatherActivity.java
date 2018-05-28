package silantyevmn.ru.weather;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION_ID = "position_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        //считываем переданную позицию
        int position = getIntent().getIntExtra(EXTRA_POSITION_ID, -1);
        if(position!=-1) {
            // подключаем FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            // Получаем ссылку на второй фрагмент по ID
            WeatherFragment weatherFragment = (WeatherFragment)fragmentManager.findFragmentById(R.id.fragment_weather);
            weatherFragment.setArguments(savedInstanceState);
            weatherFragment.setDescription(position);
        }
    }
}
