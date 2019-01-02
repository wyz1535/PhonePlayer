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

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.CategoryAdatper;
import com.leyifu.phoneplayer.bean.CategoryBead.CategoryBean;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetCategory;
import com.leyifu.phoneplayer.presenter.Persenter;

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
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCategory.setLayoutManager(linearLayoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        recyclerViewCategory.addItemDecoration(divider);
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());

        Persenter.pGetCategory(this, HttpApi.class);
    }

    @Override
    public void iGetCategorySuccess(CategoryBean categoryBean) {
        Log.e(TAG, "iGetCategorySuccess: " + categoryBean.getMessage() + " state: " + categoryBean.getStatus() + "    " + categoryBean.getData());
        progressBarCategory.setVisibility(View.GONE);
        CategoryAdatper categoryAdatper = new CategoryAdatper(getActivity(), categoryBean.getData());
        recyclerViewCategory.setAdapter(categoryAdatper);
    }

    @Override
    public void iGetCategoryFailed(Throwable throwable) {
        Log.e(TAG, "iGetCategoryFailed: " + throwable);
        progressBarCategory.setVisibility(View.GONE);

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
