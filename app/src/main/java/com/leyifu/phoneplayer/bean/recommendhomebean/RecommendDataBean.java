package com.leyifu.phoneplayer.bean.recommendhomebean;

import java.util.List;

/**
 * Created by hahaha on 2018/11/15 0015.
 */
public class RecommendDataBean {
    private List<BannersBean> banners;
    private List<RecommendAppsBean> recommendApps;
    private List<RecommendGamesBean> recommendGames;

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<RecommendAppsBean> getRecommendApps() {
        return recommendApps;
    }

    public void setRecommendApps(List<RecommendAppsBean> recommendApps) {
        this.recommendApps = recommendApps;
    }

    public List<RecommendGamesBean> getRecommendGames() {
        return recommendGames;
    }

    public void setRecommendGames(List<RecommendGamesBean> recommendGames) {
        this.recommendGames = recommendGames;
    }

}
