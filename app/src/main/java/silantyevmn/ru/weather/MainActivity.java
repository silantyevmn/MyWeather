package silantyevmn.ru.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewDescription;
    private Spinner spinner;

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
        spinner = findViewById(R.id.spinner_for_city);
        // Устанавливаем слушатель нажатий
        button.setOnClickListener(this);
        // проверка на первый и сл.запуски
        if (savedInstanceState == null) {
            showTheLoop("Первый запуск");
        } else showTheLoop("последующий запуск");
    }

    @Override
    public void onClick(View view) {
        showTheLoop("onClick");
        if (view.getId() == R.id.button_show_description) {
            int position = spinner.getSelectedItemPosition();
            String temperature = CityEmmiter.getCities().get(position).getTemperature();
            textViewDescription.setText(temperature);
        }
    }

    private void showTheLoop(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        Log.d("MainActivity",msg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showTheLoop("onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        showTheLoop("onRestore");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTheLoop("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        showTheLoop("onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        showTheLoop("onSave");
    }

    @Override
    protected void onStop() {
        super.onStop();
        showTheLoop("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showTheLoop("onDestroy");
    }
}
