package silantyevmn.ru.weather.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.List;
import java.util.Locale;

import silantyevmn.ru.weather.DetailsRecyclerAdapter;
import silantyevmn.ru.weather.R;
import silantyevmn.ru.weather.database.CityEntity;
import silantyevmn.ru.weather.database.DataBaseSource;
import silantyevmn.ru.weather.utils.CityPreference;
import silantyevmn.ru.weather.utils.DataPreference;
import silantyevmn.ru.weather.utils.json.retrofit.OpenWeatherRetrofit;

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
    private DetailsRecyclerAdapter adapter;
    private final Handler handler = new Handler();
    private Typeface weatherFont;
    private ProgressBar progressBar;
    private ImageView imageAppBar;
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
            imageAppBar.setVisibility(View.VISIBLE);
            imageFlag.setVisibility(View.VISIBLE);
            layoutProgress.setVisibility(View.GONE);
        } else {
            layoutContainer.setVisibility(View.GONE);
            imageAppBar.setVisibility(View.GONE);
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
        imageAppBar = rootView.findViewById(R.id.image_icon);
        imageFlag = rootView.findViewById(R.id.image_flag);

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
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        //считываем с базы
        CityEntity cityEntity= DataBaseSource.getListCityEntity().get(position);
        //city = CityEmmiter.getCities().get(position);
        //загружаем данные погоды из интернета
        //Retrofit
        OpenWeatherRetrofit openWeatherRetrofit = new OpenWeatherRetrofit(this,cityEntity);
        openWeatherRetrofit.requestRetrofitOneDay(cityEntity.getName().toLowerCase(Locale.US), position);
        // пробуем через AsyncTask
        //requestMaker.make(city.getName().toLowerCase(Locale.US));
        //updateWeatherData(city.getName().toLowerCase(Locale.US));

        tvCity.setText(cityEntity.getName());
        tvDate.setText(DataPreference.getCurrentDate("EEEE, dd MMM yyyy, HH:mm"));

        //устанавливаем адаптер
        initAdapter(initList(cityEntity));

    }

    private List<CityEntity> initList(CityEntity cityEntity) {
        List<CityEntity> cityEntities=new ArrayList<>();
        cityEntities.add(cityEntity);
        return cityEntities;
    }

    private void initAdapter(List<CityEntity> cities) {
        adapter = new DetailsRecyclerAdapter(cities, R.layout.fragment_detail_item);
        recyclerView.setAdapter(adapter);
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
    public void onShowCity(final CityEntity city) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                tvTemperature.setText(city.getTemperature(tvTemperature.getHint().toString()));
                //todo picasso flag show
                //http://flagpedia.net/data/flags/normal/ru.png

                Picasso
                        .with(getContext())
                        .load("http://flagpedia.net/data/flags/normal/" + city.getCountryCode() + ".png")
                        .into(imageFlag);

                initAdapter(initList(city));
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
            }
        });

    }

    @Override
    public void onSetImageAppBar(final int imageResource) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageAppBar.setImageResource(imageResource);
            }
        });

    }
}
