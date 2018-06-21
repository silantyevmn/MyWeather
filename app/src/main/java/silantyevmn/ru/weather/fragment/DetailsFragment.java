package silantyevmn.ru.weather.fragment;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import silantyevmn.ru.weather.DetailsRecyclerAdapter;
import silantyevmn.ru.weather.R;
import silantyevmn.ru.weather.utils.City;
import silantyevmn.ru.weather.utils.CityEmmiter;
import silantyevmn.ru.weather.utils.Keys;
import silantyevmn.ru.weather.utils.SensorManagerData;

/**
 * Created by silan on 27.05.2018.
 */

public class DetailsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private TextView tvDate;
    private TextView tvCity;
    private City city;
    private boolean isHumidity;
    private boolean isPressure;
    private boolean isWind;
    private int countDayWeather = 1; //Показ количество дней по умолчанию
    private SensorManagerData sensorManagerData;
    private DetailsRecyclerAdapter adapter;

    //передаем аргументы(позицию) во фрагмент
    public static DetailsFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(Keys.KEY_POSITION, position);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        recyclerView = rootView.findViewById(R.id.recycler);
        tvCity = rootView.findViewById(R.id.text_view_city);
        tvDate = rootView.findViewById(R.id.text_view_time);

        //устанавливаем сенсоры
        sensorManagerData = new SensorManagerData(getContext());
        // Регистрируем слушатель датчиков
        sensorManagerData.getSensorManager().registerListener(listener, sensorManagerData.getSensorTemperature(), sensorManagerData.getSensorManager().SENSOR_DELAY_NORMAL);
        sensorManagerData.getSensorManager().registerListener(listener, sensorManagerData.getSensorHumidity(), sensorManagerData.getSensorManager().SENSOR_DELAY_NORMAL);

        //добавляем BottomNavigationView
        BottomNavigationView navigation = (BottomNavigationView) rootView.findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //считываем аргументы
        showFragment(getArguments());
    }

    public void showFragment(Bundle bundle) {
        int position = bundle.getInt(Keys.KEY_POSITION, Keys.POSITION_DEFAULT);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        isHumidity = prefs.getBoolean(Keys.KEY_HUMIDITY, Keys.HUMIDITY_DEFAULT);
        isPressure = prefs.getBoolean(Keys.KEY_PRESSURE, Keys.PRESSURE_DEFAULT);
        isWind = prefs.getBoolean(Keys.KEY_WIND, Keys.WIND_DEFAULT);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        city = CityEmmiter.getCities().get(position);
        tvCity.setText(city.getName());
        city.setCurrentDate(Calendar.getInstance());
        tvDate.setText(city.getCurrentDate("EEEE, dd MMM yyyy, HH:mm"));

        //устанавливаем адаптер
        initAdapter(newArrayCity(countDayWeather));

    }

    private void initAdapter(ArrayList<City> cities) {
        adapter = new DetailsRecyclerAdapter(cities, R.layout.fragment_detail_item);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_navigation_menu_day: {
                countDayWeather = 1;
                initAdapter(newArrayCity(countDayWeather));
                return true;
            }
            case R.id.bottom_navigation_menu_week: {
                countDayWeather = 7;
                initAdapter(newArrayCity(countDayWeather));
                return true;
            }
            case R.id.bottom_navigation_menu_month: {
                countDayWeather = 30;
                initAdapter(newArrayCity(countDayWeather));
                return true;
            }
        }
        return false;
    }

    private ArrayList<City> newArrayCity(int count) {
        ArrayList<City> cities = new ArrayList<>();
        city.setCurrentDate(Calendar.getInstance());
        city.setIsHumidity(isHumidity);
        city.setIsPressure(isPressure);
        city.setIsWind(isWind);
        for (int i = 0; i < count; i++) {
            cities.add(city);
        }
        return cities;
    }

    //слушатель датчиков
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.values.length==0){
                return;
            }
            //если датчик температуры
            if (sensorEvent.sensor.equals(sensorManagerData.getSensorTemperature())) {
                sensorManagerData.setTemperature((int) sensorEvent.values[0]);
                // иначе датчик влажности
            } else if (sensorEvent.sensor.equals(sensorManagerData.getSensorHumidity())) {
                sensorManagerData.setHumidity((int) sensorEvent.values[0]);
            }
            //обновляем показатели сенсоров
            updateRecycler(sensorManagerData.getTemperature(), sensorManagerData.getHumidity());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public void updateRecycler(int temperature, int humidity) {
        city.setTemperature(temperature);
        city.setHumidity(humidity);
        initAdapter(newArrayCity(countDayWeather));
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManagerData.getSensorManager().unregisterListener(listener);
    }
}
