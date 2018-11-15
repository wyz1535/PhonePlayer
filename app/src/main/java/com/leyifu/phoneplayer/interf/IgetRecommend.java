package com.leyifu.phoneplayer.interf;

import com.leyifu.phoneplayer.bean.recommendhomebean.RecommendHomeBean;

/**
 * Created by hahaha on 2018/11/8 0008.
 */

public interface IgetRecommend {

//    void getRecommendSuccess(RecommendBean recommendBean);

    void getRecommendSuccess(RecommendHomeBean recommendHomeBean);

    void getRecommendFailed(Throwable e);

    void getRecommendStart();

    void getRecommendCompleted();

}
