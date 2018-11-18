package com.leyifu.phoneplayer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.RankingAdapter;
import com.leyifu.phoneplayer.bean.rankingbean.AppDatasBean;
import com.leyifu.phoneplayer.bean.rankingbean.RankingBean;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetRanking;
import com.leyifu.phoneplayer.presenter.Persenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class RankingFragment extends Fragment implements IgetRanking {

    private static final String TAG = "RankingFragment";
    private static RankingFragment rankingFragment;
    @BindView(R.id.recycler_view_ranking)
    RecyclerView recyclerViewRanking;
    @BindView(R.id.progress_bar_ranking)
    ProgressBar progressBarRanking;
    @BindView(R.id.btn_net_erro)
    Button btnNetErro;
    Unbinder unbinder;
    @BindView(R.id.ll_net_error)
    LinearLayout llNetError;
    private RankingAdapter rankingAdapter;
    private LinearLayoutManager layoutManager;
    private RankingBean mRankingBean;

    private int pager = 0;

    public RankingFragment() {
        // Required empty public constructor
    }

    public static RankingFragment newInstance() {

        if (rankingFragment == null) {
            synchronized (RankingFragment.class) {
                rankingFragment = new RankingFragment();
            }
        }
        return rankingFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewRanking.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        recyclerViewRanking.addItemDecoration(divider);
        recyclerViewRanking.setItemAnimator(new DefaultItemAnimator());
        Persenter.getRanking(this, HttpApi.class, "{'page':" + pager + "}",false);

        recyclerViewRanking.addOnScrollListener(onScrollListener);
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (rankingAdapter != null) {
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    int itemCount = layoutManager.getItemCount();
                    if (mRankingBean.getData().isHasMore()) {
                        if (lastVisibleItemPosition == 1) {
                            rankingAdapter.upstate(rankingAdapter.LOAD_NONE);
                            return;
                        } else if (lastVisibleItemPosition + 1 == itemCount) {
                            rankingAdapter.upstate(rankingAdapter.LOAD_TO_PULL);
                            rankingAdapter.upstate(rankingAdapter.LOAD_MORE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pager++;
                                    Persenter.getRanking(RankingFragment.this, HttpApi.class, "{'page':" + pager + "}",true);
                                }
                            }, 1000);

                        }
                    } else {
                        rankingAdapter.upstate(rankingAdapter.LOAD_NONE);
                        return;
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_net_erro)
    public void onViewClicked() {
        init();
    }

    @Override
    public void getRankingSuccess(RankingBean rankingBean,boolean isLoadMore) {
        llNetError.setVisibility(View.GONE);
        progressBarRanking.setVisibility(View.GONE);
        List<AppDatasBean> datasBeans = rankingBean.getData().getDatas();
        if (isLoadMore) {
            mRankingBean.getData().getDatas().addAll(rankingBean.getData().getDatas());
            rankingAdapter.notifyDataSetChanged();
        } else {
            this.mRankingBean = rankingBean;
            rankingAdapter = new RankingAdapter(getActivity(), datasBeans);
            recyclerViewRanking.setAdapter(rankingAdapter);
        }
    }

    @Override
    public void getRankingFailed(Throwable e) {
        Log.e(TAG, "getRankingFailed: " + e);
        llNetError.setVisibility(View.VISIBLE);
        progressBarRanking.setVisibility(View.GONE);
    }

    @Override
    public void getRankingStart() {
        llNetError.setVisibility(View.GONE);
        progressBarRanking.setVisibility(View.VISIBLE);
    }

    @Override
    public void getRankingCompleted() {
        llNetError.setVisibility(View.GONE);
        progressBarRanking.setVisibility(View.GONE);
    }
}
