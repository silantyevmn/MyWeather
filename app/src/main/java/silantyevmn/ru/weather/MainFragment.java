package silantyevmn.ru.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
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
    private String TAG_TEST_FRAGMENT ="test_fragment";

    public interface onSelectedButtonListener {
        public void onClickButton(String city, boolean isHumidity, boolean isPressure, boolean isWind);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //setRetainInstance(true);
        listener = (onSelectedButtonListener) getActivity();
        weatherPreferencer = WeatherPreferencer.getInstance(getActivity());
        // Объявляем и находим ресурсы
        editTextCity = rootView.findViewById(R.id.edit_text_city);
        final Button button = rootView.findViewById(R.id.button_show_description);
        button.setEnabled(!editTextCity.getText().toString().trim().isEmpty());
        textViewDescription = rootView.findViewById(R.id.textview_description);
        humiditySwitch = rootView.findViewById(R.id.switch_humidity);
        pressureSwitch = rootView.findViewById(R.id.switch_pressure);
        windSwitch = rootView.findViewById(R.id.switch_wind);
        // Устанавливаем слушатель нажатий
        button.setOnClickListener(this);
        humiditySwitch.setOnClickListener(this);
        pressureSwitch.setOnClickListener(this);
        windSwitch.setOnClickListener(this);
        editTextCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                button.setEnabled(!editable.toString().trim().isEmpty());
            }
        });
        //добавляем 3-й фрагмент
        FragmentManager childFragmentManager = getChildFragmentManager();
        TestFragment testFragment= (TestFragment) childFragmentManager.findFragmentByTag(TAG_TEST_FRAGMENT);
        if(testFragment==null){
            FragmentTransaction transaction=childFragmentManager.beginTransaction();
            testFragment=new TestFragment();
            transaction.add(R.id.fragment_test,testFragment,TAG_TEST_FRAGMENT);
            transaction.commit();
        }
        if (savedInstanceState == null) {
            //считываем из памяти значения
            load();
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        String currentCity = editTextCity.getText().toString().trim();
        if (view.getId() == R.id.button_show_description) {
            //проверка есть город в базе?
            if (CityEmmiter.getPositionFindCity(currentCity) == -1) {
                textViewDescription.setText(R.string.text_city_not_found);
                //проверка на 2 фрагмент
                if (getActivity().findViewById(R.id.fragment_weather) == null) {
                    //не запускаем 2 активити,если не найден город
                    return;
                }
            }
            listener.onClickButton(currentCity, humiditySwitch.isChecked(), pressureSwitch.isChecked(), windSwitch.isChecked());
        }
        if (view.getId() == R.id.switch_pressure || view.getId() == R.id.switch_humidity || view.getId() == R.id.switch_wind) {
            listener.onClickButton(currentCity, humiditySwitch.isChecked(), pressureSwitch.isChecked(), windSwitch.isChecked());
            save();
        }
    }

    private void load() {
        //считываем показатели
        if (humiditySwitch != null && pressureSwitch != null && windSwitch != null && editTextCity != null) {
            editTextCity.setText(weatherPreferencer.getEditTextCity());
            humiditySwitch.setChecked(weatherPreferencer.getIsHumidity());
            pressureSwitch.setChecked(weatherPreferencer.getIsPressure());
            windSwitch.setChecked(weatherPreferencer.getIsWind());
        }
    }

    private void save() {
        //записываем показатели
        if (humiditySwitch != null && pressureSwitch != null && windSwitch != null && editTextCity != null) {
            weatherPreferencer.save(editTextCity.getText().toString().trim(), humiditySwitch.isChecked(), pressureSwitch.isChecked(), windSwitch.isChecked());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //записываем значение города при перевороте экрана в Bundle
        outState.putString(WeatherPreferencer.KEY_CURRENT_CITY, editTextCity.getText().toString().trim());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        //считываем значения города при перевороте экрана
        if (savedInstanceState != null) {
            editTextCity.setText(savedInstanceState.getString(WeatherPreferencer.KEY_CURRENT_CITY, WeatherPreferencer.CURRENT_CITY_DEFAULT));
        }
    }
}
