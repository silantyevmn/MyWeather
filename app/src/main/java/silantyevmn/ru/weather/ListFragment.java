package silantyevmn.ru.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by silan on 02.06.2018.
 */

public class ListFragment extends Fragment {
    private final int ORIENTATION = LinearLayout.VERTICAL; //1
    private onClickCityListItem listener;
    private MyAdapter adapter;

    // Создадим интерфейс, через который мы будем передавать номер строки списка, нажатой пользователем
    public interface onClickCityListItem {
        void onClickListItem(int id);
    }

    // Инстантиируем наш интерфейс
    @Override
    public void onAttach(Context context) {
        listener = (onClickCityListItem) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        // Найдем наш RecyclerView в frament_list
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        // Создадим LinearLayoutManager.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // Обозначим ориентацию
        layoutManager.setOrientation(ORIENTATION);
        // Назначим нашему RecyclerView созданный ранее layoutManager
        recyclerView.setLayoutManager(layoutManager);
        // Назначим нашему RecyclerView адаптер
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    // Класс, который содержит в себе все элементы списка
    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView cityNameTextView;

        MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_list_item, parent, false));
            //установим обработчик нажатия на список
            itemView.setOnClickListener(this);
            cityNameTextView = (TextView) itemView.findViewById(R.id.text_view_item);
        }

        void bind(String value) {
            cityNameTextView.setText(value);
        }

        @Override
        public void onClick(View view) {
            setPositionOnActivity(this.getLayoutPosition());
        }
    }

    private void setPositionOnActivity(int position) {
        listener.onClickListItem(position);
    }

    // Адаптер для RecyclerView
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new MyViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bind(CityEmmiter.getCities().get(position).getName());
        }

        @Override
        public int getItemCount() {
            return CityEmmiter.getCities().size();
        }

    }
}
