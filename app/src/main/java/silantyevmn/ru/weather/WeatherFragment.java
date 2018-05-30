package silantyevmn.ru.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by silan on 27.05.2018.
 */

public class WeatherFragment extends Fragment {
    private City city;
    private TextView textViewCityName, textViewTemperature, textViewHumidity, textViewPressure, textViewWind;
    private LinearLayout layoutTemperature, layoutHumidity, layoutPressure, layoutWind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        //setRetainInstance(true);
        return initView(rootView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //считываем аргументы
        showFragment(getArguments());
    }

    private View initView(View rootView) {
        layoutTemperature = rootView.findViewById(R.id.layout_temperature);
        layoutHumidity = rootView.findViewById(R.id.layout_humidity);
        layoutPressure = rootView.findViewById(R.id.layout_pressure);
        layoutWind = rootView.findViewById(R.id.layout_wind);

        textViewCityName = rootView.findViewById(R.id.text_view_city);
        textViewTemperature = rootView.findViewById(R.id.text_view_temperature);
        textViewHumidity = rootView.findViewById(R.id.text_view_humidity);
        textViewPressure = rootView.findViewById(R.id.text_view_pressure);
        textViewWind = rootView.findViewById(R.id.text_view_wind);
        return rootView;
    }

    public void showFragment(Bundle bundle) {
        String currentCity = bundle.getString(WeatherPreferencer.KEY_CURRENT_CITY);
        boolean isHumidity = bundle.getBoolean(WeatherPreferencer.KEY_HUMIDITY, WeatherPreferencer.SWITCH_HUMIDITY_DEFAULT);
        boolean isPressure = bundle.getBoolean(WeatherPreferencer.KEY_PRESSURE, WeatherPreferencer.SWITCH_PRESSURE_DEFAULT);
        boolean isWind = bundle.getBoolean(WeatherPreferencer.KEY_WIND, WeatherPreferencer.SWITCH_WIND_DEFAULT);
        if (currentCity == null) {
            //заглушка
        } else {
            //ищем город в базе
            int position = CityEmmiter.getPositionFindCity(currentCity);
            //если не нашли город в базе
            if (position == -1) {
                city = new City(currentCity, 0, 0, 0, 0);
            } else {
                //иницилизируем класс Сити,если найден город
                city = CityEmmiter.getCities().get(position);
            }
            //в текстовые поля устанавливаем и выводим значения
            textViewCityName.setText(city.getName());
            showCityDetail(layoutTemperature, textViewTemperature, city.getTemperature(getString(R.string.text_prefix_temperature)), View.VISIBLE);
            if (isHumidity) {
                showCityDetail(layoutHumidity, textViewHumidity, city.getHumidity(getString(R.string.text_prefix_humidity)), View.VISIBLE);
            } else
                showCityDetail(layoutHumidity, textViewHumidity, city.getHumidity(getString(R.string.text_prefix_humidity)), View.GONE);
            if (isPressure) {
                showCityDetail(layoutPressure, textViewPressure, city.getPressure(getString(R.string.text_prefix_pressure)), View.VISIBLE);
            } else
                showCityDetail(layoutPressure, textViewPressure, city.getPressure(getString(R.string.text_prefix_pressure)), View.GONE);
            if (isWind) {
                showCityDetail(layoutWind, textViewWind, city.getWind(getString(R.string.text_prefix_wind)), View.VISIBLE);
            } else
                showCityDetail(layoutWind, textViewWind, city.getWind(getString(R.string.text_prefix_wind)), View.GONE);
        }
    }

    private void showCityDetail(LinearLayout layout, TextView textView, String value, int visible) {
        layout.setVisibility(visible);
        textView.setText(value);
    }

    //передаем аргументы(позицию) во фрагмент
    public static WeatherFragment newInstance(String currentCity, boolean isHumidity, boolean isPressure, boolean isWind) {
        Bundle args = new Bundle();
        args.putString(WeatherPreferencer.KEY_CURRENT_CITY, currentCity);
        args.putBoolean(WeatherPreferencer.KEY_HUMIDITY, isHumidity);
        args.putBoolean(WeatherPreferencer.KEY_PRESSURE, isPressure);
        args.putBoolean(WeatherPreferencer.KEY_WIND, isWind);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
