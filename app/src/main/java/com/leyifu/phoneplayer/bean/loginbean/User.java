package com.leyifu.phoneplayer.bean.loginbean;

/**
 * Created by hahaha on 2018/11/19 0019.
 */

public class User {

    private String id;
    private String email;
    private String logo_url;
    private String username;
    private String mobi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobi() {
        return mobi;
    }

    public void setMobi(String mobi) {
        this.mobi = mobi;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", logo_url='" + logo_url + '\'' +
                ", username='" + username + '\'' +
                ", mobi='" + mobi + '\'' +
                '}';
    }
}

