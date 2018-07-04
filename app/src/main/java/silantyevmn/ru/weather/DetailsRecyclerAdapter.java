package silantyevmn.ru.weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import silantyevmn.ru.weather.database.CityEntity;
import silantyevmn.ru.weather.utils.CityPreference;
import silantyevmn.ru.weather.utils.DataPreference;

/**
 * Created by silan on 07.06.2018.
 */

public class DetailsRecyclerAdapter extends RecyclerView.Adapter<DetailsRecyclerAdapter.MyViewHolder> {
    private List<CityEntity> cities;
    private int rLayout;
    private boolean isHumidity;
    private boolean isPressure;
    private boolean isWind;

    public DetailsRecyclerAdapter(List<CityEntity> cities, int rLayout) {
        this.cities = cities;
        this.rLayout = rLayout;
        CityPreference pref = CityPreference.getPreference(null);
        isHumidity = pref.getIsHumidity();
        isPressure = pref.getIsPressure();
        isWind = pref.getIsWind();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rLayout, parent, false);
        MyViewHolder pvh = new MyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvTemperature;
        TextView tvHumidity;
        TextView tvPressure;
        TextView tvWind;
        LinearLayout layoutHumidity, layoutPressure, layoutWind;

        MyViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.text_view_date);
            tvTemperature = (TextView) itemView.findViewById(R.id.text_view_temperature);
            tvHumidity = (TextView) itemView.findViewById(R.id.text_view_humidity);
            tvPressure = (TextView) itemView.findViewById(R.id.text_view_pressure);
            tvWind = (TextView) itemView.findViewById(R.id.text_view_wind);
            layoutHumidity = itemView.findViewById(R.id.layout_humidity);
            layoutPressure = itemView.findViewById(R.id.layout_pressure);
            layoutWind = itemView.findViewById(R.id.layout_wind);
        }

        void bind(CityEntity city) {
            tvDate.setText(DataPreference.getCurrentDate("dd.MM"));
            tvTemperature.setText(city.getTemperature(tvTemperature.getHint().toString()));
            tvHumidity.setText(city.getHumidity(tvHumidity.getHint().toString()));
            tvPressure.setText(city.getPressure(tvPressure.getHint().toString()));
            tvWind.setText(city.getWind(tvWind.getHint().toString()));
            layoutHumidity.setVisibility(isHumidity ? View.VISIBLE : View.GONE);
            layoutPressure.setVisibility(isPressure ? View.VISIBLE : View.GONE);
            layoutWind.setVisibility(isWind ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}