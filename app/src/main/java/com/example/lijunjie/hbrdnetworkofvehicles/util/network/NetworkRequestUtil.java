package com.example.lijunjie.hbrdnetworkofvehicles.util.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by lijunjie on 2018/3/7.
 * 网络请求工具类
 */

public class NetworkRequestUtil {
    private static NetworkRequestUtil myOkHttpClient;
    private OkHttpClient okHttpClient;
    private Handler handler;

    private NetworkRequestUtil() {
        okHttpClient = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    public static NetworkRequestUtil getInstance() {
        if (myOkHttpClient == null) {
            synchronized (NetworkRequestUtil.class) {
                if (myOkHttpClient == null) {
                    myOkHttpClient = new NetworkRequestUtil();
                }
            }
        }

        return myOkHttpClient;
    }

    class StringCallBack implements Callback {
        private NetworkRequestUtilListener httpCallBack;
        private Request request;

        public StringCallBack(Request request, NetworkRequestUtilListener httpCallBack) {
            this.request = request;
            this.httpCallBack = httpCallBack;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            final IOException fe = e;
            if (httpCallBack != null) {
                handler.post(() -> httpCallBack.onError(request, fe));
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String result = response.body().string();
            if (httpCallBack != null) {
                handler.post(() -> httpCallBack.onSuccess(request, result));
            }
        }
    }

    public void asyncGet(String url, NetworkRequestUtilListener httpCallBack) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new StringCallBack(request, httpCallBack));
    }


    public void asyncPost(String url, FormBody formBody, NetworkRequestUtilListener httpCallBack) {
        Request request = new Request.Builder().url(url).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new StringCallBack(request, httpCallBack));
    }


}
