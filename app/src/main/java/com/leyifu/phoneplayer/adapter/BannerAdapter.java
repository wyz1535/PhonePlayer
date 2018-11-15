package com.leyifu.phoneplayer.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by hahaha on 2018/11/15 0015.
 */

public class BannerAdapter extends PagerAdapter {

    private Context context;
    private List<ImageView> imageViews;
    private List<String> titles;

    public BannerAdapter(Context context, List<ImageView> imageViews, List<String> titles) {
        this.context = context;
        this.imageViews = imageViews;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return imageViews == null ? 0 : imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = imageViews.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView = imageViews.get(position);
        container.removeView(imageView);

    }
}
