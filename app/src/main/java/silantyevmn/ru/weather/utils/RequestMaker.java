package silantyevmn.ru.weather.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

// Делатель запросов (класс умеющий запрашивать страницы)
public class RequestMaker {
    private final String OPEN_WEATHER_MAP_API = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
    private static final String KEY = "14f34cd242746f2d76bb04739d7485fe";
    // Слушатель, при помощи него, отправим обратный вызов о готовности страницы
    private OnRequestListener listener;

    // В конструкторе примем слушателя, в дальнейшем его передадим асинхронной задаче
    public RequestMaker(OnRequestListener onRequestListener){
        listener = onRequestListener;
    }

    // Сделать запрос
    public void make(String city) {
        //всавляем в запрос город
        String uri=(String.format(OPEN_WEATHER_MAP_API, city, KEY));
        // создаем объект асинхронной задачи (передаем ей слушателя)
        Requester requester = new Requester(listener);
        // Запускаем асинхронную задачу
        requester.execute(uri);
    }

    // Интерфейс слушателя с методами обратного вызова
    public interface OnRequestListener {
        void onStatusProgress(String updateProgress);   // Вызов для обновления прогресса
        void onComplete(String result);                 // Вызов при завершении обработки
    }

    // AsyncTask это обертка для выполнения потока в фоне.
    // Начальные и конечные методы работают в потоке UI, а основной метод расчета работает в фоне
    private static class Requester extends AsyncTask<String, String, String> {
        private OnRequestListener listener;
        Requester(OnRequestListener listener) {
            this.listener = listener;
        }

        // Обновление прогресса, работает в основном потоке UI
        @Override
        protected void onProgressUpdate(String... strings) {
            listener.onStatusProgress(strings[0]);
        }

        // Выполнить таск в фоновом потоке
        @Override
        protected String doInBackground(String... strings) {
            return getResourceUri(strings[0]);
        }

        // Выдать результат, работает в основном потоке UI
        @Override
        protected void onPostExecute(String content) {
            listener.onComplete(content);
        }

        // Сама обработка загрузкти страницы
        private String getResourceUri(String uri) {
            HttpsURLConnection urlConnection = null;
            try {
                URL url = new URL(uri); // Указать адрес URI
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                publishProgress("Подготовка данных"); // обновим прогресс
                urlConnection.connect(); // соединиться
                publishProgress("Соединение");// обновим прогресс
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток ввода/вывода
                StringBuilder buf = new StringBuilder(); // здесь будем формировать результат
                publishProgress("Получение данных");// обновим прогресс
                // обработка выходных данных in
                String line = null;
                // читаем все строки из полученных выходных данных
                while ((line = in.readLine()) != null) {
                    buf.append(line);   // добавим еще одрну строку в результат
                    buf.append(System.getProperty("line.separator")); // это перевод карертки
                }
                return buf.toString();

            } catch (Exception e) {
                Log.e("WebBrowser", e.getMessage(), e);
                publishProgress("Ошибка");// обновим прогресс
            } finally {
                if (urlConnection != null) urlConnection.disconnect(); // разъединиться
            }
            return "Ошибка получения URL";
        }
    }
}
