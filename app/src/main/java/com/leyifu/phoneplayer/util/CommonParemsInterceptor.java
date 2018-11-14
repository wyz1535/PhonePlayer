package com.leyifu.phoneplayer.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

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

    @Override
    public Response intercept(Chain chain) throws IOException {

        Gson gson = new Gson();

        Request request = chain.request();

        String method = request.method();

        commonParams.put("imei", "451367413131131421");
        commonParams.put("sdk", "26");

        if (method.equals("GET")) {

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

            Log.d(TAG, "intercept: url" + httpUrl + "  request: " + request + " newurl:"+ url);

            request = request.newBuilder().url(url).build();


        } else if (method.equals("POST")) {

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
                    }
                }
            }

        }
        return chain.proceed(request);
    }
}
