package com.leyifu.phoneplayer.interf;

import com.leyifu.phoneplayer.bean.loginbean.LoginDataBean;

/**
 * Created by hahaha on 2018/11/20 0020.
 */

public interface IgetLogin {

    void iGetLoginSuccess(LoginDataBean loginDataBean);

    void iGetLoginFailed(Throwable throwable);

//    void iGetLoginSuccess(LoginDataBean.LoginBean loginBean);
}
