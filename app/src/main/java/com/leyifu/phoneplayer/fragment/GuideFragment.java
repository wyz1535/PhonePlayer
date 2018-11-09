package com.leyifu.phoneplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyifu.phoneplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hahaha on 2018/11/9 0009.
 */

public class GuideFragment extends Fragment {

    @BindView(R.id.iv_guide)
    ImageView ivGuide;
    Unbinder unbinder;
    private static GuideFragment guideFragment;
    public static final String IMG = "gui_image";

    public static Fragment newInstance(int imgs) {

        guideFragment = new GuideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IMG, imgs);
        guideFragment.setArguments(bundle);

        return guideFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);

        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        int anInt = arguments.getInt(IMG);
        ivGuide.setImageDrawable(getResources().getDrawable(anInt));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
