package silantyevmn.ru.weather;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import silantyevmn.ru.weather.fragment.ListFragment;
import silantyevmn.ru.weather.utility.CityEmmiter;

/**
 * Created by silan on 02.06.2018.
 */

public class DialogEditItem {
    private onUpdateAdapter listener;

    public interface onUpdateAdapter {
        void onUpdateAdapterItem(int position);
    }

    public DialogEditItem(ListFragment listFragment) {
        listener = listFragment;
    }

    public void editItem(Context context, String name, int position) {
        final int positionId = position;
        //Получаем вид с файла prompt.xml, который применим для диалогового окна:
        final LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_edit_item, null);

        //Создаем AlertDialog
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

        //Настраиваем prompt.xml для нашего AlertDialog:
        mDialogBuilder.setView(promptsView);

        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
        userInput.setText(name);

        //Настраиваем сообщение в диалоговом окне:
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.text_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Вводим текст и меняем его в cities
                                CityEmmiter.setEditCity(userInput.getText().toString(), positionId);
                                listener.onUpdateAdapterItem(positionId);
                                //adapter.notifyDataSetChanged();
                            }
                        })
                .setNegativeButton(R.string.text_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        //Создаем AlertDialog:
        AlertDialog alertDialog = mDialogBuilder.create();
        //и отображаем его:
        alertDialog.show();
    }
}
