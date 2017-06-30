package com.example.superzhang.demonews.fragment;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.superzhang.demonews.R;
import com.example.superzhang.demonews.listener.onCityChangedListener;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2017/6/30/030.
 */

public class DialogFragmentSelectCity extends DialogFragment{

    private onCityChangedListener listener;
    private String showMSg;

    public DialogFragmentSelectCity(String showMSg){
        this.showMSg = showMSg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_select_city,container,false);
        view.setMinimumWidth(200);
        view.setMinimumHeight(200);

        final EditText edt_city_name = (EditText) view.findViewById(R.id.edt_weather_city);
        edt_city_name.setHint(showMSg);
        TextView tv_select = (TextView) view.findViewById(R.id.btn_weather_query);



        //获取城市名称
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cityName = edt_city_name.getText().toString().trim();
                if (cityName!=null && cityName!=""){
                    setCityName(cityName);
                    System.out.println("-----------------CityName:"+getCityName());
                }
                listener.onCityChanged();
                dismiss();
            }
        });

        return view;
    }

    public String getCityName() {
        SharedPreferences preferences = getContext().getSharedPreferences("city", getContext().MODE_PRIVATE);
        String cityName = preferences.getString("city_name","广州");
        return cityName;
    }

    public  void setCityName(String cityName){
        SharedPreferences preferences = getContext().getSharedPreferences("city", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("city_name",cityName);
        editor.apply();
    };

    public void setOnCityChangedListener(onCityChangedListener listener){
        this.listener = listener;
    }

}
