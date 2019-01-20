package com.leyifu.phoneplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.act.AppDetailActivity;
import com.leyifu.phoneplayer.act.MyApplication;
import com.leyifu.phoneplayer.bean.categoryAndGoodBean.CategoryAndGoodDatasBean;
import com.leyifu.phoneplayer.constant.Constants;

import java.util.List;

/**
 * Created by hahaha on 2019/1/4 0004.
 */

public class CategoryAppAdatper extends RecyclerView.Adapter<CategoryAppAdatper.ViewHolder> {

    private Context mContext;
    private List<CategoryAndGoodDatasBean> mDatas;
    private int FOOT_TYPE = -1;
    public static final int LOAD_NONE = 0;
    public static final int LOAD_TO_PULL = 1;
    public static final int LOAD_MORE = 2;
    public static final int GAME = 3;
    public static final int CATEGORY = 4;
    private int type;
    private int state = 1;

    public CategoryAppAdatper(Context context, List<CategoryAndGoodDatasBean> datas, int type) {
        this.mContext = context;
        this.mDatas = datas;
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return FOOT_TYPE;
        } else {
            return position;
        }
    }

    @Override
    public CategoryAppAdatper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOT_TYPE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false);
            return new FootViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(CategoryAppAdatper.ViewHolder holder, int position) {

        if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (state) {
                case LOAD_NONE:
                    footViewHolder.progress_bar_foot.setVisibility(View.GONE);
                    footViewHolder.tv_footer_load.setText(R.string.no_more_data);
                    break;
                case LOAD_TO_PULL:
                    footViewHolder.progress_bar_foot.setVisibility(View.GONE);
                    footViewHolder.tv_footer_load.setText(R.string.load_to_pull);
                    break;
                case LOAD_MORE:
                    footViewHolder.progress_bar_foot.setVisibility(View.VISIBLE);
                    footViewHolder.tv_footer_load.setText(R.string.load_more);
                    break;
                default:
                    break;
            }

        } else {

            ViewHolder viewHolder = holder;
            Glide.with(mContext).load(Constants.BASE_IMG_URL + mDatas.get(position).getIcon()).into(viewHolder.iv_icon);
            viewHolder.tv_title.setText(mDatas.get(position).getDisplayName());
            viewHolder.iv_allow.setImageResource(R.drawable.ic_onload_svg);
            final int apId = mDatas.get(position).getId();
            if (type == CATEGORY) {
                viewHolder.tv_type.setVisibility(View.GONE);
            } else if (type == GAME) {
                viewHolder.tv_type.setVisibility(View.VISIBLE);
                viewHolder.tv_type.setText(mDatas.get(position).getLevel1CategoryName());
            }

            viewHolder.rv_item_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AppDetailActivity.class);
                    intent.putExtra(Constants.APP_ID, apId);

                    mContext.startActivity(intent);
                    ((MyApplication) ((Activity) mContext).getApplication()).setView(v);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_icon;
        private final TextView tv_title;
        private final TextView tv_type;
        private final RelativeLayout rv_item_category;
        private final ImageView iv_allow;

        public ViewHolder(View itemView) {
            super(itemView);

            rv_item_category = ((RelativeLayout) itemView.findViewById(R.id.rv_item_category));
            iv_icon = ((ImageView) itemView.findViewById(R.id.iv_icon));
            tv_title = ((TextView) itemView.findViewById(R.id.tv_title));
            tv_type = ((TextView) itemView.findViewById(R.id.tv_type));
            iv_allow = ((ImageView) itemView.findViewById(R.id.iv_allow));
        }
    }

    public class FootViewHolder extends ViewHolder {

        private final LinearLayout ll_footer;
        private final ProgressBar progress_bar_foot;
        private final TextView tv_footer_load;

        public FootViewHolder(View itemView) {
            super(itemView);

            ll_footer = ((LinearLayout) itemView.findViewById(R.id.ll_footer));
            progress_bar_foot = ((ProgressBar) itemView.findViewById(R.id.progress_bar_foot));
            tv_footer_load = ((TextView) itemView.findViewById(R.id.tv_footer_load));
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            itemView.setLayoutParams(params);
        }
    }

    public void upstate(int state) {
        this.state = state;
        notifyDataSetChanged();
    }
}
