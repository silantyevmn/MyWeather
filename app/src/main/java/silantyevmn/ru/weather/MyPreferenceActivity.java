package silantyevmn.ru.weather;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by silan on 08.06.2018.
 */

public class MyPreferenceActivity extends PreferenceActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getBaseContext(),
                R.xml.settings, false);
        addPreferencesFromResource(R.xml.settings);
    }
}
