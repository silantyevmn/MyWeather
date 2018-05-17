package silantyevmn.ru.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView textViewDescription;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(CityEmmiter.getCities()==null){
            // Расчитываем погоду:)
            CityEmmiter.initNewCityParam(MainActivity.this, 5, 30);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Объявляем и находим ресурсы
        Button button = findViewById(R.id.button_show_description);
        textViewDescription=findViewById(R.id.textview_description);
        spinner = findViewById(R.id.spinner_for_city);
        // Устанавливаем слушатель нажатий
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_show_description) {
            int position = spinner.getSelectedItemPosition();
            String temperature=CityEmmiter.getCities().get(position).getTemperature();
            textViewDescription.setText(temperature);
        }
    }
}
