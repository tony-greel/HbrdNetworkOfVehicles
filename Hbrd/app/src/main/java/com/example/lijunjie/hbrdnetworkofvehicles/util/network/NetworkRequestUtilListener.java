package com.example.lijunjie.hbrdnetworkofvehicles.util.network;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by lijunjie on 2018/3/7.
 * 网络请求工具类接口
 */

public interface NetworkRequestUtilListener {
    void onError(Request request, IOException e);

    void onSuccess(Request request, String result);
}
