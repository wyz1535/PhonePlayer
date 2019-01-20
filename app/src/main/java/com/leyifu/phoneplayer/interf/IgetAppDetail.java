package com.leyifu.phoneplayer.interf;

import com.leyifu.phoneplayer.bean.appDetail.AppDetailBean;

/**
 * Created by hahaha on 2018/12/26 0026.
 */

public interface IgetAppDetail {

    void iGetAppDetailSuccess(AppDetailBean appDetailBean);

    void iGetAppDetailFailed(Throwable throwable);

    void iGetAppDetailStart();

    void  iGetAppDetailComplate();
}
