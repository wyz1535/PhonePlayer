package com.leyifu.phoneplayer.presenter;

import com.leyifu.phoneplayer.bean.RecommendBean;
import com.leyifu.phoneplayer.bean.appDetail.AppDetailBean;
import com.leyifu.phoneplayer.bean.categoryAndGoodBean.CategoryAndGood;
import com.leyifu.phoneplayer.bean.categoryBean.CategoryBean;
import com.leyifu.phoneplayer.bean.loginbean.LoginDataBean;
import com.leyifu.phoneplayer.bean.loginbean.LoginRequestBean;
import com.leyifu.phoneplayer.bean.rankingbean.RankingBean;
import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendHomeBean;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetAppDetail;
import com.leyifu.phoneplayer.interf.IgetCategory;
import com.leyifu.phoneplayer.interf.IgetCategoryAndGood;
import com.leyifu.phoneplayer.interf.IgetLogin;
import com.leyifu.phoneplayer.interf.IgetRanking;
import com.leyifu.phoneplayer.interf.IgetRecommend;
import com.leyifu.phoneplayer.util.ApiUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by hahaha on 2018/11/8 0008.
 */

public class Persenter {


    private static Observable<CategoryAndGood> observable;

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

    /**
     * 登陆
     *
     * @param igetLogin
     * @param httpApiClass
     * @param loginRequestBean 登陆参数
     */
    public static void pGetLogin(final IgetLogin igetLogin, Class<HttpApi> httpApiClass, LoginRequestBean loginRequestBean) {

        Observable<LoginDataBean> observable = ApiUtils.getRetrofit().create(httpApiClass).getLogin(loginRequestBean);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginDataBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        igetLogin.iGetShowLoading();
                    }

                    @Override
                    public void onCompleted() {
                        igetLogin.iGetCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        igetLogin.iGetLoginFailed(e);
                    }

                    @Override
                    public void onNext(LoginDataBean loginDataBean) {
                        igetLogin.iGetLoginSuccess(loginDataBean);
                    }
                });
    }


    /**
     * 分类
     *
     * @param igetCategory
     * @param httpApiClass
     */
    public static void pGetCategory(final IgetCategory igetCategory, Class<HttpApi> httpApiClass) {


        Observable<CategoryBean> observable = ApiUtils.getRetrofit().create(httpApiClass).getCategory();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CategoryBean>() {
                               @Override
                               public void onStart() {
                                   super.onStart();
                                   igetCategory.iGetCategoryStart();
                               }

                               @Override
                               public void onCompleted() {
                                   igetCategory.iGetCategoryComplate();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   igetCategory.iGetCategoryFailed(e);
                               }

                               @Override
                               public void onNext(CategoryBean categoryBean) {
                                   igetCategory.iGetCategorySuccess(categoryBean, false);
                               }
                           }
                );
    }


    public static void getCategoryAndGood(final IgetCategoryAndGood igetCategoryAndGood, Class<HttpApi> httpApiClass, int categoryId, int categoryPosition, int page, final boolean isLoadMore) {

        if (categoryPosition == 0) {
            observable = ApiUtils.getRetrofit().create(httpApiClass).getCategoryAndGood(categoryId, page);
        } else if (categoryPosition == 1) {
            observable = ApiUtils.getRetrofit().create(httpApiClass).getCategoryAndTop(categoryId, page);
        } else if (categoryPosition == 2) {
            observable = ApiUtils.getRetrofit().create(httpApiClass).getCategoryAndNewList(categoryId, page);
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CategoryAndGood>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        igetCategoryAndGood.igetCateGoodStart();
                        igetCategoryAndGood.igetCateGoodMore(isLoadMore);
                    }

                    @Override
                    public void onCompleted() {
                        igetCategoryAndGood.igetCateGoodComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        igetCategoryAndGood.igetCateGoodFailed(e);
                    }

                    @Override
                    public void onNext(CategoryAndGood categoryAndGood) {
                        igetCategoryAndGood.igetCateGoodSuccess(categoryAndGood, isLoadMore);
                    }
                });
    }

    public static void pGetGame(final IgetCategoryAndGood igetCategoryAndGood, Class<HttpApi> httpApiClass, int page, final boolean isLoadMore) {

        Observable<CategoryAndGood> observable = ApiUtils.getRetrofit().create(httpApiClass).getGame(page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CategoryAndGood>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        igetCategoryAndGood.igetCateGoodStart();
                    }

                    @Override
                    public void onCompleted() {
                        igetCategoryAndGood.igetCateGoodComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        igetCategoryAndGood.igetCateGoodFailed(e);
                    }

                    @Override
                    public void onNext(CategoryAndGood categoryAndGood) {
                        igetCategoryAndGood.igetCateGoodSuccess(categoryAndGood, isLoadMore);
                    }
                });
    }

    public static void pGetAppDetail(final IgetAppDetail igetAppDetail, Class<HttpApi> httpApiClass, int appId) {

        Observable<AppDetailBean> observable = ApiUtils.getRetrofit().create(httpApiClass).getAppDetail(appId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AppDetailBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        igetAppDetail.iGetAppDetailStart();
                    }

                    @Override
                    public void onCompleted() {
                        igetAppDetail.iGetAppDetailComplate();
                    }

                    @Override
                    public void onError(Throwable e) {
                        igetAppDetail.iGetAppDetailFailed(e);
                    }

                    @Override
                    public void onNext(AppDetailBean appDetailBean) {
                        igetAppDetail.iGetAppDetailSuccess(appDetailBean);
                    }
                });

    }
}
