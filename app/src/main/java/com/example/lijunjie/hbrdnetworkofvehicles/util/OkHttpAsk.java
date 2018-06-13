package com.example.lijunjie.hbrdnetworkofvehicles.util;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zengwei on 2018/6/12.
 */

public class OkHttpAsk {
    private static Handler handler;
    /**
     * 请求方法
     * @param url
     * @param map
     * @param okHttpAskListener
     */
    public static void requestNet(String url, Map<String ,String> map, final OkHttpAskListener okHttpAskListener){
        handler=new Handler(Looper.getMainLooper());
        if (url==null) {
            return;
        }
        //创建okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //参数传递
        FormBody.Builder formBody=new FormBody.Builder();
        Set<String> keySet=map.keySet();
        for (String i:keySet) {
            //从集合中一一取到对应的key和value
            String str=map.get(i);
            formBody.add(i, str);
        }
        //创建一个Request
        final Request request = new Request.Builder().url(url).post(formBody.build()).build();
        //请求加入调度
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                okHttpAskListener.onError();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(() -> {
                    try {
                        okHttpAskListener.onSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}
