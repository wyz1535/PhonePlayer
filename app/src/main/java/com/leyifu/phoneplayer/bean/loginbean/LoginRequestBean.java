package com.leyifu.phoneplayer.bean.loginbean;

/**
 * Created by hahaha on 2018/11/20 0020.
 */

public class LoginRequestBean {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestBean{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
