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

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.RecommendAdapter;
import com.leyifu.phoneplayer.bean.RecommendBean;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetRecommend;
import com.leyifu.phoneplayer.presenter.Persenter;
import com.leyifu.phoneplayer.util.ShowUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        recyclerViewRecommend.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //动画
        recyclerViewRecommend.setItemAnimator(new DefaultItemAnimator());

        Persenter.getRecommend(this, HttpApi.class, "{'page':" + pager + "}");

    }

    @Override
    public void getRecommendSuccess(RecommendBean recommendBean) {

        if (recommendBean != null) {

            RecommendAdapter recommendAdapter = new RecommendAdapter(getActivity(), recommendBean.getDatas());
            recyclerViewRecommend.setAdapter(recommendAdapter);
        }
    }

    @Override
    public void getRecommendFailed() {
        ShowUtil.toast(getActivity(), getResources().getString(R.string.net_error));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
