package com.leyifu.phoneplayer.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.bean.recommendhomebean.BannersBean;
import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendAppsBean;
import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendDataBean;
import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendGamesBean;
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
    public static final int TYPE_HORIZONTAL_RECYCLER = 2;
    public static final int TYPE_TITLE_APP = 3;
    public static final int TYPE_TITLE_GAMES = 4;
    public static final int TYPE_APP = 5;
    public static final int TYPE_GAME = 6;

    private Context mContext;
    private RecommendDataBean mDataBean;
    private final LayoutInflater inflater;
    private final List<RecommendAppsBean> recommendApps;
    private final List<RecommendGamesBean> recommendGames;
    private final int gameSize;
    private final int appSize;

    public HomeAdapter(Context context, RecommendDataBean dataBean) {
        this.mContext = context;
        this.mDataBean = dataBean;

        recommendApps = mDataBean.getRecommendApps();
        appSize = recommendApps.size();


        recommendGames = mDataBean.getRecommendGames();
        gameSize = recommendGames.size();

        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_BANNER;
        } else if (position == 1) {
            return TYPE_HOT;
        } else if (position == 2) {
            return TYPE_HORIZONTAL_RECYCLER;
        } else if (position == 3) {
            return TYPE_TITLE_APP;
        } else if (position > 3 && position < appSize + 4) {
            return TYPE_APP;
        } else if (position == appSize + 4) {
            return TYPE_TITLE_GAMES;
        } else if (position > appSize + 4) {
            return TYPE_GAME;
        }

        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_BANNER) {
            View view = inflater.inflate(R.layout.home_banner, parent, false);
            return new BannerViewholder(view);
        } else if (viewType == TYPE_HOT) {
            View view = inflater.inflate(R.layout.template_nav_icon, parent, false);
            return new NavIconViewHolder(view);
        } else if (viewType == TYPE_HORIZONTAL_RECYCLER) {
            View view = inflater.inflate(R.layout.home_horizontal_games, parent, false);
            return new HorizontalGamesViewHolder(view);
        } else if (viewType == TYPE_TITLE_APP) {
            View view = inflater.inflate(R.layout.home_title_apps, parent, false);
            return new TitleAppAndGameViewHolder(view);
        } else if (viewType == TYPE_APP) {
            View view = inflater.inflate(R.layout.template_recomend_app, parent, false);
            return new HotAppViewHolder(view);
        } else if (viewType == TYPE_TITLE_GAMES) {
            View view = inflater.inflate(R.layout.home_title_apps, parent, false);
            return new TitleAppAndGameViewHolder(view);
        } else if (viewType == TYPE_GAME) {
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

        } else if (position == 2) {
            HorizontalGamesViewHolder viewHolder = (HorizontalGamesViewHolder) holder;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolder.recycler_view_horizontal_games.setLayoutManager(linearLayoutManager);
            RecommendHorizontalAdapter recommendHorizontalAdapter = new RecommendHorizontalAdapter(mContext, recommendGames);
            viewHolder.recycler_view_horizontal_games.setAdapter(recommendHorizontalAdapter);


        } else if (position == 3) {
            TitleAppAndGameViewHolder viewHolder = (TitleAppAndGameViewHolder) holder;

            viewHolder.tx_app_title.setText(mContext.getResources().getString(R.string.hot_app));
        } else if (position > 3 && position < appSize + 4) {
            HotAppViewHolder viewHolder = (HotAppViewHolder) holder;

            viewHolder.tv_top_list.setVisibility(View.GONE);
            final String displayName = recommendApps.get(position - 4).getDisplayName();
            if (displayName.length() > 10) {
                String substring = displayName.substring(0, 10);
                viewHolder.text_title.setText(substring + "...");
            } else {
                viewHolder.text_title.setText(displayName);
            }

            viewHolder.text_size.setText(recommendApps.get(position - 4).getApkSize() / 1024 / 1024 + " MB");
            Glide.with(mContext).load(Constants.BASE_IMG_URL + recommendApps.get(position - 4).getIcon()).into(viewHolder.img_icon);

            onClickButton(viewHolder.rl_item_recycler_view, displayName);
            onClickLoad(viewHolder.btn_dl,displayName);

        } else if (position == appSize + 4) {
            TitleAppAndGameViewHolder viewHolder = (TitleAppAndGameViewHolder) holder;

            viewHolder.tx_app_title.setText(mContext.getResources().getString(R.string.hot_game));
        } else if (position > appSize + 4) {
            HotAppViewHolder viewHolder = (HotAppViewHolder) holder;
            viewHolder.tv_top_list.setVisibility(View.GONE);
            final String displayName = recommendGames.get(position - appSize - 5).getDisplayName();
            if (displayName.length() > 10) {
                String substring = displayName.substring(0, 10);
                viewHolder.text_title.setText(substring + "...");
            } else {
                viewHolder.text_title.setText(displayName);
            }
            viewHolder.text_size.setText(recommendGames.get(position - appSize - 5).getApkSize() / 1024 / 1024 + " MB");
            Glide.with(mContext).load(Constants.BASE_IMG_URL + recommendGames.get(position - appSize - 5).getIcon()).into(viewHolder.img_icon);

            onClickButton(viewHolder.rl_item_recycler_view, displayName);
            onClickLoad(viewHolder.btn_dl,displayName);
        }
    }

    private void onClickLoad(Button view,final String displayName) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/11/16 0016 下载的点击事件
                ShowUtil.toast(mContext, displayName + "下载...");
            }
        });
    }

    private void onClickButton(RelativeLayout view, final String displayName) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/11/16 0016 recyclerview的点击事件
                ShowUtil.toast(mContext, displayName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appSize + gameSize + 5;
    }

    @Override
    public void onClick(View v) {
        // TODO: 2018/11/16 0016 热门的点击事件
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
        private final TextView tv_top_list;
        private final RelativeLayout rl_item_recycler_view;

        public HotAppViewHolder(View itemView) {
            super(itemView);

            img_icon = ((ImageView) itemView.findViewById(R.id.img_icon));
            text_title = ((TextView) itemView.findViewById(R.id.text_title));
            text_size = ((TextView) itemView.findViewById(R.id.text_size));
            btn_dl = ((Button) itemView.findViewById(R.id.btn_dl));
            tv_top_list = ((TextView) itemView.findViewById(R.id.tv_top_list));
            rl_item_recycler_view = ((RelativeLayout) itemView.findViewById(R.id.rl_item_recycler_view));
        }
    }

    class TitleAppAndGameViewHolder extends RecyclerView.ViewHolder {

        private final TextView tx_app_title;

        public TitleAppAndGameViewHolder(View itemView) {
            super(itemView);
            tx_app_title = ((TextView) itemView.findViewById(R.id.tx_app_title));
        }
    }

    class HorizontalGamesViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerView recycler_view_horizontal_games;
        private final RelativeLayout rl_item_horizontal_view;

        public HorizontalGamesViewHolder(View itemView) {
            super(itemView);

            recycler_view_horizontal_games = ((RecyclerView) itemView.findViewById(R.id.recycler_view_horizontal_games));
            rl_item_horizontal_view = ((RelativeLayout) itemView.findViewById(R.id.rl_item_horizontal_view));
        }
    }


}
