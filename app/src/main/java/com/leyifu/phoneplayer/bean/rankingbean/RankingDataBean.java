package com.leyifu.phoneplayer.bean.rankingbean;

import java.util.List;

/**
 * Created by hahaha on 2018/11/16 0016.
 */
public class RankingDataBean {
    /**
     * hasMore : true
     * datas : []
     * status : 1
     * message : success
     */

    private boolean hasMore;
    private int status;
    private String message;
    private List<AppDatasBean> datas;

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

    public List<AppDatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<AppDatasBean> datas) {
        this.datas = datas;
    }

}
