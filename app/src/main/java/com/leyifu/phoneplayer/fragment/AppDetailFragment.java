package com.leyifu.phoneplayer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.SameDevAdapter;
import com.leyifu.phoneplayer.bean.appDetail.AppDetailDataBean;
import com.leyifu.phoneplayer.constant.Constants;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppDetailFragment extends Fragment {

    private static final String TAG = "AppDetailFragment";
    private static AppDetailFragment mAppDetailFragment;
    public static final String APP_ID = "app_id";
    private static AppDetailDataBean mAppDetailDataBean;
    List<String> imgUrl = new ArrayList<>();

    Unbinder unbinder;
    @BindView(R.id.ll_screen_shot)
    LinearLayout llScreenShot;
    @BindView(R.id.ex_tv)
    ExpandableTextView exTv;
    @BindView(R.id.tv_updata)
    TextView tvUpdata;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.tv_dev)
    TextView tvDev;
    @BindView(R.id.rv_same_dev)
    RecyclerView rvSameDev;
    @BindView(R.id.rv_same_app)
    RecyclerView rvSameApp;


    public AppDetailFragment() {
        // Required empty public constructor
    }

    public static AppDetailFragment newInstance(AppDetailDataBean appDetailDataBean) {
        mAppDetailDataBean = appDetailDataBean;

        if (mAppDetailFragment == null) {
            synchronized (AppDetailFragment.class) {
                if (mAppDetailFragment == null) {
                    mAppDetailFragment = new AppDetailFragment();
                }
            }
        }

        return mAppDetailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_detail, container, false);
        Log.e(TAG, "onCreateView: " + mAppDetailDataBean.getPackageName());
        init(view);
        return view;
    }

    private void init(View view) {

        unbinder = ButterKnife.bind(this, view);

        initScreenShot();

        initIntroduction();

        initDetail();

        initSameDev();

    }

    private void initSameDev() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvSameDev.setLayoutManager(linearLayoutManager);

        rvSameDev.setAdapter(new SameDevAdapter(mAppDetailDataBean.getSameDevAppInfoList()));
    }

    private void initIntroduction() {
        exTv.setText(mAppDetailDataBean.getIntroduction());
    }

    private void initDetail() {
        String dataFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date(mAppDetailDataBean.getUpdateTime()));
        tvUpdata.setText("更新时间:" + dataFormat);
        tvVersion.setText("最新版本:" + mAppDetailDataBean.getVersionName());
        tvSize.setText("应用大小:" +( mAppDetailDataBean.getApkSize()/1024/1024 )+ "MB");
        tvDev.setText("开发者:" + mAppDetailDataBean.getPublisherName());
    }

    private void initScreenShot() {

        List<String> list = Arrays.asList(mAppDetailDataBean.getScreenshot().split(","));

        for (String s : list) {
            ImageView imageView = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.template_imageview, llScreenShot, false);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(480, 640);
            imageView.setLayoutParams(params);
            Log.e(TAG, "initScreenShot: " + list.get(0));
            Glide.with(this).load(Constants.BASE_IMG_URL + s).into(imageView);

            llScreenShot.addView(imageView);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
