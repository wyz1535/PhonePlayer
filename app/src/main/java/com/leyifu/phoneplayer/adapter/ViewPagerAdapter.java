package com.leyifu.phoneplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by hahaha on 2018/11/7 0007.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> fragments;
    private List<String> titles;


    public ViewPagerAdapter(FragmentManager supportFragmentManager, List<Fragment> fragments, List<String> titles) {
        super(supportFragmentManager);

        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
