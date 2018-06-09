package silantyevmn.ru.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by silan on 27.05.2018.
 */

public class DetailsFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView tvDate;
    private TextView tvCity;

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
        boolean isHumidity= prefs.getBoolean(Keys.KEY_HUMIDITY,Keys.HUMIDITY_DEFAULT);
        boolean isPressure = prefs.getBoolean(Keys.KEY_PRESSURE, Keys.PRESSURE_DEFAULT);
        boolean isWind = prefs.getBoolean(Keys.KEY_WIND, Keys.WIND_DEFAULT);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        City city = CityEmmiter.getCities().get(position);
        ArrayList<City> cities = new ArrayList<>();
        city.setCurrentDate(Calendar.getInstance());
        city.setIsHumidity(isHumidity);
        city.setIsPressure(isPressure);
        city.setIsWind(isWind);
        cities.add(city);

        tvCity.setText(city.getName());
        tvDate.setText(city.getCurrentDate("EEEE, dd MMM yyyy, HH:mm"));
        DetailsRecyclerAdapter adapter = new DetailsRecyclerAdapter(cities, R.layout.fragment_detail_item);
        recyclerView.setAdapter(adapter);
    }

}
