package silantyevmn.ru.weather.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import java.util.List;

import silantyevmn.ru.weather.DialogView;
import silantyevmn.ru.weather.R;
import silantyevmn.ru.weather.Start;
import silantyevmn.ru.weather.database.DataBaseSource;
import silantyevmn.ru.weather.database.city.CityEntity;

/**
 * Created by silan on 02.06.2018.
 */

public class MainFragment extends Fragment{
    private onClickCityListItem listener;
    private MyAdapter adapter;
    private List<CityEntity> cityEntities;
    private DataBaseSource dataBaseSource;

    // Создадим интерфейс, через который мы будем передавать номер строки списка, нажатой пользователем
    public interface onClickCityListItem {
        void onClickListItem(int position);
    }

    // Инстантиируем наш интерфейс
    @Override
    public void onAttach(Context context) {
        listener = (onClickCityListItem) context;
        cityEntities= DataBaseSource.getInstance().getListCityEntity();
        dataBaseSource=DataBaseSource.getInstance();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        FloatingActionButton button=rootView.findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCity(0,"NewCity");
            }
        });
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
            itemView.setOnClickListener(this);
            //регистрируем контекст_меню
            registerForContextMenu(itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bind(CityEntity city) {
            cityNameTextView.setText(city.getName());
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

    // Адаптер для RecyclerView
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new MyViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bind(cityEntities.get(position));
        }

        @Override
        public int getItemCount() {
            return cityEntities.size();
        }

    }

    //обработка нажатий на список, передача position
    private void setPositionOnActivity(int position) {
        listener.onClickListItem(position);
    }

    //добавление города в список
    private void addCity(final int position, String name) {
        DialogView.getDialog(new DialogView.onClick() {
            @Override
            public void onClickPositive(String cityName) {
                CityEntity cityEntity=new CityEntity(cityName);
                //добавляем в базу
                DataBaseSource.getInstance().insert(cityEntity);
                //обновляем коллекцию
                updateCityEntity();
            }
        }).showDialogView(getActivity(),name,"");
    }

    //удаление города из списка
    private void deleteCity(int position) {
        if (cityEntities.get(position).isLocation()) {
            showDialog(getString(R.string.text_dialog_delete_and_update_error));
        } else {
            dataBaseSource.delete(cityEntities.get(position));
            //обновляем коллекцию
            updateCityEntity();
        }
    }

    private void showDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setPositiveButton("Ok",null);
        builder.create().show();
    }

    //редактирование города
    private void editCity(final int position) {
        final CityEntity currentCity=cityEntities.get(position);
        final String name = currentCity.getName();
        if (cityEntities.get(position).isLocation()) {
            showDialog(getString(R.string.text_dialog_delete_and_update_error));
        } else {
            //показываем окно с вводом города
            DialogView.getDialog(new DialogView.onClick() {
                @Override
                public void onClickPositive(String cityName) {
                    CityEntity newCityEntity = new CityEntity(cityName);
                    //обновляем в базе
                    dataBaseSource.delete(currentCity);
                    dataBaseSource.insert(newCityEntity);
                    //обновляем коллекцию
                    updateCityEntity();
                }
            }).showDialogView(getActivity(), name, "");
        }
    }

    private void updateCityEntity() {
        //загрузка из базы и обновление коллекции
        cityEntities= dataBaseSource.getListCityEntity();
        adapter.notifyDataSetChanged();
    }
}
