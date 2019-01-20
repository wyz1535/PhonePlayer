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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.CategoryAppAdatper;
import com.leyifu.phoneplayer.bean.categoryAndGoodBean.CategoryAndGood;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetCategoryAndGood;
import com.leyifu.phoneplayer.presenter.Persenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements IgetCategoryAndGood {


    private static final String TAG = "GameFragment";
    private static GameFragment gameFragment;
    @BindView(R.id.recycler_view_category)
    RecyclerView recyclerViewCategory;
    @BindView(R.id.progress_bar_category)
    ProgressBar progressBarCategory;
    @BindView(R.id.iv_net_errot)
    ImageView ivNetErrot;
    @BindView(R.id.btn_net_erro)
    Button btnNetErro;
    @BindView(R.id.ll_net_error)
    LinearLayout llNetError;
    @BindView(R.id.tv_invalid_token)
    TextView tvInvalidToken;
    Unbinder unbinder;
    private View view;
    private int page;
    private LinearLayoutManager linearLayoutManager;
    private CategoryAndGood mCategoryAndGood;
    private CategoryAppAdatper categoryAppAdatper;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance() {

        if (gameFragment == null) {

            synchronized (GameFragment.class) {
                gameFragment = new GameFragment();
            }
        }

        return gameFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);
        init();
        return view;
    }

    private void init() {
        unbinder = ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCategory.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        recyclerViewCategory.addItemDecoration(dividerItemDecoration);
        recyclerViewCategory.setItemAnimator(defaultItemAnimator);

        Persenter.pGetGame(this, HttpApi.class, page, false);

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
    public void igetCateGoodSuccess(CategoryAndGood categoryAndGood, boolean isLoadMore) {
        Log.e(TAG, "iGetCategorySuccess: " + categoryAndGood.getStatus() + "   " + categoryAndGood.getData().isHasMore() + "   " + categoryAndGood.getData().getDatas());

        if (isLoadMore) {

        } else {
            this.mCategoryAndGood = categoryAndGood;
            categoryAppAdatper = new CategoryAppAdatper(getActivity(), categoryAndGood.getData().getDatas(),categoryAppAdatper.GAME);
            recyclerViewCategory.setAdapter(categoryAppAdatper);
        }
    }

    @Override
    public void igetCateGoodFailed(Throwable e) {
        Log.e(TAG, "igetCateGoodFailed: " + e);
        setProgressAndError(false,true);
    }

    @Override
    public void igetCateGoodMore(boolean isLoadMore) {
        if (isLoadMore) {
            progressBarCategory.setVisibility(View.GONE);
        }
    }

    @Override
    public void igetCateGoodStart() {
        setProgressAndError(true,true);
    }

    @Override
    public void igetCateGoodComplete() {
        setProgressAndError(false,false);
    }

    private void setProgressAndError(boolean progressBar, boolean error) {

        if (progressBar) {
            progressBarCategory.setVisibility(View.VISIBLE);
        } else {
            progressBarCategory.setVisibility(View.GONE);
        }

        if (error) {
            llNetError.setVisibility(View.VISIBLE);
        } else {
            llNetError.setVisibility(View.GONE);
        }
    }
}
