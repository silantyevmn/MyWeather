package silantyevmn.ru.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by silan on 31.05.2018.
 */

public class TestFragment extends Fragment implements View.OnClickListener{
    private TextView textView;
    private Button btn1,btn2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_button, container, false);
        //находим ресурсы
        textView=rootView.findViewById(R.id.text_view_test);
        btn1=rootView.findViewById(R.id.button_test1);
        btn2=rootView.findViewById(R.id.button_test2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.button_test1){
            showText(btn1.getText().toString());
        }
        if(view.getId()==R.id.button_test2){
            showText(btn2.getText().toString());
        }
    }

    private void showText(String value){
        textView.setText("fragment3-button-"+value);
    }
}
