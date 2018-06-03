package silantyevmn.ru.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements ListFragment.onClickCityListItem {
    private MenuItem itemHumidity, itemPressure, itemWind;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            if (CityEmmiter.getCities() == null) {
                // Расчитываем погоду:)
                String[] arrCity = getResources().getStringArray(R.array.city_selection);
                CityEmmiter.initNewCityParam(arrCity);
            }
        }

        int position = (savedInstanceState == null) ? Keys.POSITION_DEFAULT : Keys.getPosition(this);
        FrameLayout fragment2 = (FrameLayout) findViewById(R.id.fragment_details);
        //проверяем есть в активити фрагмент деталей погоды?
        if (fragment2 != null) {
            //если есть, то показываем его
            DetailsFragment detailsFragment = DetailsFragment.newInstance(
                    //передаем во фрагмент значение из памяти приложения
                    position,
                    Keys.getIsHumidity(this),
                    Keys.getIsPressure(this),
                    Keys.getIsWind(this));
            //запускаем транзакцию и добавляем фрагмент
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_details, detailsFragment)
                    .commit();
        }
    }

    @Override
    public void onClickListItem(int position) {
        this.position = position;
        save();
        // Получаем ссылку на второй фрагмент по ID
        FrameLayout fragment = (FrameLayout) findViewById(R.id.fragment_details);
        // если фрагмента не существует
        if (fragment == null) {
            // запускаем активность, если город нашелся
            if (position != -1) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(Keys.KEY_POSITION, position);
                intent.putExtra(Keys.KEY_HUMIDITY, itemHumidity.isChecked());
                intent.putExtra(Keys.KEY_PRESSURE, itemPressure.isChecked());
                intent.putExtra(Keys.KEY_WIND, itemWind.isChecked());
                startActivity(intent);
            }
        } else {
            // Выводим 2-фрагмент
            DetailsFragment detailsFragment = DetailsFragment.newInstance(position, itemHumidity.isChecked(), itemPressure.isChecked(), itemWind.isChecked());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_details, detailsFragment)
                    .commit();
        }
    }

    //иницилизируем боковое меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        itemHumidity = menu.findItem(R.id.item_humidity);
        itemPressure = menu.findItem(R.id.item_pressure);
        itemWind = menu.findItem(R.id.item_wind);
        load();
        return super.onCreateOptionsMenu(menu);
    }

    //обрабатываем нажатия на боковое меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_humidity: {
                itemClickChecked(item, item.isChecked());
                return false;
            }
            case R.id.item_pressure: {
                itemClickChecked(item, item.isChecked());
                return false;
            }
            case R.id.item_wind: {
                itemClickChecked(item, item.isChecked());
                return false;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //устанавливаем значение и сохраняем показатели чекбоксов в боковом меню
    private void itemClickChecked(MenuItem item, boolean isChecked) {
        item.setChecked(!isChecked);
        save();
    }

    private void load() {
        itemHumidity.setChecked(Keys.getIsHumidity(this));
        itemPressure.setChecked(Keys.getIsPressure(this));
        itemWind.setChecked(Keys.getIsWind(this));
    }

    private void save() {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt(Keys.KEY_POSITION, position);
        editor.putBoolean(Keys.KEY_HUMIDITY, itemHumidity.isChecked());
        editor.putBoolean(Keys.KEY_PRESSURE, itemPressure.isChecked());
        editor.putBoolean(Keys.KEY_WIND, itemWind.isChecked());
        editor.apply();
    }

}
