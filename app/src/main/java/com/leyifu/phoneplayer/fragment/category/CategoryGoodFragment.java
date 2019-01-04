package com.leyifu.phoneplayer.fragment.category;


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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.adapter.CategoryAppAdatper;
import com.leyifu.phoneplayer.bean.categoryAndGoodBean.CategoryAndGood;
import com.leyifu.phoneplayer.constant.Constants;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetCategoryAndGood;
import com.leyifu.phoneplayer.presenter.Persenter;
import com.leyifu.phoneplayer.util.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryGoodFragment extends Fragment implements IgetCategoryAndGood {

    private static final String TAG = "CategoryGoodFragment";
    private static CategoryGoodFragment categoryGoodFragment;
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
    public static final String APP_ID = "app_id";
    public static final String APP_POSTION = "app_position";
    private int categoryId;
    private int categoryPosition;
    private int page;
    private CategoryAndGood mCategoryAndGood;
    private CategoryAppAdatper categoryAppAdatper;
    private LinearLayoutManager manager;
    private View view;

    public CategoryGoodFragment() {
        // Required empty public constructor
    }

    public static CategoryGoodFragment newInstance(int appId, int position) {

//        if (categoryGoodFragment == null) {
//            synchronized (CategoryGoodFragment.class) {
//                if (categoryGoodFragment == null) {
        categoryGoodFragment = new CategoryGoodFragment();
//                }
//            }
//        }

        Bundle bundle = new Bundle();
        bundle.putInt(APP_ID, appId);
        bundle.putInt(APP_POSTION, position);
        categoryGoodFragment.setArguments(bundle);

        return categoryGoodFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        init();

        return view;
    }

    private void init() {
        unbinder = ButterKnife.bind(this, view);

        Bundle arguments = getArguments();
        if (arguments != null) {
            categoryId = arguments.getInt(APP_ID);
            categoryPosition = arguments.getInt(APP_POSTION);
        }

        manager = new LinearLayoutManager(getActivity());
        recyclerViewCategory.setLayoutManager(manager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        recyclerViewCategory.addItemDecoration(dividerItemDecoration);
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());

        Persenter.getCategoryAndGood(this, HttpApi.class, categoryId, categoryPosition, page, false);

        recyclerViewCategory.addOnScrollListener(onScrollListener);
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == recyclerView.SCROLL_STATE_IDLE) {
                if (categoryAppAdatper != null) {
                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    int itemCount = manager.getItemCount();

                    if (mCategoryAndGood.getData().isHasMore()) {
                        if (lastVisibleItemPosition == 1) {
                            categoryAppAdatper.upstate(categoryAppAdatper.LOAD_NONE);
                        } else if (lastVisibleItemPosition + 1 == itemCount) {
                            categoryAppAdatper.upstate(categoryAppAdatper.LOAD_TO_PULL);
                            categoryAppAdatper.upstate(categoryAppAdatper.LOAD_MORE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    page++;
                                    Persenter.getCategoryAndGood(CategoryGoodFragment.this, HttpApi.class, categoryId, categoryPosition, page, true);
                                }
                            }, 1000);
                        }
                    } else {
                        categoryAppAdatper.upstate(categoryAppAdatper.LOAD_NONE);
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };


    @OnClick(R.id.btn_net_erro)
    public void onViewClicked() {
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void igetCateGoodSuccess(CategoryAndGood categoryAndGood, boolean isLoadMore) {
        Log.e(TAG, "igetCateGoodSuccess: " + categoryAndGood.getData().getDatas().get(0).getId());
        if (categoryAndGood.getStatus() == 1) {

            if (isLoadMore) {
                mCategoryAndGood.getData().getDatas().addAll(categoryAndGood.getData().getDatas());
                categoryAppAdatper.notifyDataSetChanged();
            } else {
                this.mCategoryAndGood = categoryAndGood;
                categoryAppAdatper = new CategoryAppAdatper(getActivity(), categoryAndGood.getData().getDatas(), categoryAppAdatper.CATEGORY);
                recyclerViewCategory.setAdapter(categoryAppAdatper);
            }

        } else {
            tvInvalidToken.setVisibility(View.VISIBLE);
            ACache aCache = ACache.get(getActivity());
            aCache.put(Constants.TOKEN, "");
            aCache.put(Constants.USER, "");
        }
    }

    @Override
    public void igetCateGoodFailed(Throwable e) {
        Log.e(TAG, "igetCateGoodFailed: " + e);

        setProgressAndError(false, true);

    }

    @Override
    public void igetCateGoodMore() {

    }

    @Override
    public void igetCateGoodStart() {
        setProgressAndError(true, false);

    }

    @Override
    public void igetCateGoodComplete() {
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
}
