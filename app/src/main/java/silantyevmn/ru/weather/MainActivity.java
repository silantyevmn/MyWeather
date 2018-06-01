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
        // если первый запуск
        if (savedInstanceState == null) {
            FrameLayout fragment2 = findViewById(R.id.fragment_details);
            //проверяем есть в активити фрагмент деталей погоды?
            if (fragment2 != null) {
                //если есть, то показываем его
                DetailsFragment detailsFragment = DetailsFragment.newInstance(
                        //передаем во фрагмент значение из памяти приложения
                        DataPreferences.getEditTextCity(this),
                        DataPreferences.getIsHumidity(this),
                        DataPreferences.getIsPressure(this),
                        DataPreferences.getIsWind(this));
                //запускаем транзакцию и добавляем фрагмент
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_details, detailsFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onClickButton(String currentCity, boolean isHumidity, boolean isPressure, boolean isWind) {
        int position=CityEmmiter.getPositionFindCity(currentCity);
        // Получаем ссылку на второй фрагмент по ID
        FrameLayout fragment = findViewById(R.id.fragment_details);
        // если фрагмента не существует
        if (fragment == null) {
            // запускаем активность, если город нашелся
            if (position != -1) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DataPreferences.KEY_CURRENT_CITY, currentCity);
                intent.putExtra(DataPreferences.KEY_HUMIDITY, isHumidity);
                intent.putExtra(DataPreferences.KEY_PRESSURE, isPressure);
                intent.putExtra(DataPreferences.KEY_WIND, isWind);
                startActivity(intent);
            }
        } else {
            // Выводим 2-фрагмент
            DetailsFragment detailsFragment = DetailsFragment.newInstance(currentCity, isHumidity, isPressure, isWind);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_details, detailsFragment)
                    .commit();
        }
    }

}
