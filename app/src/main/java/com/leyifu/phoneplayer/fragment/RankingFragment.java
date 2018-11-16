package com.leyifu.phoneplayer.fragment;

import android.os.Bundle;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewRanking.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        recyclerViewRanking.addItemDecoration(divider);
        recyclerViewRanking.setItemAnimator(new DefaultItemAnimator());
        Persenter.getRanking(this, HttpApi.class, "{'page':" + 0 + "}");
    }

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
    public void getRankingSuccess(RankingBean rankingBean) {
        llNetError.setVisibility(View.GONE);
        progressBarRanking.setVisibility(View.GONE);

        List<AppDatasBean> datasBeans = rankingBean.getData().getDatas();
        RankingAdapter rankingAdapter = new RankingAdapter(getActivity(), datasBeans);
        recyclerViewRanking.setAdapter(rankingAdapter);
    }

    @Override
    public void getRankingFailed(Throwable e) {
        Log.e(TAG, "getRankingFailed: " +e);
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
