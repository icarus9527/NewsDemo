package com.example.superzhang.demonews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.superzhang.demonews.R;
import com.example.superzhang.demonews.data.WeatherBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/30/030.
 */

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.MyWeatherViewHolder>{
    private List<WeatherBean.ResultBean.FutureBean> futureBeanList = new ArrayList<>();

    public WeatherRecyclerViewAdapter(List<WeatherBean.ResultBean.FutureBean> futureBeanList) {
        this.futureBeanList = futureBeanList;
    }

    static class MyWeatherViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        private View itemView;
        private TextView tvFutureWeek;
        private TextView tvFutureDate;
        private TextView tvFutureTemperature;
        private TextView tvFutureWeather;
        private TextView tvFutureWind;
        private ImageView imgFutureWeather;


        public MyWeatherViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvFutureWeek = (TextView) itemView.findViewById(R.id.tv_future_week);
            tvFutureDate = (TextView) itemView.findViewById(R.id.tv_future_date);
            tvFutureTemperature = (TextView) itemView.findViewById(R.id.tv_future_temperature);
            tvFutureWeather = (TextView) itemView.findViewById(R.id.tv_future_weather);
            tvFutureWind = (TextView) itemView.findViewById(R.id.tv_future_wind);
            imgFutureWeather = (ImageView) itemView.findViewById(R.id.img_future_weather);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public MyWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_weather_items,parent,false);
        final WeatherRecyclerViewAdapter.MyWeatherViewHolder holder = new WeatherRecyclerViewAdapter.MyWeatherViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyWeatherViewHolder holder, int position) {

        WeatherBean.ResultBean.FutureBean futureBean = futureBeanList.get(position);
        if (futureBean==null){
            System.out.println("-------------------futureBean:暂无数据");
            holder.tvFutureTemperature.setText("暂无数据");
        }
        holder.tvFutureTemperature.setText(futureBean.getTemperature());
        holder.tvFutureWeather.setText(futureBean.getWeather());
        holder.tvFutureWind.setText(futureBean.getWind());
        holder.tvFutureWeek.setText(futureBean.getWeek());
        holder.tvFutureDate.setText(futureBean.getDate());
    }

    @Override
    public int getItemCount() {
        return (futureBeanList == null) ? 0 : futureBeanList.size();
    }

    public void onWeatherDataChanged(List<WeatherBean.ResultBean.FutureBean> futureBean){
        futureBeanList = futureBean;
        notifyDataSetChanged();
    }

}
