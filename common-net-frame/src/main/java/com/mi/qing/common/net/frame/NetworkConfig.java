package com.mi.qing.common.net.frame;

import android.app.Application;

import java.util.List;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class NetworkConfig {
    private String releaseBaseUrl; // release地址，根据工具编译的模式自动切换
    private String debugBaseUrl;   // debug地址，根据工具编译的模式自动切换
    private Application application; // Android 全局应用程序主类
    private HttpLoggingInterceptor.Level logLevel;  //网络请求日志打印级别
    private Boolean isOpenCache;  //是否开启网络缓存，无网络时自动返回缓存内容
    private Boolean isOpenRxCache; // 是否开启RxCache数据缓存
    private List<NetworkHeaderParams> networkHeaderParams;  // 自定义配置网络请求header信息
    private  OkHttpClient.Builder okHttpClientBuild; //自定义配置OkhttpClient
    private ResponseErrorListener responseErrorListener;  // 自定义配置全局异常捕获监听

    public String getReleaseBaseUrl() {
        return releaseBaseUrl;
    }

    public void setReleaseBaseUrl(String releaseBaseUrl) {
        this.releaseBaseUrl = releaseBaseUrl;
    }

    public String getDebugBaseUrl() {
        return debugBaseUrl;
    }

    public void setDebugBaseUrl(String debugBaseUrl) {
        this.debugBaseUrl = debugBaseUrl;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public HttpLoggingInterceptor.Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(HttpLoggingInterceptor.Level logLevel) {
        this.logLevel = logLevel;
    }

    public Boolean getOpenCache() {
        return isOpenCache;
    }

    public void setOpenCache(Boolean openCache) {
        isOpenCache = openCache;
    }

    public Boolean getOpenRxCache() {
        return isOpenRxCache;
    }

    public void setOpenRxCache(Boolean openRxCache) {
        isOpenRxCache = openRxCache;
    }

    public List<NetworkHeaderParams> getNetworkHeaderParams() {
        return networkHeaderParams;
    }

    public void setNetworkHeaderParams(List<NetworkHeaderParams> networkHeaderParams) {
        this.networkHeaderParams = networkHeaderParams;
    }

    public OkHttpClient.Builder getOkHttpClientBuild() {
        return okHttpClientBuild;
    }

    public void setOkHttpClientBuild(OkHttpClient.Builder okHttpClientBuild) {
        this.okHttpClientBuild = okHttpClientBuild;
    }

    public ResponseErrorListener getResponseErrorListener() {
        return responseErrorListener;
    }

    public void setResponseErrorListener(ResponseErrorListener responseErrorListener) {
        this.responseErrorListener = responseErrorListener;
    }
}
