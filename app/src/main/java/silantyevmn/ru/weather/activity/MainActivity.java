package silantyevmn.ru.weather.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import silantyevmn.ru.weather.R;
import silantyevmn.ru.weather.fragment.DetailsFragment;
import silantyevmn.ru.weather.fragment.ListFragment;
import silantyevmn.ru.weather.utility.CityEmmiter;
import silantyevmn.ru.weather.utility.Keys;

import static android.content.DialogInterface.*;

public class MainActivity extends AppCompatActivity implements ListFragment.onClickCityListItem,NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //поддержка векторов для старых девайсов
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        if (savedInstanceState == null) {
            if (CityEmmiter.getCities() == null) {
                // Расчитываем погоду:)
                String[] arrCity = getResources().getStringArray(R.array.city_selection);
                CityEmmiter.initNewCityParam(arrCity);
            }
        }
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //

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

    //обработка клавиши назад
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(R.string.dialog_title_exits);
            builder.setPositiveButton(R.string.dialog_button_exit, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.finish();
                }
            });
            builder.setNegativeButton(R.string.dialog_button_cancel, null);
            builder.create().show();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_tools: startActivity(new Intent(MainActivity.this,SettingActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}