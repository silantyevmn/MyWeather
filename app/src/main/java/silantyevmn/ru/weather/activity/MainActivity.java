package silantyevmn.ru.weather.activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import silantyevmn.ru.weather.fragment.MainFragment;
import silantyevmn.ru.weather.utils.CityPreference;

import static android.content.DialogInterface.OnClickListener;

public class MainActivity extends AppCompatActivity implements MainFragment.onClickCityListItem, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private CityPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //поддержка векторов для старых девайсов
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
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
        preference = CityPreference.getPreference(null);
        int position = preference.getPosition();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragment_details);
        //проверяем есть в активити фрагмент деталей погоды?
        if (frameLayout != null) {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        //записываем позицию
        preference.setPosition(position);
        // Получаем ссылку на второй фрагмент по ID
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragment_details);
        // если фрагмента не существует
        if (frameLayout == null) {
            // запускаем активность, если город нашелся
            if (position != -1) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(preference.KEY_POSITION, position);
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
            case R.id.item_setting: {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return false;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_tools:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
