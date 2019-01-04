package com.leyifu.phoneplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.act.CategoryAppActivity;
import com.leyifu.phoneplayer.bean.categoryBean.CategoryDataBean;
import com.leyifu.phoneplayer.constant.Constants;

import java.util.List;

/**
 * Created by hahaha on 2019/1/2 0002.
 */

public class CategoryAdatper extends RecyclerView.Adapter<CategoryAdatper.ViewHolder> {

    private Context mContext;
    private List<CategoryDataBean> mData;


    public CategoryAdatper(Context context, List<CategoryDataBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public CategoryAdatper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdatper.ViewHolder holder, final int position) {
        holder.tv_title.setText(mData.get(position).getName());
        Glide.with(mContext).load(Constants.BASE_IMG_URL + mData.get(position).getIcon()).into(holder.iv_icon);

        holder.rv_item_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CategoryAppActivity.class);
                intent.putExtra(Constants.CATEGORY_APP_ID, mData.get(position).getId());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_icon;
        private final TextView tv_title;
        private final ImageView iv_allow;
        private final RelativeLayout rv_item_category;

        public ViewHolder(View itemView) {
            super(itemView);
            rv_item_category = ((RelativeLayout) itemView.findViewById(R.id.rv_item_category));
            iv_icon = ((ImageView) itemView.findViewById(R.id.iv_icon));
            tv_title = ((TextView) itemView.findViewById(R.id.tv_title));
            iv_allow = ((ImageView) itemView.findViewById(R.id.iv_allow));
        }
    }
}
