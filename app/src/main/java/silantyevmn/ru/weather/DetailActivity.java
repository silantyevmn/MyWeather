package silantyevmn.ru.weather;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        //считываем значения из активити
        int position = getIntent().getIntExtra(Keys.KEY_POSITION, Keys.POSITION_DEFAULT);
        //наполняем и показываем фрагмент
        DetailsFragment fragment = DetailsFragment.newInstance(position);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_details, fragment)
                .commit();
    }

}
