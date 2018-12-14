package com.leyifu.phoneplayer.bean.loginbean;

/**
 * Created by hahaha on 2018/12/13 0013.
 */

public class LoginDataBean {

    /**
     * data : {}
     * message : success
     * status : 1
     */

    private DataBean data;
    private String message;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
