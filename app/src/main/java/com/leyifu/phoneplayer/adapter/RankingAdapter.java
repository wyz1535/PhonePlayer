package com.leyifu.phoneplayer.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.bean.rankingbean.AppDatasBean;
import com.leyifu.phoneplayer.constant.Constants;

import java.util.List;

/**
 * Created by hahaha on 2018/11/16 0016.
 */

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private Context mContext;
    private List<AppDatasBean> mDataBean;

    public static final int TYPE_FOOT = -1;
    public static final int LOAD_NONE = 0;
    public static final int LOAD_TO_PULL = 1;
    public static final int LOAD_MORE = 2;
    private int state = 1;

    public RankingAdapter(Context context, List<AppDatasBean> dataBean) {

        this.mContext = context;
        this.mDataBean = dataBean;
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount();
        if (position + 1 == itemCount) {
            return TYPE_FOOT;
        } else {
            return position;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false);
            return new FootViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.template_recomend_app, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
        } else if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tv_top_list.setText(position + 1 + ". ");
            String displayName = mDataBean.get(position).getDisplayName();
            if (displayName.length() > 10) {
                String substring = displayName.substring(0, 10);
                viewHolder.text_title.setText(substring + "...");
            } else {
                viewHolder.text_title.setText(displayName);
            }

            viewHolder.text_size.setText(mDataBean.get(position).getLevel1CategoryName());
            Glide.with(mContext).load(Constants.BASE_IMG_URL + mDataBean.get(position).getIcon()).into(viewHolder.img_icon);
        }
    }

    @Override
    public int getItemCount() {
        return mDataBean==null?0:mDataBean.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img_icon;
        private final TextView text_title;
        private final TextView text_size;
        private final Button btn_dl;
        private final TextView tv_top_list;
        private final RelativeLayout rl_item_recycler_view;

        public ViewHolder(View itemView) {
            super(itemView);

            img_icon = ((ImageView) itemView.findViewById(R.id.img_icon));
            text_title = ((TextView) itemView.findViewById(R.id.text_title));
            text_size = ((TextView) itemView.findViewById(R.id.text_size));
            btn_dl = ((Button) itemView.findViewById(R.id.btn_dl));
            tv_top_list = ((TextView) itemView.findViewById(R.id.tv_top_list));
            rl_item_recycler_view = ((RelativeLayout) itemView.findViewById(R.id.rl_item_recycler_view));
        }
    }

    class FootViewHolder extends ViewHolder {

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
