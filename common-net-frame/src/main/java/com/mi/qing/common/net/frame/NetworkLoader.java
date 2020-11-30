package com.mi.qing.common.net.frame;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.mi.qing.common.net.frame.base.BaseResponseErrorListenerImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class NetworkLoader {
    public static final String TAG = NetworkLoader.class.getSimpleName();
    //在retrofit设置api接口时header头设置参数开启
    public static final String DEFAULT_IS_ADD_HEADER = "true";
    private final static Map<String, NetworkLoader> networkLoaderMaps = new HashMap<>();
    private NetworkConfig networkConfig = null;
    private Retrofit retrofit = null;
    private RxCache rxCache = null;
    private RxErrorHandler rxErrorHandler = null;

    private void setNetworkConfig(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public static NetworkLoader getInstance() {
        if (!networkLoaderMaps.containsKey(TAG)) {
            networkLoaderMaps.put(TAG, new NetworkLoader());
        }
        return networkLoaderMaps.get(TAG);
    }

    private void build() {
        if (networkConfig == null) {
            Log.e(TAG, "未设置网络框架配置，清使用NetworkLoader.Build类创建");
        }

        if (TextUtils.isEmpty(networkConfig.getDebugBaseUrl()) || TextUtils.isEmpty(networkConfig.getReleaseBaseUrl())) {
            Log.e(TAG, "未设置网络框架基础请求地址,请配置debug或者release请求地址");
        }
        setRetrofit(new Network(networkConfig).getRetrofitInstance());

        if (networkConfig.getOpenRxCache()) {
            File rxCacheDirectory = new File(networkConfig.getApplication().getCacheDir(), "common_rx_cache");
            if (!rxCacheDirectory.exists()) {
                rxCacheDirectory.mkdirs();
            }
            setRxCache(new RxCache.Builder()
                    .persistence(rxCacheDirectory, new GsonSpeaker()));
        }

        setRxErrorHandler(RxErrorHandler
                .builder()
                .with(networkConfig.getApplication())
                .responseErrorListener(networkConfig.getResponseErrorListener()).build());
    }


    public RxCache getRxCache() {
        return rxCache;
    }

    private void setRxCache(RxCache rxCache) {
        this.rxCache = rxCache;
    }

    private void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public RxErrorHandler getRxErrorHandler() {
        return rxErrorHandler;
    }

    private void setRxErrorHandler(RxErrorHandler rxErrorHandler) {
        this.rxErrorHandler = rxErrorHandler;
    }
    @NonNull
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static class Build {
        private NetworkConfig networkConfig;

        public Build(Application application) {
            networkConfig = new NetworkConfig();
            //设置默认配置
            networkConfig.setApplication(application);
            networkConfig.setLogLevel(HttpLoggingInterceptor.Level.NONE);
            networkConfig.setOpenCache(false);
            networkConfig.setOpenRxCache(false);
            networkConfig.setResponseErrorListener(new BaseResponseErrorListenerImpl());
        }

        public Build setBaseUrl(String releaseBaseUrl, String debugBaseUrl) {
            networkConfig.setReleaseBaseUrl(releaseBaseUrl);
            networkConfig.setDebugBaseUrl(debugBaseUrl);
            return this;
        }

        public Build setLogLevel(HttpLoggingInterceptor.Level logLevel) {
            networkConfig.setLogLevel(logLevel);
            return this;
        }

        public Build setOpenCache(boolean isOpenCache) {
            networkConfig.setOpenCache(isOpenCache);
            return this;
        }

        public Build setOpenRxCache(boolean isOpenRxCache) {
            networkConfig.setOpenRxCache(isOpenRxCache);
            return this;
        }

        public Build setNetworkHeaderParams(NetworkHeaderParams networkHeaderParams) {
            if (networkConfig.getNetworkHeaderParams() == null) {
                networkConfig.setNetworkHeaderParams(new ArrayList<>());
            }
            networkConfig.getNetworkHeaderParams().add(networkHeaderParams);
            return this;
        }

        public Build setOkHttpClientBuild(OkHttpClient.Builder okHttpClientBuild) {
            networkConfig.setOkHttpClientBuild(okHttpClientBuild);
            return this;
        }

        public Build setResponseErrorListener(ResponseErrorListener responseErrorListener) {
            networkConfig.setResponseErrorListener(responseErrorListener);
            return this;
        }


        public Build create() {
            NetworkLoader.getInstance().setNetworkConfig(networkConfig);
            return this;
        }

        public void build(){
            NetworkLoader.getInstance().build();
        }

    }

}
