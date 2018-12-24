package com.leyifu.phoneplayer.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.leyifu.phoneplayer.act.MyApplication;
import com.leyifu.phoneplayer.constant.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by hahaha on 2018/11/13 0013.
 */

public class CommonParemsInterceptor implements Interceptor {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final String TAG = "CommonParemsInterceptor";
    Map<String, Object> commonParams = new HashMap<>();
    private Context mContext;


    public CommonParemsInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        mContext = MyApplication.getContext();

        Gson gson = new Gson();

        Request request = chain.request();

        String method = request.method();

        String imei = DeviceUtils.getIMEI(mContext);

        if (imei != null && imei.startsWith("000000")) {
            imei = "5284047f4ffb4e04824a2fd1d1f0cd62";
        }

        commonParams.put(Constants.IMEI, imei);
        commonParams.put(Constants.MODEL, DeviceUtils.getModel());
        commonParams.put(Constants.LANGUAGE, DeviceUtils.getLanguage());
        commonParams.put(Constants.os, DeviceUtils.getBuildVersionIncremental());
        commonParams.put(Constants.RESOLUTION, DensityUtil.getScreenW(mContext) + "*" + DensityUtil.getScreenH(mContext));
        commonParams.put(Constants.SDK, DeviceUtils.getBuildVersionSDK() + "");
        commonParams.put(Constants.DENSITY_SCALE_FACTOR, mContext.getResources().getDisplayMetrics().density + "");

//        String token = ACache.get(mContext).getAsString(Constant.TOKEN);
        commonParams.put(Constants.TOKEN, "");

        if ("GET".equals(method)) {

            HttpUrl httpUrl = request.url();  //原始url

            HashMap<String, Object> rootMap = new HashMap<>();

            Set<String> allParamNames = httpUrl.queryParameterNames();

            for (String allParamName : allParamNames) {

                if ("p".equals(allParamName)) {

                    String oldParamsJson = httpUrl.queryParameter("p");
                    rootMap = gson.fromJson(oldParamsJson, HashMap.class);
                    rootMap.put("publicParams", commonParams);


                } else {
                    rootMap.put(allParamName, httpUrl.queryParameter(allParamName));
                }

            }

            String newParamsJson = gson.toJson(rootMap);

            String url = httpUrl.toString();

            int indexOf = url.indexOf("?");

            if (indexOf > 0) {
                url = url.substring(0, indexOf);
            }

            url = url + "?" + "p=" + newParamsJson;

//            Log.e(TAG, "intercept: url" + httpUrl + "  request: " + request + " newurl:" + url);

            request = request.newBuilder().url(url).build();


        } else if ("POST".equals(method)) {

            RequestBody body = request.body();
            HashMap<String, Object> rootMap = new HashMap<>();
            if (body instanceof FormBody) { // form 表单

                for (int i = 0; i < ((FormBody) body).size(); i++) {

                    rootMap.put(((FormBody) body).encodedName(i), ((FormBody) body).encodedValue(i));
                }
            } else {

                Buffer buffer = new Buffer();

                body.writeTo(buffer);

                String oldJsonParams = buffer.readUtf8();

                if (!TextUtils.isEmpty(oldJsonParams)) {
                    rootMap = gson.fromJson(oldJsonParams, HashMap.class); // 原始参数
                    if (rootMap != null) {
                        rootMap.put("publicParams", commonParams); // 重新组装
                        String newJsonParams = gson.toJson(rootMap); // {"page":0,"publicParams":{"imei":'xxxxx',"sdk":14,.....}}

                        request = request.newBuilder().post(RequestBody.create(JSON, newJsonParams)).build();
//                        Log.e(TAG, "intercept: " + request + " oldJsonParams= " + oldJsonParams + " newJsonParams= " + newJsonParams);
                    }
                }
            }

        }
        Response response = chain.proceed(request);
        return response;
    }

}
