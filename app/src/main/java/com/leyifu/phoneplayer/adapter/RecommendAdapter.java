package com.leyifu.phoneplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.bean.AppBean;
import com.leyifu.phoneplayer.constant.Constants;

import java.util.List;

/**
 * Created by hahaha on 2018/11/8 0008.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private List<AppBean> datas;
    private Context context;

    public RecommendAdapter(Context context, List<AppBean> datas) {

        this.datas = datas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_recomend_app, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(context).load(Constants.BASE_IMG_URL + datas.get(position).getIcon()).into(holder.img_icon);
        holder.text_title.setText(datas.get(position).getDisplayName());
        holder.text_size.setText(datas.get(position).getApkSize() / 1024 / 1024 + " MB");
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img_icon;
        private final TextView text_title;
        private final TextView text_size;
        private final Button btn_dl;

        public ViewHolder(View itemView) {
            super(itemView);

            img_icon = ((ImageView) itemView.findViewById(R.id.img_icon));
            text_title = ((TextView) itemView.findViewById(R.id.text_title));
            text_size = ((TextView) itemView.findViewById(R.id.text_size));
            btn_dl = ((Button) itemView.findViewById(R.id.btn_dl));
        }
    }
}
