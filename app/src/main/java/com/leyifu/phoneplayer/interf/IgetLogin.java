package com.leyifu.phoneplayer.interf;

import com.leyifu.phoneplayer.bean.loginbean.LoginBean;

/**
 * Created by hahaha on 2018/11/20 0020.
 */

public interface IgetLogin {

    void iGetLoginSuccess(LoginBean loginBean);

    void iGetLoginFailed(Throwable throwable);
}
