package com.leyifu.phoneplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.constant.Constants;

import java.util.List;

/**
 * Created by hahaha on 2019/1/20 0020.
 */

public class ScreenShotAdapter extends RecyclerView.Adapter<ScreenShotAdapter.ViewHolder> {

    private Context mContext;
    private List<String> imgUrl;

    public ScreenShotAdapter(Context context,List<String> imgUrl) {
        this.mContext = context;
        this.imgUrl = imgUrl;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.screen_shot_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(Constants.BASE_IMG_URL + imgUrl.get(position)).into(holder.iv_screen_shot);
    }

    @Override
    public int getItemCount() {
        return imgUrl == null ? 0 : imgUrl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_screen_shot;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_screen_shot = ((ImageView) itemView.findViewById(R.id.iv_screen_shot));
        }
    }
}
