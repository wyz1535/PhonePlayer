package com.leyifu.phoneplayer.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.CategoryActivityAdapter;
import com.leyifu.phoneplayer.constant.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryAppActivity extends BaseActivity {


    private static final String TAG = "CategoryAppActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<String> titles = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.activity_category_app;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        int appId = intent.getIntExtra(Constants.CATEGORY_APP_ID, -1);
        Log.e(TAG, "init: " + appId);

        titles.add(getResources().getString(R.string.category_good));
        titles.add(getResources().getString(R.string.category_top));
        titles.add(getResources().getString(R.string.category_new));

        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)),true);
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(3);

        viewPager.setAdapter(new CategoryActivityAdapter(getSupportFragmentManager(),appId,titles));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
