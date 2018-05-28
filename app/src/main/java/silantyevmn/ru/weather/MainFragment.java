package silantyevmn.ru.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by silan on 27.05.2018.
 */

public class MainFragment extends Fragment implements View.OnClickListener {
    private WeatherPreferencer weatherPreferencer;
    private TextView textViewDescription;
    private EditText editTextCity;
    private Switch humiditySwitch, pressureSwitch, windSwitch;
    private onSelectedButtonListener listener;

    public interface onSelectedButtonListener {
        public void onSelectedButton(int position);

        public void onSelectedSwitch(boolean humiditySwitch, boolean pressureSwitch, boolean windSwitch);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listener = (onSelectedButtonListener) getActivity();
        weatherPreferencer = new WeatherPreferencer(getActivity(), savedInstanceState);
        // Объявляем и находим ресурсы
        Button button = rootView.findViewById(R.id.button_show_description);
        textViewDescription = rootView.findViewById(R.id.textview_description);
        editTextCity = rootView.findViewById(R.id.edit_text_city);
        humiditySwitch = rootView.findViewById(R.id.switch_humidity);
        pressureSwitch = rootView.findViewById(R.id.switch_pressure);
        windSwitch = rootView.findViewById(R.id.switch_wind);
        // Устанавливаем слушатель нажатий
        button.setOnClickListener(this);
        humiditySwitch.setOnClickListener(this);
        pressureSwitch.setOnClickListener(this);
        windSwitch.setOnClickListener(this);
        if (savedInstanceState == null) {
            load();
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        int position = 0;
        if (view.getId() == R.id.button_show_description) {
            position = CityEmmiter.getPositionFindCity(editTextCity.getText().toString());
            if (position == -1) {
                textViewDescription.setText(R.string.text_city_not_found);
                return;
            }
            listener.onSelectedButton(position);
        }
        if (view.getId() == R.id.switch_pressure || view.getId() == R.id.switch_humidity || view.getId() == R.id.switch_wind) {
            listener.onSelectedSwitch(humiditySwitch.isChecked(), pressureSwitch.isChecked(), windSwitch.isChecked());
            save();
        }
    }

    private void load() {
        //считываем показатели
        if (humiditySwitch != null && pressureSwitch != null && windSwitch != null) {
            humiditySwitch.setChecked(weatherPreferencer.getIsHumidity());
            pressureSwitch.setChecked(weatherPreferencer.getIsPressure());
            windSwitch.setChecked(weatherPreferencer.getIsWind());
            //временно не успеваю, позже переделаю
            CityEmmiter.setIsHumidity(weatherPreferencer.getIsHumidity());
            CityEmmiter.setIsPressure(weatherPreferencer.getIsPressure());
            CityEmmiter.setIsWind(weatherPreferencer.getIsWind());
        }
    }

    private void save() {
        //записываем показатели
        if (humiditySwitch != null && pressureSwitch != null && windSwitch != null) {
            weatherPreferencer.save(humiditySwitch.isChecked(), pressureSwitch.isChecked(), windSwitch.isChecked());
        }
    }

}
