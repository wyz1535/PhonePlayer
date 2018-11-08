package com.leyifu.phoneplayer.bean;

import java.util.List;

/**
 * Created by hahaha on 2018/11/8 0008.
 */

public class RecommendBean {


    /**
     * hasMore : false
     * datas : []
     * status : 1
     * message : success
     */

    private boolean hasMore;
    private int status;
    private String message;
    private List<AppBean> datas;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AppBean> getDatas() {
        return datas;
    }

    public void setDatas(List<AppBean> datas) {
        this.datas = datas;
    }

}
