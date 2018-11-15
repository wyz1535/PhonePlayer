package com.leyifu.phoneplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.bean.recommendhomebean.BannersBean;
import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendAppsBean;
import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendDataBean;
import com.leyifu.phoneplayer.constant.Constants;
import com.leyifu.phoneplayer.util.ShowUtil;
import com.leyifu.phoneplayer.widget.BannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hahaha on 2018/11/15 0015.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public static final int TYPE_BANNER = 0;
    public static final int TYPE_HOT = 1;
    public static final int TYPE_APP = 2;
    public static final int TYPE_GAME = 3;

    private Context mContext;
    private RecommendDataBean mDataBean;
    private final LayoutInflater inflater;

    public HomeAdapter(Context context, RecommendDataBean dataBean) {
        this.mContext = context;
        this.mDataBean = dataBean;

        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_BANNER;
        } else if (position == 1) {
            return TYPE_HOT;
        }
//        else if (position == 2) {
//            return TYPE_APP;
//        }
//        else if (position == 3) {
//            return TYPE_GAME;
//        }

        return TYPE_APP;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_BANNER) {
            View view = inflater.inflate(R.layout.home_banner, parent, false);
            return new BannerViewholder(view);
        } else if (viewType == TYPE_HOT) {
            View view = inflater.inflate(R.layout.template_nav_icon, parent, false);
            return new NavIconViewHolder(view);
        } else if (viewType == TYPE_APP) {
            View view = inflater.inflate(R.layout.template_recomend_app, parent, false);
            return new HotAppViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        List<String> imgUrl = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        if (position == 0) {

            List<BannersBean> banners = mDataBean.getBanners();
            for (BannersBean banner : banners) {
                String thumbnail = banner.getThumbnail();
                imgUrl.add(thumbnail);
                titles.add(banner.getAction());
            }

            BannerViewholder bannerViewholder = (BannerViewholder) holder;
            bannerViewholder.banner_view.setDatas(imgUrl, titles, null);

        } else if (position == 1) {

            NavIconViewHolder navIconViewHolder = (NavIconViewHolder) holder;

            navIconViewHolder.layout_hot_app.setOnClickListener(this);
            navIconViewHolder.layout_hot_game.setOnClickListener(this);
            navIconViewHolder.layout_hot_subject.setOnClickListener(this);

        } else {
            HotAppViewHolder viewHolder = (HotAppViewHolder) holder;

            List<RecommendAppsBean> recommendApps = mDataBean.getRecommendApps();
            viewHolder.text_title.setText(recommendApps.get(position).getDisplayName());
            viewHolder.text_size.setText(recommendApps.get(position).getApkSize() / 1024 / 1024 + " MB");
            Glide.with(mContext).load(Constants.BASE_IMG_URL + recommendApps.get(position).getIcon()).into(viewHolder.img_icon);
        }
    }


    @Override
    public int getItemCount() {
        Log.e("getItemCount", "getItemCount: "+mDataBean.getRecommendApps().size());
        return mDataBean.getRecommendApps().size() ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_hot_app:
                ShowUtil.toast(mContext, "layout_hot_app");
                break;
            case R.id.layout_hot_game:
                ShowUtil.toast(mContext, "layout_hot_game");
                break;
            case R.id.layout_hot_subject:
                ShowUtil.toast(mContext, "layout_hot_subject");
                break;
            default:
                break;
        }
    }

    class BannerViewholder extends RecyclerView.ViewHolder {

        BannerView banner_view;

        public BannerViewholder(View itemView) {
            super(itemView);

            banner_view = ((BannerView) (itemView).findViewById(R.id.banner_view));
        }
    }

    class NavIconViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout layout_hot_app;
        private final LinearLayout layout_hot_game;
        private final LinearLayout layout_hot_subject;

        public NavIconViewHolder(View itemView) {
            super(itemView);

            layout_hot_app = ((LinearLayout) itemView.findViewById(R.id.layout_hot_app));
            layout_hot_game = ((LinearLayout) itemView.findViewById(R.id.layout_hot_game));
            layout_hot_subject = ((LinearLayout) itemView.findViewById(R.id.layout_hot_subject));
        }
    }

    class HotAppViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img_icon;
        private final TextView text_title;
        private final TextView text_size;
        private final Button btn_dl;

        public HotAppViewHolder(View itemView) {
            super(itemView);

            img_icon = ((ImageView) itemView.findViewById(R.id.img_icon));
            text_title = ((TextView) itemView.findViewById(R.id.text_title));
            text_size = ((TextView) itemView.findViewById(R.id.text_size));
            btn_dl = ((Button) itemView.findViewById(R.id.btn_dl));
        }
    }

}
