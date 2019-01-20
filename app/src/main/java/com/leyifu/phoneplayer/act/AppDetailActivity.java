package com.leyifu.phoneplayer.act;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.bean.appDetail.AppDetailBean;
import com.leyifu.phoneplayer.bean.appDetail.AppDetailDataBean;
import com.leyifu.phoneplayer.constant.Constants;
import com.leyifu.phoneplayer.fragment.AppDetailFragment;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetAppDetail;
import com.leyifu.phoneplayer.presenter.Persenter;
import com.leyifu.phoneplayer.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppDetailActivity extends BaseActivity implements IgetAppDetail {

    private static final String TAG = "AppDetailActivity";
    @BindView(R.id.iv_view)
    View ivView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolBar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
//    @BindView(R.id.rv_screen_shot)
//    RecyclerView rvScreenShot;

    List<String> imgUrl = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.activity_app_detail;
    }

    @Override
    protected void init() {

        ButterKnife.bind(this);

        Intent intent = getIntent();
        int appId = intent.getIntExtra(Constants.APP_ID, -1);

        View view = ((MyApplication) getApplication()).getView();

        if (view != null) {
            Bitmap bitmap = getViewImageCache(view);

            int[] location = new int[2];

            view.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];

            if (bitmap != null) {
                ivView.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }

            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ivView.getLayoutParams());
            marginLayoutParams.leftMargin = left;
            marginLayoutParams.topMargin = top - DensityUtil.getStatusBarH(this);
            marginLayoutParams.width = getResources().getDisplayMetrics().widthPixels;
            marginLayoutParams.height = view.getHeight();

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(marginLayoutParams);
            ivView.setLayoutParams(params);

            expandView(appId);

        }
    }

    private void expandView(final int appId) {
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivView, "scaleY", 1.0f, ((float) heightPixels));
        animator.setDuration(100);
        animator.setStartDelay(500);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ivView.setBackgroundColor(getResources().getColor(R.color.white));
                coordinatorLayout.setVisibility(View.VISIBLE);
                Persenter.pGetAppDetail(AppDetailActivity.this, HttpApi.class, appId);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ivView.setVisibility(View.GONE);

            }

        });

        animator.start();
    }



    private Bitmap getViewImageCache(View view) {

//        设置view的缓存，提高view的绘制速度
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();
        if (bitmap == null) {
            return null;
        }
        bitmap = bitmap.createBitmap(bitmap);
        view.destroyDrawingCache();
        return bitmap;
    }

    @Override
    public void iGetAppDetailSuccess(AppDetailBean appDetailBean) {

        if (appDetailBean.getStatus() == 1 && appDetailBean.getData() != null) {
            Glide.with(this).load(Constants.BASE_IMG_URL + appDetailBean.getData().getIcon()).into(imgIcon);
            collapsingToolBar.setTitle(appDetailBean.getData().getDisplayName());

            initFrameLayout(appDetailBean.getData());

        }
    }


    @Override
    public void iGetAppDetailFailed(Throwable throwable) {
        Log.e(TAG, "iGetAppDetailFailed: " + throwable);
    }

    @Override
    public void iGetAppDetailStart() {

    }

    @Override
    public void iGetAppDetailComplate() {

    }

    private void initFrameLayout(AppDetailDataBean appDetailDataBean) {
        AppDetailFragment appDetailFragment = AppDetailFragment.newInstance(appDetailDataBean);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_app_detail, appDetailFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
