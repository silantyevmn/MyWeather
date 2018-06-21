package silantyevmn.ru.weather.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import silantyevmn.ru.weather.DialogEditItem;
import silantyevmn.ru.weather.R;
import silantyevmn.ru.weather.utils.City;
import silantyevmn.ru.weather.utils.CityEmmiter;

/**
 * Created by silan on 02.06.2018.
 */

public class ListFragment extends Fragment implements DialogEditItem.onUpdateAdapter {
    private onClickCityListItem listener;
    private MyAdapter adapter;

    // Создадим интерфейс, через который мы будем передавать номер строки списка, нажатой пользователем
    public interface onClickCityListItem {
        void onClickListItem(int position);
    }

    //обновляем адаптер
    @Override
    public void onUpdateAdapterItem(int position) {
        adapter.notifyItemChanged(position);
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
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        // Назначим нашему RecyclerView созданный ранее layoutManager
        recyclerView.setLayoutManager(layoutManager);
        // Назначим нашему RecyclerView адаптер
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    // Класс, который содержит в себе все элементы списка
    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        private TextView cityNameTextView;
        private TextView cityTemperatureTextView;
        //обработчик на контекстное меню
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int position = getAdapterPosition();
                switch (item.getItemId()) {
                    case R.id.item_add: {
                        addCity(position, "NewCity");
                        break;
                    }
                    case R.id.item_delete: {
                        deleteCity(position);
                        break;
                    }
                    case R.id.item_menu_edit: {
                        editCity(position);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        };

        MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_list_item, parent, false));
            //установим обработчик нажатия на список
            cityNameTextView = itemView.findViewById(R.id.text_view_city);
            cityTemperatureTextView = itemView.findViewById(R.id.text_view_temperature);
            itemView.setOnClickListener(this);
            //регистрируем контекст_меню
            registerForContextMenu(itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bind(City city) {
            cityNameTextView.setText(city.getName());
            cityTemperatureTextView.setText(city.getTemperature(getContext()));
        }

        @Override
        public void onClick(View view) {
            setPositionOnActivity(this.getLayoutPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.context_menu, contextMenu);
            contextMenu.findItem(R.id.item_add).setOnMenuItemClickListener(onEditMenu);
            contextMenu.findItem(R.id.item_delete).setOnMenuItemClickListener(onEditMenu);
            contextMenu.findItem(R.id.item_menu_edit).setOnMenuItemClickListener(onEditMenu);
        }
    }

    //обработка нажатий на список, передача position
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
            City city = CityEmmiter.getCities().get(position);
            holder.bind(city);
        }

        @Override
        public int getItemCount() {
            return CityEmmiter.getCities().size();
        }

    }

    //добавление города в список
    private void addCity(int position, String name) {
        CityEmmiter.setAddNewCity(name, position);
        adapter.notifyItemChanged(position);
    }

    //удаление города из списка
    private void deleteCity(int position) {
        CityEmmiter.setDeleteCity(position);
        adapter.notifyItemRemoved(position);
    }

    //редактирование города
    private void editCity(int position) {
        String name = CityEmmiter.getCities().get(position).getName();
        //показываем окно с вводом города
        new DialogEditItem(this).editItem(getContext(), name, position);
    }
}
