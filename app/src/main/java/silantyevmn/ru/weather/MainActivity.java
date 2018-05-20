package silantyevmn.ru.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewDescription;
    private EditText editTextCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (CityEmmiter.getCities() == null) {
            // Расчитываем погоду:)
            CityEmmiter.initNewCityParam(MainActivity.this, 5, 30);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Объявляем и находим ресурсы
        Button button = findViewById(R.id.button_show_description);
        textViewDescription = findViewById(R.id.textview_description);
        editTextCity = findViewById(R.id.edit_text_city);
        editTextCity.requestFocus();
        // Устанавливаем слушатель нажатий
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_show_description) {

            int position = CityEmmiter.getPositionFindCity(editTextCity.getText().toString());
            if(position==-1){
                textViewDescription.setText("Город не найден");
                return;
            }
            String temperature = CityEmmiter.getCities().get(position).getTemperature();
            textViewDescription.setText(temperature);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
