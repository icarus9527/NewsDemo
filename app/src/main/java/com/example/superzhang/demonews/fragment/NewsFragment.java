package com.example.superzhang.demonews.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.superzhang.demonews.R;
import com.example.superzhang.demonews.adapter.NewsFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by superzhang on 2017/5/28.
 */

public class NewsFragment extends Fragment{

    private TabLayout mTabLayout;
    private ViewPager mViewPagerNews;
    private FragmentPagerAdapter fragmentAdapter;

    private List<Fragment> list_fragment = new ArrayList<>();
    private List<String> list_titles = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initNewsFragment();
    }

    private void initNewsFragment() {
        String labels[] = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
        for (int i = 0; i < labels.length; i++) {
            list_titles.add(labels[i]);
        }

        //新建Fragment
        for(int i=0;i<labels.length;i++){
            NewsItemFragment fgNews = new NewsItemFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("labels",i);
            fgNews.setArguments(bundle);
            list_fragment.add(fgNews);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        if(view==null){
            view = inflater.inflate(R.layout.fragment_news,container,false);
        }
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);

        //设置tabLayout的标签模式
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE  );

        //TabLayout与viewpager的绑定
        fragmentAdapter = new NewsFragmentAdapter(getFragmentManager(), list_fragment, list_titles);

        mViewPagerNews = (ViewPager) view.findViewById(R.id.viewPagerNews);
        mViewPagerNews.setAdapter(fragmentAdapter);
        mViewPagerNews.setOffscreenPageLimit(list_fragment.size());

        mTabLayout.setupWithViewPager(mViewPagerNews);
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF6600"));

        return view;
    }
}
