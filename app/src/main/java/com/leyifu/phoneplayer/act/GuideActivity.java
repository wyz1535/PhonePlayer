package com.leyifu.phoneplayer.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.GuideAdapter;
import com.leyifu.phoneplayer.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewpager_guide)
    ViewPager viewpagerGuide;
    @BindView(R.id.btn_guide)
    Button btnGuide;
    @BindView(R.id.ll_guide_point)
    LinearLayout llGuidePoint;

    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        fragments.add(GuideFragment.newInstance(R.drawable.guide01));
        fragments.add(GuideFragment.newInstance(R.drawable.guide02));
        fragments.add(GuideFragment.newInstance(R.drawable.guide03));

        GuideAdapter guideAdapter = new GuideAdapter(getSupportFragmentManager(), fragments);

        viewpagerGuide.setOffscreenPageLimit(guideAdapter.getCount());
        viewpagerGuide.setAdapter(guideAdapter);

        for (int i = 0; i < fragments.size(); i++) {

            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            params.rightMargin = 10;
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.guide_point_select);

            llGuidePoint.addView(view);

        }

        llGuidePoint.getChildAt(0).setEnabled(false);

        viewpagerGuide.addOnPageChangeListener(this);

    }

    @OnClick(R.id.btn_guide)
    public void onViewClicked() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private int proPosition;
    private int modifyPosition;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.modifyPosition = position;
        if (proPosition != modifyPosition) {
            llGuidePoint.getChildAt(proPosition).setEnabled(true);
            llGuidePoint.getChildAt(modifyPosition).setEnabled(false);
            proPosition = modifyPosition;
        }

        if (position == fragments.size()) {
            btnGuide.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
