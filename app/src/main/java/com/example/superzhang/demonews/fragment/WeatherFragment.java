package com.example.superzhang.demonews.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.superzhang.demonews.R;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.example.superzhang.demonews.adapter.WeatherRecyclerViewAdapter;
import com.example.superzhang.demonews.data.WeatherBean;
import com.example.superzhang.demonews.listener.onCityChangedListener;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by superzhang on 2017/5/16.
 */

public class WeatherFragment extends Fragment implements onCityChangedListener {

    private static final int GET_WEATHER_DATA = 3001;
    private Handler mhandler ;
    private TextView sk_temp,sk_wind_direction,sk_strength,sk_humidity,sk_time;
    private TextView tv_location,more_weather;
    private EditText edt_weather_city;
    private WeatherRecyclerViewAdapter adaper;

    //滑动布局
    private SwipeLayout swipeLayout;

    //底部recyclerView
    private RecyclerView recyclerView;

    private WeatherBean.ResultBean.SkBean skBean = new WeatherBean.ResultBean.SkBean();
    private WeatherBean.ResultBean.TodayBean todayBean = new WeatherBean.ResultBean.TodayBean();
    private List<WeatherBean.ResultBean.FutureBean> futureBean = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather,container,false);

        initHandler();
        findViews(view);
        initSwipeLayout(view);
        //getCityWeatherData(getCityName());
        initRecycleView();


        //所在地点击事件
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogFragment("请输入城市名称");
            }
        });

        //更多详情所在事件
        more_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout.open(SwipeLayout.DragEdge.Bottom);
            }
        });
        return view;
    }

    private void initHandler() {
        mhandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == GET_WEATHER_DATA){
                    //设置当前天气
                    sk_temp.setText(skBean.getTemp()+"°");
                    sk_wind_direction.setText(skBean.getWind_direction());
                    sk_strength.setText(skBean.getWind_strength());
                    sk_humidity.setText("湿度:"+skBean.getHumidity());
                    sk_time.setText(skBean.getTime());
                    tv_location.setText(getCityName());
                    adaper.onWeatherDataChanged(futureBean);
                }
                return true;
            }
        });
    }

    //弹出设置城市框
    private void alertDialogFragment(String msg) {
        DialogFragmentSelectCity dialogFragment = new DialogFragmentSelectCity(msg);
        dialogFragment.setOnCityChangedListener(WeatherFragment.this);
        dialogFragment.show(getFragmentManager(),msg);
    }

    private void initRecycleView() {
        /*for (int i=0;i<7;i++){
            WeatherBean.ResultBean.FutureBean bean = new WeatherBean.ResultBean.FutureBean ();
            bean.setTemperature("27℃~33℃");
            bean.setDate("20170808");
            bean.setWeather("晴转多云");
            bean.setWind("东南风3-4级");
            bean.setWeek("星期六");
            futureBean.add(bean);
        }*/

        //设置recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adaper = new WeatherRecyclerViewAdapter(futureBean);
        recyclerView.setAdapter(adaper);

    }

    //设置滑动布局
    private void initSwipeLayout(View view) {
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Bottom,view.findViewById(R.id.swipelayout_bottom));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Top,view.findViewById(R.id.swipelayout_header));
        swipeLayout.addSwipeListener(new SimpleSwipeListener(){
            @Override
            public void onOpen(SwipeLayout layout) {
                if (!(futureBean.size()>0)){
                    swipeLayout.close();
                }
            }
        });
    }

    private void findViews(View view){
        sk_temp = (TextView) view.findViewById(R.id.sk_temp);
        sk_wind_direction = (TextView) view.findViewById(R.id.sk_wind_direction);
        sk_strength = (TextView) view.findViewById(R.id.sk_strength);
        sk_humidity = (TextView) view.findViewById(R.id.sk_humidity);
        sk_time = (TextView) view.findViewById(R.id.sk_time);

        tv_location = (TextView) view.findViewById(R.id.tv_location);
        more_weather = (TextView) view.findViewById(R.id.more_weather);
        edt_weather_city = (EditText) view.findViewById(R.id.edt_weather_city);

        swipeLayout = (SwipeLayout) view.findViewById(R.id.swipelayout_content);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_weather);
    }

    //从聚合数据获取天气状况
    private void getCityWeatherData(String cityname){
        String url = "http://v.juhe.cn/weather/index?format=2&cityname="+cityname+"&key=2d6934e1e4e6eba32e15867c8af4b8be";
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String responseWeatherData = responseInfo.result;
                formatWeatherData(responseWeatherData);
            }
            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(getContext(),"Json数据请求错误",Toast.LENGTH_SHORT).show();
            }
        });
        mhandler.sendEmptyMessageDelayed(GET_WEATHER_DATA,2000);
    }

    private void formatWeatherData(String responseWeatherData) {

        String json = responseWeatherData;
        System.out.println("------------json"+json);

        Gson gson = new Gson();
        WeatherBean datas = gson.fromJson(json,WeatherBean.class);
        //结果数据
        if (datas.getResultcode().equals("200")){
            WeatherBean.ResultBean result = datas.getResult();
            skBean = result.getSk();//实时天气
            todayBean = result.getToday();//当前天气状况
            futureBean = result.getFuture();//未来7天天气
        } else if (datas.getResultcode().equals("201")){
            //Toast.makeText(getContext(),"Error Cityname!",Toast.LENGTH_SHORT).show();
            alertDialogFragment("名称错误 请重试");
        } else if (datas.getResultcode().equals("202")){
            alertDialogFragment("查询不到该城市的信息");
        }

        /*if (skBean.getTemp()!=null){
            mhandler.sendEmptyMessage(GET_WEATHER_DATA);
        } else  {
            Toast.makeText(getContext(),"解析Json错误",Toast.LENGTH_SHORT).show();
        }*/

    }

    //统一未来一周的Bean类型
    private String formatJson(String responseWeatherData) {
        String json = responseWeatherData;
        //Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        //cal.add(Calendar.DAY_OF_MONTH, -1);//取当前日期的前一天.
        //cal.add(Calendar.DAY_OF_MONTH, +1);//取当前日期的后一天.
        Calendar cal = Calendar.getInstance();
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
        for (int i=1;i<=7;i++){
            cal.add(Calendar.DAY_OF_MONTH, +i);
            String date = format.format(cal.getTime());
            //Day20170629Bean
            json = json.replace("day_"+i,"Day"+date+"Bean");
        }
        return json;
    }

    private void checkCityOfShared() {
        String cityName = getCityName();
        System.out.println("----------------查询到的城市名称是:"+cityName);
        if (cityName==null || cityName==""){
            Intent intent = new Intent(getContext(),DialogFragmentSelectCity.class);
            //startActivity(intent);
        }
    }

    public String getCityName() {
        SharedPreferences preferences = getContext().getSharedPreferences("city", getContext().MODE_PRIVATE);
        String cityName = preferences.getString("city_name","广州");
        return cityName;
    }

    @Override
    public void onCityChanged() {
        System.out.println("-------city changed");
        getCityWeatherData(getCityName());
    }
}
