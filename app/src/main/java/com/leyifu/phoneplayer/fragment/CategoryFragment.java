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
import com.leyifu.phoneplayer.adapter.CategoryAdatper;
import com.leyifu.phoneplayer.bean.categoryBean.CategoryBean;
import com.leyifu.phoneplayer.constant.Constants;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetCategory;
import com.leyifu.phoneplayer.presenter.Persenter;
import com.leyifu.phoneplayer.util.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements IgetCategory {


    private static CategoryFragment categoryFragment;
    @BindView(R.id.btn_net_erro)
    Button btnNetErro;
    Unbinder unbinder;
    @BindView(R.id.recycler_view_category)
    RecyclerView recyclerViewCategory;
    @BindView(R.id.progress_bar_category)
    ProgressBar progressBarCategory;
    @BindView(R.id.iv_net_errot)
    ImageView ivNetErrot;
    @BindView(R.id.ll_net_error)
    LinearLayout llNetError;
    @BindView(R.id.tv_invalid_token)
    TextView tvInvalidToken;
    private View view;
    private final String TAG = "categoryFragment";

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String msg) {
        if (categoryFragment == null) {
            synchronized (CategoryFragment.class) {
                categoryFragment = new CategoryFragment();
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("message", msg);
        categoryFragment.setArguments(bundle);

        return categoryFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String message = bundle.getString("message");
//            Log.e(TAG, "onCreateView: " + message);
        }

        init();

        return view;
    }

    public void init() {

        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCategory.setLayoutManager(linearLayoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        recyclerViewCategory.addItemDecoration(divider);
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());

        Persenter.pGetCategory(this, HttpApi.class);
        Log.e(TAG, "init: categoryFragment");


    }

    @Override
    public void iGetCategorySuccess(CategoryBean categoryBean,boolean isLoadMore) {
        Log.e(TAG, "iGetCategorySuccess: " + categoryBean.getMessage() + " state: " + categoryBean.getStatus() + "    " + categoryBean.getData());

        if (categoryBean.getStatus() == 1 && categoryBean.getData() != null) {
            CategoryAdatper categoryAdatper = new CategoryAdatper(getActivity(), categoryBean.getData());
            recyclerViewCategory.setAdapter(categoryAdatper);
        } else {
            tvInvalidToken.setVisibility(View.VISIBLE);

            ACache aCache = ACache.get(getActivity());
            aCache.put(Constants.TOKEN, "");
            aCache.put(Constants.USER, "");
        }

        setProgressAndError(true, true);

    }

    @Override
    public void iGetCategoryFailed(Throwable throwable) {

        setProgressAndError(false, true);

    }

    @Override
    public void iGetCategoryStart() {
        setProgressAndError(true, false);
    }

    @Override
    public void iGetCategoryComplate() {
        setProgressAndError(false, false);
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

    @OnClick(R.id.btn_net_erro)
    public void onViewClicked() {
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
