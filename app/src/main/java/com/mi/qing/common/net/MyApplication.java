package com.mi.qing.common.net;

import android.app.Application;

import com.mi.qing.common.net.frame.NetworkLoader;
import com.mi.qing.common.net.source.impl.CustomResponseErrorListenerImpl;

import okhttp3.logging.HttpLoggingInterceptor;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new NetworkLoader.Build(this)
                .setBaseUrl("https://www.wanandroid.com/","https://www.wanandroid.com/")
                .setLogLevel(HttpLoggingInterceptor.Level.NONE)
                .setOpenCache(true)
                .setOpenRxCache(true)
                //自定义token设置
                .setNetworkHeaderParams(new TokenParams())
                //自定义网络异常处理
                 .setResponseErrorListener(new CustomResponseErrorListenerImpl())
                .create().build();
    }
}
