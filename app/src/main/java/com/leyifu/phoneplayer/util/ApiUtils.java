package com.leyifu.phoneplayer.util;

import com.google.gson.GsonBuilder;
import com.leyifu.phoneplayer.constant.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hahaha on 2018/11/8 0008.
 */

public class ApiUtils {

    public static final Retrofit getRetrofit() {

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit;
    }
}
