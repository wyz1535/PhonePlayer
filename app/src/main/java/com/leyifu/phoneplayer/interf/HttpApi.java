package com.leyifu.phoneplayer.interf;


import com.leyifu.phoneplayer.bean.RecommendBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hahaha on 2018/11/8 0008.
 */

public interface HttpApi {

    /**
     *   推荐
     * @param pager
     * @return
     */
    @GET("featured")
    Observable<RecommendBean> getRecommendInter(@Query("p") String pager);

}
