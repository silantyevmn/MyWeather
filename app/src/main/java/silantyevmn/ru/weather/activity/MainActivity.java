package silantyevmn.ru.weather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import silantyevmn.ru.weather.R;
import silantyevmn.ru.weather.fragment.DetailsFragment;
import silantyevmn.ru.weather.fragment.ListFragment;
import silantyevmn.ru.weather.utility.CityEmmiter;
import silantyevmn.ru.weather.utility.Keys;

public class MainActivity extends AppCompatActivity implements ListFragment.onClickCityListItem {
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
                    position);
            //запускаем транзакцию и добавляем фрагмент
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_details, detailsFragment)
                    .commit();
        }
    }

    @Override
    public void onClickListItem(int position) {
        save(position);
        // Получаем ссылку на второй фрагмент по ID
        FrameLayout fragment = (FrameLayout) findViewById(R.id.fragment_details);
        // если фрагмента не существует
        if (fragment == null) {
            // запускаем активность, если город нашелся
            if (position != -1) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(Keys.KEY_POSITION, position);
                startActivity(intent);
            }
        } else {
            // Выводим 2-фрагмент
            DetailsFragment detailsFragment = DetailsFragment.newInstance(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_details, detailsFragment)
                    .commit();
        }
    }

    //иницилизируем боковое меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        //MenuItem itemSetting=menu.findItem(R.id.item_setting);
        return super.onCreateOptionsMenu(menu);
    }

    //обрабатываем нажатия на боковое меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_setting:{
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                return false;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save(int position) {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt(Keys.KEY_POSITION, position);
        editor.apply();
    }

}
