package com.example.superzhang.demonews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.superzhang.demonews.data.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by superzhang on 2017/5/16.
 */

public class NewsFragmentAdapter extends FragmentPagerAdapter{

    private List<Fragment> list_fragment;
    private List<String> list_Title;

    public NewsFragmentAdapter(FragmentManager fm, List<Fragment> list_fragment,  List<String> list_Title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        if(list_fragment==null){
            return 0;
        }
        return list_fragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position % list_Title.size());
    }
}
