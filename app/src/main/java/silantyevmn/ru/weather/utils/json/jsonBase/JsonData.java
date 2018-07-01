package silantyevmn.ru.weather.utils.json.jsonBase;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by silan on 23.06.2018.
 */

public class JsonData {
    //private static final String OPEN_WEATHER_MAP_API="https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
    //private static final String KEY_CITY="x-api-key";
    private static final String KEY = "14f34cd242746f2d76bb04739d7485fe";
    private static final String RESPONSE = "cod";
    private static final String NEW_LINE = "\n";
    private static final int SERVER_CODE_OK = 200;

    //возвращаем JsonData или NULL
    public static JSONObject getJSONData(Context context, String city) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city, KEY));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//            connection.addRequestProperty(KEY0,KEY_CITY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;
            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append(NEW_LINE);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(rawData.toString());
            if (jsonObject.getInt(RESPONSE) == SERVER_CODE_OK) {
                return jsonObject;
            } else return null;

        } catch (Exception e) {
            return null; //обработка ошибки
        }
    }


}
