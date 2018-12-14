package com.leyifu.phoneplayer.bean.loginbean;

import java.io.Serializable;

/**
 * Created by hahaha on 2018/12/13 0013.
 */
public class UserBean implements Serializable{
    /**
     * id : 276740
     * logo_url : //wx.qlogo.cn/mmopen/vi_32/dTGkyA4IBIVoouZtz9OQHGJgoTRxR11QSEflgyPOKXK3cmOibokCa3dPIdibibTZibVsRVco37Fia1iaRkyvj7nM9sow/0
     * mobi : 15527961535
     * username : 168wyz
     */

    private int id;
    private String logo_url;
    private String mobi;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getMobi() {
        return mobi;
    }

    public void setMobi(String mobi) {
        this.mobi = mobi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
