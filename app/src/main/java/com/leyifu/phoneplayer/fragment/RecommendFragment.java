package com.leyifu.phoneplayer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.HomeAdapter;
import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendDataBean;
import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendHomeBean;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetRecommend;
import com.leyifu.phoneplayer.presenter.Persenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment implements IgetRecommend {

    private static final String TAG = "RecommendFragment";
    private static RecommendFragment recommendFragment;

    @BindView(R.id.recycler_view_recommend)
    RecyclerView recyclerViewRecommend;
    Unbinder unbinder;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.btn_net_erro)
    Button btnNetErro;
    @BindView(R.id.ll_net_error)
    LinearLayout llNetError;

    private int pager = 0;

    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment getInstance() {

        if (recommendFragment == null) {

            synchronized (RecommendFragment.class) {

                recommendFragment = new RecommendFragment();
            }
        }

        return recommendFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        unbinder = ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {

        //布局管理器
        recyclerViewRecommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        //分割线
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_line));
//        RecyclerViewDivider divider = new RecyclerViewDivider(30, Color.RED);
        recyclerViewRecommend.addItemDecoration(divider);
        //动画
        recyclerViewRecommend.setItemAnimator(new DefaultItemAnimator());

//        Persenter.getRecommend(this, HttpApi.class, "{'page':" + pager + "}");

        Persenter.getRecommendHome(this, HttpApi.class, "{'page':" + pager + "}");
    }

    @OnClick(R.id.btn_net_erro)
    public void onViewClicked() {
        init();
    }


    @Override
    public void getRecommendSuccess(RecommendHomeBean recommendHomeBean) {

        progressBar.setVisibility(View.VISIBLE);
        llNetError.setVisibility(View.INVISIBLE);

        if (recommendHomeBean != null) {

//            RecommendAdapter recommendAdapter = new RecommendAdapter(getActivity(), recommendBean.getDatas());

            RecommendDataBean dataBean = recommendHomeBean.getData();

            HomeAdapter homeAdapter = new HomeAdapter(getActivity(), dataBean);

            recyclerViewRecommend.setAdapter(homeAdapter);
        }
    }

    @Override
    public void getRecommendFailed(Throwable e) {

        llNetError.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getRecommendStart() {
        progressBar.setVisibility(View.VISIBLE);
        llNetError.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getRecommendCompleted() {
        progressBar.setVisibility(View.INVISIBLE);
        llNetError.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
