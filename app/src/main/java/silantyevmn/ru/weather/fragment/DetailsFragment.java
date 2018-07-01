package silantyevmn.ru.weather.fragment;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import silantyevmn.ru.weather.DetailsRecyclerAdapter;
import silantyevmn.ru.weather.R;
import silantyevmn.ru.weather.utils.City;
import silantyevmn.ru.weather.utils.CityEmmiter;
import silantyevmn.ru.weather.utils.CityPreference;
import silantyevmn.ru.weather.utils.json.OpenWeatherRetrofit;

/**
 * Created by silan on 27.05.2018.
 */

public class DetailsFragment extends Fragment implements OpenWeatherRetrofit.onShowWeather {
    private final String FONT = "fonts/weathericons.ttf";
    private LinearLayout layoutProgress;
    private View layoutContainer;
    private RecyclerView recyclerView;
    private TextView tvDate;
    private TextView tvCity;
    private TextView tvTemperature;
    private TextView tvIcon;
    private TextView tvStatus;
    private City city;
    private boolean isHumidity;
    private boolean isPressure;
    private boolean isWind;
    private DetailsRecyclerAdapter adapter;
    private final Handler handler = new Handler();
    private Typeface weatherFont;
    private ProgressBar progressBar;
    private ImageView imageIcon;
    private ImageView imageFlag; //выведем флаг

    //передаем аргументы(позицию) во фрагмент
    public static DetailsFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(CityPreference.KEY_POSITION, position);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //скрываем и показываем контейнеры при загрузке из интернета
    private void setVisibleContainer(boolean flag) {
        if (flag) {
            layoutContainer.setVisibility(View.VISIBLE);
            imageIcon.setVisibility(View.VISIBLE);
            imageFlag.setVisibility(View.VISIBLE);
            layoutProgress.setVisibility(View.GONE);
        } else {
            layoutContainer.setVisibility(View.GONE);
            imageIcon.setVisibility(View.GONE);
            imageFlag.setVisibility(View.GONE);
            layoutProgress.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), FONT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        recyclerView = rootView.findViewById(R.id.recycler);
        tvCity = rootView.findViewById(R.id.text_view_city);
        tvDate = rootView.findViewById(R.id.text_view_time);
        tvStatus = rootView.findViewById(R.id.text_view_info);
        tvTemperature = rootView.findViewById(R.id.text_view_temperature);
        tvIcon = rootView.findViewById(R.id.text_view_icon);
        tvIcon.setTypeface(weatherFont);
        progressBar = rootView.findViewById(R.id.progressBar);
        imageIcon = rootView.findViewById(R.id.image_icon);
        imageFlag=rootView.findViewById(R.id.image_flag);

        //контейнеры
        layoutProgress = rootView.findViewById(R.id.linear_layout_progress);
        layoutContainer = rootView.findViewById(R.id.linear_layout_data);
        //показыаваем контейнер загрузки
        setVisibleContainer(false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //считываем аргументы
        showFragment(getArguments());
    }

    public void showFragment(Bundle bundle) {
        int position = bundle.getInt(CityPreference.KEY_POSITION, CityPreference.POSITION_DEFAULT);
        //запишем позицию
        CityPreference.getPreference(getContext()).setPosition(position);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        isHumidity = prefs.getBoolean(CityPreference.KEY_HUMIDITY, CityPreference.HUMIDITY_DEFAULT);
        isPressure = prefs.getBoolean(CityPreference.KEY_PRESSURE, CityPreference.PRESSURE_DEFAULT);
        isWind = prefs.getBoolean(CityPreference.KEY_WIND, CityPreference.WIND_DEFAULT);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        city = CityEmmiter.getCities().get(position);
        //загружаем данные погоды из интернета
        //Retrofit
        OpenWeatherRetrofit openWeatherRetrofit = new OpenWeatherRetrofit(this);
        openWeatherRetrofit.requestRetrofit(city.getName().toLowerCase(Locale.US), position);
        // пробуем через AsyncTask
        //requestMaker.make(city.getName().toLowerCase(Locale.US));
        //updateWeatherData(city.getName().toLowerCase(Locale.US));

        tvCity.setText(city.getName());
        city.setCurrentDate(Calendar.getInstance());
        tvDate.setText(city.getCurrentDate("EEEE, dd MMM yyyy, HH:mm"));

        //устанавливаем адаптер
        initAdapter(newArrayCity());

    }

    private void initAdapter(ArrayList<City> cities) {
        adapter = new DetailsRecyclerAdapter(cities, R.layout.fragment_detail_item);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<City> newArrayCity() {
        ArrayList<City> cities = new ArrayList<>();
        city.setCurrentDate(Calendar.getInstance());
        city.setIsHumidity(isHumidity);
        city.setIsPressure(isPressure);
        city.setIsWind(isWind);
        cities.add(city);
        return cities;
    }

    /*//загрузка данных из интернета
    private void updateWeatherData(final String city) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject json = JsonData.getJSONData(getContext(), city);
                if (json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showError(getString(R.string.error_json_null));
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }).start();
    }

    private void renderWeather(JSONObject json) {
        Log.d("Json", json.toString());
        try {
            String name = json.getString("name").toLowerCase();
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            //tvDescription.setText(details.getString("description"));
            city.setTemperature(main.getInt("temp"));
            tvTemperature.setText(city.getTemperature(getContext()));
            city.setPressure(main.getInt("pressure"));
            city.setHumidity(main.getInt("humidity"));
            JSONObject wind = json.getJSONObject("wind");
            city.setWind(wind.getInt("speed"));
            //найдем и покажем иконку погоды
            setWeatherIcon(details.getInt("id"), json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

            initAdapter(newArrayCity());
            setVisibleContainer(true);
        } catch (Exception e) {
            showError(e.getMessage().toString());
        }

    }*/


    private void showError(String text) {
        progressBar.setVisibility(View.GONE);
        tvStatus.setText(text);
    }

    @Override
    public void onShowCity(final City city) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                tvTemperature.setText(city.getTemperature(tvTemperature.getHint().toString()));
                //todo picasso flag show
                //http://flagpedia.net/data/flags/normal/ru.png

                Picasso
                        .with(getContext())
                        .load("http://flagpedia.net/data/flags/normal/"+city.getCountryCode()+".png")
                        .into(imageFlag);

                initAdapter(newArrayCity());
                setVisibleContainer(true);
            }
        });

    }

    @Override
    public void onShowError(final Throwable t) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                showError(t.getMessage());
            }
        });


    }

    @Override
    public void onSetIcon(final int icon) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                tvIcon.setText(icon);
                //tvIcon.setText(R.string.weather_test);
            }
        });

    }

    @Override
    public void onSetImageIcon(final int imageResource) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageIcon.setImageResource(imageResource);
            }
        });

    }
}
