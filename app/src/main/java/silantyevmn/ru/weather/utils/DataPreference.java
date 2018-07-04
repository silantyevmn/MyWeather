package silantyevmn.ru.weather.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by silan on 04.07.2018.
 */

public class DataPreference {

    public static String getCurrentDate(String format) {
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(Calendar.getInstance().getTime());
    }
}
