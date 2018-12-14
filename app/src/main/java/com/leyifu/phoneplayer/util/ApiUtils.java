package com.leyifu.phoneplayer.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leyifu.phoneplayer.bean.loginbean.LoginDataBean;
import com.leyifu.phoneplayer.bean.loginbean.LoginRequestBean;
import com.leyifu.phoneplayer.constant.Constants;
import com.leyifu.phoneplayer.interf.IgetLogin;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hahaha on 2018/11/8 0008.
 */

public class ApiUtils {

    private static Gson mGson = new Gson();
    public static String benSessionId;

    public static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
                .addInterceptor(new CommonParemsInterceptor())
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();
    }


    public static final Retrofit getRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();

        return retrofit;
    }

    public static void okHttpPost(final IgetLogin igetLogin, String url, LoginRequestBean loginRequestBean) {

        String toJson = mGson.toJson(loginRequestBean);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toJson);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                igetLogin.iGetLoginFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //子线程中
                LoginDataBean loginDataBean = mGson.fromJson(response.body().string(), LoginDataBean.class);
                Log.e("onResponse", "onResponse: "+loginDataBean.getMessage()+" : "+loginDataBean.getData().getToken());
            }
        });
    }

}
