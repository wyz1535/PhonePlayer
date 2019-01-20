package com.leyifu.phoneplayer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.bean.appDetail.AppDetailDataBean;
import com.leyifu.phoneplayer.constant.Constants;

import java.util.ArrayList;
import java.util.Arrays;
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
