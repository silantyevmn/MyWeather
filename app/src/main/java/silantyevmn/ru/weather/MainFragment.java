package silantyevmn.ru.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by silan on 27.05.2018.
 */

public class MainFragment extends Fragment implements View.OnClickListener {
    public interface onSelectedButtonListener {
        void onClickButton(String city, boolean isHumidity, boolean isPressure, boolean isWind);
    }

    private TextView textViewDescription;
    private EditText editTextCity;
    private CheckBox humidityCheckBox, pressureCheckBox, windCheckBox;

    private onSelectedButtonListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (onSelectedButtonListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // надуваем view
        return initView(rootView);
    }

    @NonNull
    private View initView(View rootView) {
        editTextCity = rootView.findViewById(R.id.edit_text_city);
        final Button button = rootView.findViewById(R.id.button_show_description);
        button.setEnabled(!editTextCity.getText().toString().trim().isEmpty());
        textViewDescription = rootView.findViewById(R.id.textview_description);
        humidityCheckBox = rootView.findViewById(R.id.checkbox_humidity);
        pressureCheckBox = rootView.findViewById(R.id.checkbox_pressure);
        windCheckBox = rootView.findViewById(R.id.checkbox_wind);

        // Устанавливаем слушатель нажатий
        button.setOnClickListener(this);
        humidityCheckBox.setOnClickListener(this);
        pressureCheckBox.setOnClickListener(this);
        windCheckBox.setOnClickListener(this);
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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //если первый запуск загружаем значения из sharePreferencer
        if(savedInstanceState==null){
            load();
        }
    }

    @Override
    public void onClick(View view) {
        String currentCity = editTextCity.getText().toString().trim();
        if (view.getId() == R.id.button_show_description) {
            //проверка есть город в базе?
            if (CityEmmiter.getPositionFindCity(currentCity) == -1) {
                textViewDescription.setText(R.string.text_city_not_found);
                //проверка на 2 фрагмент
                if (getActivity().findViewById(R.id.fragment_details) == null) {
                    //не запускаем 2 активити,если не найден город
                    return;
                }
            }
            listener.onClickButton(currentCity, humidityCheckBox.isChecked(), pressureCheckBox.isChecked(), windCheckBox.isChecked());
        }
        if (view.getId() == R.id.checkbox_humidity || view.getId() == R.id.checkbox_pressure || view.getId() == R.id.checkbox_wind) {
            //listener.onClickButton(currentCity, humidityCheckBox.isChecked(), pressureCheckBox.isChecked(), windCheckBox.isChecked());
            save();
        }
    }

    private void load() {
        //считываем показатели
        if (humidityCheckBox != null && pressureCheckBox != null && windCheckBox != null && editTextCity != null) {
            editTextCity.setText(DataPreferences.getEditTextCity(getActivity()));
            humidityCheckBox.setChecked(DataPreferences.getIsHumidity(getActivity()));
            pressureCheckBox.setChecked(DataPreferences.getIsPressure(getActivity()));
            windCheckBox.setChecked(DataPreferences.getIsWind(getActivity()));
        }
    }

    private void save() {
        //записываем показатели
        if (humidityCheckBox != null && humidityCheckBox != null && windCheckBox != null && editTextCity != null) {
            DataPreferences.save(getActivity(), editTextCity.getText().toString().trim(), humidityCheckBox.isChecked(), pressureCheckBox.isChecked(), windCheckBox.isChecked());
        }
    }

}
