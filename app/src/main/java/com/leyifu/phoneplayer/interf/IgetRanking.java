package com.leyifu.phoneplayer.interf;

import com.leyifu.phoneplayer.bean.rankingbean.RankingBean;

/**
 * Created by hahaha on 2018/11/16 0016.
 */

public interface IgetRanking {

    void getRankingSuccess(RankingBean rankingBean,boolean isLoadMore);

    void getRankingFailed(Throwable e);

    void getRankingStart();

    void getRankingCompleted();
}
