package com.leyifu.phoneplayer.bean.categoryAndGoodBean;

import java.util.List;

/**
 * Created by hahaha on 2019/1/3 0003.
 */
public class CategoryAndGoodDataBean {
    /**
     * datas : [{}]
     * hasMore : false
     * message : success
     * status : 1
     */

    private boolean hasMore;
    private String message;
    private int status;
    private List<CategoryAndGoodDatasBean> datas;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
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

    public List<CategoryAndGoodDatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CategoryAndGoodDatasBean> datas) {
        this.datas = datas;
    }

}
