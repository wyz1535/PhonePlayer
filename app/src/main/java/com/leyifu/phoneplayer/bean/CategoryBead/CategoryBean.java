package com.leyifu.phoneplayer.bean.CategoryBead;

import java.util.List;

/**
 * Created by hahaha on 2018/12/26 0026.
 */

public class CategoryBean {


    /**
     * data : []
     * message : success
     * status : 1
     */

    private String message;
    private int status;
    private java.util.List<CategoryDataBean> data;

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

    public List<CategoryDataBean> getData() {
        return data;
    }

    public void setData(List<CategoryDataBean> data) {
        this.data = data;
    }
}
