package com.leyifu.phoneplayer.presenter;

import android.util.Log;

import com.leyifu.phoneplayer.bean.RecommendBean;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetRecommend;
import com.leyifu.phoneplayer.util.ApiUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by hahaha on 2018/11/8 0008.
 */

public class Persenter {


    public static void getRecommend(final IgetRecommend igetRecommend, Class<HttpApi> httpApiClass, String pager) {

        Observable<RecommendBean> observable = ApiUtils.getRetrofit().create(httpApiClass).getRecommendInter(pager);

        Log.e("Persenter", "getRecommend: " + observable);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RecommendBean>() {
                    @Override
                    public void call(RecommendBean recommendBean) {
                        igetRecommend.getRecommendSuccess(recommendBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        igetRecommend.getRecommendFailed();
                    }
                });
    }
}
