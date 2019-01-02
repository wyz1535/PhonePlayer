package com.leyifu.phoneplayer.act;

import android.content.Intent;
import android.content.res.ColorStateList;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.ViewPagerAdapter;
import com.leyifu.phoneplayer.bean.loginbean.UserBean;
import com.leyifu.phoneplayer.constant.Constants;
import com.leyifu.phoneplayer.fragment.CategoryFragment;
import com.leyifu.phoneplayer.fragment.GameFragment;
import com.leyifu.phoneplayer.fragment.RankingFragment;
import com.leyifu.phoneplayer.fragment.RecommendFragment;
import com.leyifu.phoneplayer.util.ACache;
import com.leyifu.phoneplayer.util.RxBus;
import com.leyifu.phoneplayer.util.ShowUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
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
    private ImageView navHeadCircle;
    private TextView navHeadTitle;
    private View headerView;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

        initNavigation();

        initToolBar();

        initTablayout();

        initUser();

        RxBus.getDefault().tObservable(UserBean.class).subscribe(new Consumer<UserBean>() {
            @Override
            public void accept(UserBean user) throws Exception {
                initHeadView(user);
            }
        });
    }

    private void initUser() {

        Object obj = ACache.get(this).getAsObject(Constants.USER);
        if (obj != null) {
            UserBean userBean = (UserBean) obj;
            initHeadView(userBean);
        }
    }

    private void initTablayout() {

        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();

        titles.add(getResources().getString(R.string.tab_recommend));
        titles.add(getResources().getString(R.string.tab_ranking));
        titles.add(getResources().getString(R.string.tab_game));
        titles.add(getResources().getString(R.string.tab_category));


        fragments.add(RecommendFragment.getInstance());
        fragments.add(RankingFragment.newInstance());
        fragments.add(GameFragment.newInstance());
        fragments.add(CategoryFragment.newInstance("这是activity的数据"));

        tablayout.addTab(tablayout.newTab().setText(titles.get(0)), true);
        tablayout.addTab(tablayout.newTab().setText(titles.get(1)));
        tablayout.addTab(tablayout.newTab().setText(titles.get(2)));
        tablayout.addTab(tablayout.newTab().setText(titles.get(3)));
        tablayout.setupWithViewPager(mainViewPager);
        mainViewPager.setOffscreenPageLimit(4);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, titles);


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

        headerView = navigationView.getHeaderView(0);

        navHeadCircle = (ImageView) headerView.findViewById(R.id.nav_head_circle);
        navHeadTitle = (TextView) headerView.findViewById(R.id.nav_head_title);

        headerView.setOnClickListener(headerViewClick);

        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        menuSelect();

    }

    /**
     * menu 选择器
     */
    private void menuSelect() {
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.black02),
                getResources().getColor(R.color.white)
        };
        ColorStateList csl = new ColorStateList(states, colors);

        navigationView.setItemTextColor(csl);
        navigationView.setItemIconTintList(csl);
    }

    View.OnClickListener headerViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    };

    private void initHeadView(UserBean user) {
        String logo_url = user.getLogo_url();
        if (!logo_url.startsWith("http")) {
            Glide.with(this).load("https:" + user.getLogo_url())
//                .placeholder(getResources().getDrawable(R.drawable.ic_login_svg))
                    .error(getResources().getDrawable(R.drawable.nav_head))
//                .dontAnimate()
                    .centerCrop()
                    .into(navHeadCircle);
        } else {
            Glide.with(this).load(user.getLogo_url())
                    .error(getResources().getDrawable(R.drawable.nav_head))
                    .centerCrop()
                    .into(navHeadCircle);
        }

        navHeadTitle.setText(user.getUsername());
        headerView.setEnabled(false);
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

                case R.id.loadmanager:
                    ShowUtil.toast(MainActivity.this, "this is a loadmanager");
                    break;

                case R.id.setting:
                    ShowUtil.toast(MainActivity.this, "this is a setting");
                    break;
                case R.id.uninstall:
                    ShowUtil.toast(MainActivity.this, "this is a uninstall");
                    break;
                case R.id.logout:
                    ACache aCache = ACache.get(MainActivity.this);
                    aCache.put(Constants.TOKEN, "");
                    aCache.put(Constants.USER, "");

                    navHeadTitle.setText(getResources().getString(R.string.no_login));
                    headerView.setEnabled(true);
                    navHeadCircle.setImageDrawable(getResources().getDrawable(R.drawable.ic_nologin_svg));

                    ShowUtil.toast(MainActivity.this, getResources().getString(R.string.logouted));
                    break;

                default:
                    break;
            }

            return false;
        }
    };
}
