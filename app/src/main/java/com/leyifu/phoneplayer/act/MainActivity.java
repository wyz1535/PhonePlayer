package com.leyifu.phoneplayer.act;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.ViewPagerAdapter;
import com.leyifu.phoneplayer.fragment.CategoryFragment;
import com.leyifu.phoneplayer.fragment.GameFragment;
import com.leyifu.phoneplayer.fragment.RankingFragment;
import com.leyifu.phoneplayer.fragment.RecommendFragment;
import com.leyifu.phoneplayer.util.ShowUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tool_search)
    ImageView toolSearch;
    @BindView(R.id.main_view_pager)
    ViewPager mainViewPager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

        initNavigation();

        initToolBar();

        initTablayout();
    }

    private void initTablayout() {

        List<String> titles = new ArrayList<>();
        List<Fragment> fragments= new ArrayList<>();

        titles.add(getResources().getString(R.string.tab_recommend));
        titles.add(getResources().getString(R.string.tab_ranking));
        titles.add(getResources().getString(R.string.tab_game));
        titles.add(getResources().getString(R.string.tab_category));



        fragments.add(RecommendFragment.getInstance());
        fragments.add(RankingFragment.newInstance());
        fragments.add(GameFragment.newInstance());
        fragments.add(CategoryFragment.newInstance("这是activity的数据"));

        tablayout.addTab(tablayout.newTab().setText(titles.get(0)),true);
        tablayout.addTab(tablayout.newTab().setText(titles.get(1)));
        tablayout.addTab(tablayout.newTab().setText(titles.get(2)));
        tablayout.addTab(tablayout.newTab().setText(titles.get(3)));
        tablayout.setupWithViewPager(mainViewPager);
        mainViewPager.setOffscreenPageLimit(4);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments ,titles);


        mainViewPager.setAdapter(viewPagerAdapter);

    }

    /**
     * 初始化toolbar，绑定drawablelayout
     */
    private void initToolBar() {

        toolbar.setTitle(getResources().getString(R.string.app_title));

        setSupportActionBar(toolbar);

        toolbar.inflateMenu(R.menu.navigation_menu);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.close, R.string.open);

        drawerToggle.syncState();

        drawerLayout.addDrawerListener(drawerToggle);
    }

    private void initNavigation() {

        View headerView = navigationView.getHeaderView(0);

//        headerView.setOnClickListener(navigationHeadListner);

        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

    }

    @OnClick({R.id.navigation_view, R.id.toolbar, R.id.tool_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.navigation_view:
                break;

            case R.id.toolbar:
                break;
            case R.id.tool_search:
                ShowUtil.toast(this, "this is a search");
                break;
            default:
                break;
        }
    }

    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.refresh:
                    ShowUtil.toast(MainActivity.this, "this is a refresh");
                    break;

                case R.id.message:
                    ShowUtil.toast(MainActivity.this, "this is a message");
                    break;

                case R.id.setting:
                    ShowUtil.toast(MainActivity.this, "this is a setting");
                    break;

                default:
                    break;
            }

            return false;
        }
    };
}
