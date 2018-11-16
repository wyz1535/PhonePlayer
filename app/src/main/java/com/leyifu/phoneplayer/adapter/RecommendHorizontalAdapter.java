package com.leyifu.phoneplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendGamesBean;
import com.leyifu.phoneplayer.constant.Constants;
import com.leyifu.phoneplayer.util.ShowUtil;

import java.util.List;

/**
 * Created by hahaha on 2018/11/16 0016.
 */

public class RecommendHorizontalAdapter extends RecyclerView.Adapter<RecommendHorizontalAdapter.ViewHolder> {

    private Context mContext;
    private List<RecommendGamesBean> mRecommendGames;


    public RecommendHorizontalAdapter(Context context, List<RecommendGamesBean> recommendGames) {
        this.mContext = context;
        this.mRecommendGames = recommendGames;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_games, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = holder;
        Glide.with(mContext).load(Constants.BASE_IMG_URL + mRecommendGames.get(position).getIcon()).into(viewHolder.iv_games);
        String displayName = mRecommendGames.get(position).getDisplayName();
        if (displayName.length() > 4) {
            String substring = displayName.substring(0, 4);
            viewHolder.tx_games.setText(substring);
        } else {
            viewHolder.tx_games.setText(displayName);
        }

        onClickEvent(viewHolder.ll_item_horizontal_view, displayName);
    }

    private void onClickEvent(LinearLayout view, final String displayName) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/11/16 0016 首页horizontal点击事件
                ShowUtil.toast(mContext, displayName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecommendGames.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_games;
        private final TextView tx_games;
        private final LinearLayout ll_item_horizontal_view;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_games = ((ImageView) itemView.findViewById(R.id.iv_games));
            tx_games = ((TextView) itemView.findViewById(R.id.tx_games));
            ll_item_horizontal_view = ((LinearLayout) itemView.findViewById(R.id.ll_item_horizontal_view));
        }
    }

}
