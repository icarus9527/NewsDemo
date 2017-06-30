package com.example.superzhang.demonews.adapter;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by superzhang on 2017/5/28.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter{

    private List<Fragment> mainFragmentList = new ArrayList<>();

    public MainFragmentAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> mainFragmentList) {
        super(fm);
        this.mainFragmentList = mainFragmentList;

    }
    @Override
    public Fragment getItem(int position) {
        return mainFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mainFragmentList.size();
    }
}
