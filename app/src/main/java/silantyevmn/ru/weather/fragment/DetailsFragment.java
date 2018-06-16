package silantyevmn.ru.weather.fragment;

import android.content.SharedPreferences;
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
import silantyevmn.ru.weather.utils.City;
import silantyevmn.ru.weather.utils.CityEmmiter;
import silantyevmn.ru.weather.DetailsRecyclerAdapter;
import silantyevmn.ru.weather.utils.Keys;
import silantyevmn.ru.weather.R;

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
        tvCity=rootView.findViewById(R.id.text_view_city);
        tvDate = rootView.findViewById(R.id.text_view_time);
        //добавляем BottomNavigationView
        BottomNavigationView navigation=(BottomNavigationView) rootView.findViewById(R.id.bottom_navigation);
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
        isHumidity= prefs.getBoolean(Keys.KEY_HUMIDITY,Keys.HUMIDITY_DEFAULT);
        isPressure = prefs.getBoolean(Keys.KEY_PRESSURE, Keys.PRESSURE_DEFAULT);
        isWind = prefs.getBoolean(Keys.KEY_WIND, Keys.WIND_DEFAULT);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        city = CityEmmiter.getCities().get(position);
        tvCity.setText(city.getName());
        city.setCurrentDate(Calendar.getInstance());
        tvDate.setText(city.getCurrentDate("EEEE, dd MMM yyyy, HH:mm"));
        newArrayCity(1);
        /*DetailsRecyclerAdapter adapter = new DetailsRecyclerAdapter(cities, R.layout.fragment_detail_item);
        recyclerView.setAdapter(adapter);*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bottom_navigation_menu_day:{
                newArrayCity(1);
                return true;
            }
            case R.id.bottom_navigation_menu_week:{
                newArrayCity(7);
                return true;
            }
            case R.id.bottom_navigation_menu_month:{
                newArrayCity(30);
                return true;
            }
        }
        return false;
    }

    private void newArrayCity(int count) {
        ArrayList<City> cities = new ArrayList<>();
        city.setCurrentDate(Calendar.getInstance());
        city.setIsHumidity(isHumidity);
        city.setIsPressure(isPressure);
        city.setIsWind(isWind);
        for (int i = 0; i < count; i++) {
            cities.add(city);
        }
        DetailsRecyclerAdapter adapter = new DetailsRecyclerAdapter(cities, R.layout.fragment_detail_item);
        recyclerView.setAdapter(adapter);
    }
}
