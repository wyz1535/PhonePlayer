package com.leyifu.phoneplayer.interf;

import com.leyifu.phoneplayer.bean.categoryBean.CategoryBean;

/**
 * Created by hahaha on 2018/12/26 0026.
 */

public interface IgetCategory {

    void iGetCategorySuccess(CategoryBean categoryBean,boolean isLoadMore);

    void iGetCategoryFailed(Throwable throwable);

    void iGetCategoryStart();

    void  iGetCategoryComplate();
}
