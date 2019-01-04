package com.leyifu.phoneplayer.bean.categoryAndGoodBean;

/**
 * Created by hahaha on 2019/1/3 0003.
 */

public class CategoryAndGood {


    /**
     * data : {"datas":[]
     * message : success
     * status : 1
     */

    private CategoryAndGoodDataBean data;
    private String message;
    private int status;

    public CategoryAndGoodDataBean getData() {
        return data;
    }

    public void setData(CategoryAndGoodDataBean data) {
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
