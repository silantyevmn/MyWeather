package silantyevmn.ru.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by silan on 07.06.2018.
 */

public class DetailsRecyclerAdapter extends RecyclerView.Adapter<DetailsRecyclerAdapter.MyViewHolder> {
    private ArrayList<City> cities;
    private int rLayout;

    public DetailsRecyclerAdapter(ArrayList<City> cities, int rLayout) {
        this.cities = cities;
        this.rLayout=rLayout;
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
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvTemperature;
        TextView tvHumidity;
        TextView tvPressure;
        TextView tvWind;
        LinearLayout layoutHumidity,layoutPressure,layoutWind;
        Context context;

        MyViewHolder(View itemView) {
            super(itemView);
            context=itemView.getContext();
            tvDate=(TextView) itemView.findViewById(R.id.text_view_date);
            tvTemperature = (TextView) itemView.findViewById(R.id.text_view_temperature);
            tvHumidity = (TextView) itemView.findViewById(R.id.text_view_humidity);
            tvPressure = (TextView) itemView.findViewById(R.id.text_view_pressure);
            tvWind = (TextView) itemView.findViewById(R.id.text_view_wind);
            layoutHumidity=itemView.findViewById(R.id.layout_humidity);
            layoutPressure=itemView.findViewById(R.id.layout_pressure);
            layoutWind=itemView.findViewById(R.id.layout_wind);
        }

        void bind(City city){
            tvDate.setText(city.getCurrentDate("dd.MM"));
            tvTemperature.setText(city.getTemperature(context));
            tvHumidity.setText(city.getHumidity(context));
            tvPressure.setText(city.getPressure(context));
            tvWind.setText(city.getWind(context));
            layoutHumidity.setVisibility(city.isHumidity()?View.VISIBLE:View.GONE);
            layoutPressure.setVisibility(city.isPressure()?View.VISIBLE:View.GONE);
            layoutWind.setVisibility(city.isWind()?View.VISIBLE:View.GONE);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}