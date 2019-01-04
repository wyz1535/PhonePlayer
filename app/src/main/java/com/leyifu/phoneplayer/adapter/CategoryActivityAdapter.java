package com.leyifu.phoneplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.leyifu.phoneplayer.fragment.category.CategoryGoodFragment;

import java.util.List;

/**
 * Created by hahaha on 2019/1/4 0004.
 */

public class CategoryActivityAdapter  extends FragmentStatePagerAdapter {


    private  int appId;
    private List<String> titles;


    public CategoryActivityAdapter(FragmentManager supportFragmentManager, int appId, List<String> titles) {
        super(supportFragmentManager);

        this.appId = appId;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
//        return fragments.get(position);
        return CategoryGoodFragment.newInstance(appId,position);
    }

    @Override
    public int getCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}