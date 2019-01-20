package com.leyifu.phoneplayer.act;

import android.app.Application;
import android.content.Context;
import android.view.View;

/**
 * Created by hahaha on 2018/11/15 0015.
 */

public class MyApplication extends Application {

    private static Context context;
    private View view;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
