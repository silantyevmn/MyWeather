package silantyevmn.ru.weather.utils.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by silan on 09.07.2018.
 */

public class MyFirebaseInstance extends FirebaseInstanceIdService {
    private final String TAG = "PushIDService";

    @Override
    public void onTokenRefresh() {
        // Получить ключ установки приложения на устройство
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    // Метод отправки ключа в вашу БД
    private void sendRegistrationToServer(String refreshedToken) {
    }

}
