package com.leyifu.phoneplayer.bean;

/**
 * Created by hahaha on 2018/11/7 0007.
 */

public class FragmentInfo {

    private String titles;

    private Class fragment;

    public FragmentInfo() {
    }

    public FragmentInfo(String titles, Class fragment) {
        this.titles = titles;
        this.fragment = fragment;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
