package com.leyifu.phoneplayer.bean.loginbean;

/**
 * Created by hahaha on 2018/11/19 0019.
 */

public class LoginBean {

    private String token;
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
