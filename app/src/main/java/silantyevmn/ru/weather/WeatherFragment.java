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
    private TextView textViewCityName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        textViewCityName = rootView.findViewById(R.id.text_view_city);
        return rootView;
    }

    public void setDescription(int position) {
        //иницилизируем класс Сити
        city = CityEmmiter.getCities().get(position);
        //в текстовые поля устанавливаем и выводим значения
        textViewCityName.setText(city.getName());
        showScreenValue(R.id.layout_temperature, R.id.text_view_temperature, city.getTemperature(getString(R.string.text_prefix_temperature)), View.VISIBLE);
        if (CityEmmiter.getIsHumidity()) {
            showScreenValue(R.id.layout_humidity, R.id.text_view_humidity, city.getHumidity(getString(R.string.text_prefix_humidity)), View.VISIBLE);
        } else
            showScreenValue(R.id.layout_humidity, R.id.text_view_humidity, city.getHumidity(getString(R.string.text_prefix_humidity)), View.GONE);
        if (CityEmmiter.getIsPressure()) {
            showScreenValue(R.id.layout_pressure, R.id.text_view_pressure, city.getPressure(getString(R.string.text_prefix_pressure)), View.VISIBLE);
        } else
            showScreenValue(R.id.layout_pressure, R.id.text_view_pressure, city.getPressure(getString(R.string.text_prefix_pressure)), View.GONE);
        if (CityEmmiter.getIsWind()) {
            showScreenValue(R.id.layout_wind, R.id.text_view_wind, city.getWind(getString(R.string.text_prefix_wind)), View.VISIBLE);
        } else
            showScreenValue(R.id.layout_wind, R.id.text_view_wind, city.getWind(getString(R.string.text_prefix_wind)), View.GONE);

    }

    private void showScreenValue(int idLayout, int idTextView, String value, int visible) {
        LinearLayout linearLayout = getView().findViewById(idLayout);
        linearLayout.setVisibility(visible);
        TextView textView = getView().findViewById(idTextView);
        textView.setText(value);
    }
}
