package com.leyifu.phoneplayer.interf;

import com.leyifu.phoneplayer.bean.categoryAndGoodBean.CategoryAndGood;

/**
 * Created by hahaha on 2019/1/3 0003.
 */

public interface IgetCategoryAndGood {

    void igetCateGoodSuccess(CategoryAndGood categoryAndGood, boolean isLoadMore);

    void igetCateGoodFailed(Throwable e);

    void igetCateGoodMore(boolean isLoadMore);

    void igetCateGoodStart();

    void igetCateGoodComplete();
}
