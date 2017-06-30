package com.example.superzhang.demonews.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.superzhang.demonews.adapter.MainFragmentAdapter;
import com.example.superzhang.demonews.fragment.JokeFragment;
import com.example.superzhang.demonews.fragment.NewsFragment;
import com.example.superzhang.demonews.R;
import com.example.superzhang.demonews.fragment.MineFragment;
import com.example.superzhang.demonews.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mMainViewPager;
    private TextView viewNews,viewJoke,viewWeather,viewMine;
    private List<Fragment> mainFragmentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragmentItems();
        initButtomItems();

    }

    //设置Fragment界面
    private void initFragmentItems() {
        mMainViewPager = (ViewPager) findViewById(R.id.mainViewPager);

        NewsFragment newsFragment = new NewsFragment();
        mainFragmentList.add(newsFragment);

        JokeFragment jokeFragment= new JokeFragment();
        mainFragmentList.add(jokeFragment);

        WeatherFragment weatherFragment= new WeatherFragment();
        mainFragmentList.add(weatherFragment);

        MineFragment mineFragment = new MineFragment();
        mainFragmentList.add(mineFragment);

        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), mainFragmentList);
        mMainViewPager.setAdapter(adapter);
        mMainViewPager.setOffscreenPageLimit(mainFragmentList.size());
    }

    //设置底部栏
    private void initButtomItems() {
        viewNews = (TextView) findViewById(R.id.viewNews);
        viewJoke = (TextView) findViewById(R.id.viewJoke);
        viewWeather = (TextView) findViewById(R.id.viewWeather);
        viewMine = (TextView) findViewById(R.id.viewMine);

        viewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewPager.setCurrentItem(0);
                viewNews.setTextColor(Color.BLACK);
                viewNews.setTextSize(30);
                viewJoke.setTextColor(Color.WHITE);
                viewJoke.setTextSize(20);
                viewWeather.setTextColor(Color.WHITE);
                viewWeather.setTextSize(20);
                viewMine.setTextColor(Color.WHITE);
                viewMine.setTextSize(20);
            }
        });

        viewJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewPager.setCurrentItem(1);
                viewJoke.setTextColor(Color.BLACK);
                viewJoke.setTextSize(30);
                viewNews.setTextColor(Color.WHITE);
                viewNews.setTextSize(20);
                viewWeather.setTextColor(Color.WHITE);
                viewWeather.setTextSize(20);
                viewMine.setTextColor(Color.WHITE);
                viewMine.setTextSize(20);
            }
        });

        viewWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewPager.setCurrentItem(2);
                viewWeather.setTextColor(Color.BLACK);
                viewWeather.setTextSize(30);
                viewJoke.setTextColor(Color.WHITE);
                viewJoke.setTextSize(20);
                viewNews.setTextColor(Color.WHITE);
                viewNews.setTextSize(20);
                viewMine.setTextColor(Color.WHITE);
                viewMine.setTextSize(20);
            }
        });

        viewMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewPager.setCurrentItem(3);
                viewMine.setTextColor(Color.BLACK);
                viewMine.setTextSize(30);
                viewJoke.setTextColor(Color.WHITE);
                viewJoke.setTextSize(20);
                viewWeather.setTextColor(Color.WHITE);
                viewWeather.setTextSize(20);
                viewNews.setTextColor(Color.WHITE);
                viewNews.setTextSize(20);
            }
        });
    }

}
