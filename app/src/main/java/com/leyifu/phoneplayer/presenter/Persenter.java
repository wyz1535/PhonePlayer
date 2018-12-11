package com.leyifu.phoneplayer.presenter;

import com.leyifu.phoneplayer.bean.RecommendBean;
import com.leyifu.phoneplayer.bean.loginbean.LoginBean;
import com.leyifu.phoneplayer.bean.loginbean.LoginRequestBean;
import com.leyifu.phoneplayer.bean.rankingbean.RankingBean;
import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendHomeBean;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetLogin;
import com.leyifu.phoneplayer.interf.IgetRanking;
import com.leyifu.phoneplayer.interf.IgetRecommend;
import com.leyifu.phoneplayer.util.ApiUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by hahaha on 2018/11/8 0008.
 */

public class Persenter {


    public static void getRecommend(final IgetRecommend igetRecommend, Class<HttpApi> httpApiClass, String pager) {

        Observable<RecommendBean> observable = ApiUtils.getRetrofit().create(httpApiClass).getRecommendInter(pager);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<RecommendBean>() {
//                    @Override
//                    public void call(RecommendBean recommendBean) {
//                        igetRecommend.getRecommendSuccess(recommendBean);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        igetRecommend.getRecommendFailed();
//                    }
//                }
//                );
                .subscribe(new Subscriber<RecommendBean>() {

                    @Override
                    public void onStart() {
                        igetRecommend.getRecommendStart();
                    }

                    @Override
                    public void onCompleted() {
                        igetRecommend.getRecommendCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        igetRecommend.getRecommendFailed(e);
                    }

                    @Override
                    public void onNext(RecommendBean recommendBean) {

//                        igetRecommend.getRecommendSuccess(recommendBean);
                    }
                });
    }

    /**
     * 推荐页面 请求数据
     *
     * @param igetRecommend
     * @param httpApiClass
     * @param pager
     */
    public static void getRecommendHome(final IgetRecommend igetRecommend, Class<HttpApi> httpApiClass, String pager) {

        Observable<RecommendHomeBean> observable = ApiUtils.getRetrofit().create(httpApiClass).getRecommendHome(pager);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecommendHomeBean>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        igetRecommend.getRecommendStart();
                    }

                    @Override
                    public void onCompleted() {
                        igetRecommend.getRecommendCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        igetRecommend.getRecommendFailed(e);
                    }

                    @Override
                    public void onNext(RecommendHomeBean recommendHomeBean) {
                        igetRecommend.getRecommendSuccess(recommendHomeBean);
                    }
                });
    }

    /**
     * 排行榜页面请求数据
     *
     * @param igetRanking
     * @param httpApiClass
     * @param pager
     */
    public static void getRanking(final IgetRanking igetRanking, Class<HttpApi> httpApiClass, String pager, final boolean isLoadMore) {

        Observable<RankingBean> observable = ApiUtils.getRetrofit().create(httpApiClass).getRanking(pager);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RankingBean>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        igetRanking.getRankingStart(isLoadMore);
                    }

                    @Override
                    public void onCompleted() {
                        igetRanking.getRankingCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        igetRanking.getRankingFailed(e);
                    }

                    @Override
                    public void onNext(RankingBean rankingBean) {
                        igetRanking.getRankingSuccess(rankingBean, isLoadMore);
                    }
                });
    }

//    public static void pGetLogin(final IgetLogin igetLogin, Class<HttpApi> httpApiClass, String msisdn, String password) {
//    public static void pGetLogin(final IgetLogin igetLogin, Class<HttpApi> httpApiClass, HashMap<String,String> map) {
    public static void pGetLogin(final IgetLogin igetLogin, Class<HttpApi> httpApiClass, LoginRequestBean loginRequestBean) {

//        Log.e("LoginActivity", "msisdn: "+msisdn +"  password: " + password);
        Observable<LoginBean> observable = ApiUtils.getRetrofit().create(httpApiClass).getLogin(loginRequestBean);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LoginBean>() {
                    @Override
                    public void call(LoginBean loginBean) {
                        igetLogin.iGetLoginSuccess(loginBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        igetLogin.iGetLoginFailed(throwable);
                    }
                });

    }


}
