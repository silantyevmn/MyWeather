package silantyevmn.ru.weather;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by silan on 30.06.2018.
 */

public class DialogView {
    private onClick listener;
    private static DialogView dialogView = null;

    private DialogView(onClick listener) {
        this.listener = listener;
    }

    public static DialogView getDialog(onClick listener) {
        if (dialogView == null) {
            return new DialogView(listener);
        } else {
            return dialogView;
        }
    }

    public interface onClick {
        void onClickPositive(String city);
    }

    public void showDialogView(Activity activity, String city, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        builder.setTitle(title);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_item, null);
        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        final EditText userInput = view.findViewById(R.id.input_text);
        userInput.setText(city);
        builder.setView(view);
        builder.setPositiveButton(activity.getString(R.string.dialog_button_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onClickPositive(userInput.getText().toString());
            }
        });
        builder.setNegativeButton(activity.getString(R.string.dialog_button_cancel), null);
        builder.create().show();
    }

}
