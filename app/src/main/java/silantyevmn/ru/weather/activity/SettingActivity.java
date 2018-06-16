package silantyevmn.ru.weather.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import silantyevmn.ru.weather.R;

/**
 * Created by silan on 08.06.2018.
 */

public class SettingActivity extends PreferenceActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getBaseContext(),
                R.xml.settings, false);
        addPreferencesFromResource(R.xml.settings);
    }
}
