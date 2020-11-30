package com.mi.qing.common.net.frame;

import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Network {
    private final String TAG = Network.class.getSimpleName();
    //缓存最大容量
    private static final int CACHE_MAX_SIZE = 10 * 1024 * 1024;
    private final NetworkConfig networkConfig;

    protected Network(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    private OkHttpClient getOkHttpClientInstance() {
        //网络请求日志打印拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Log.e(TAG, message));
        //设置外部配置打印级别
        httpLoggingInterceptor.level(networkConfig.getLogLevel());
        OkHttpClient.Builder builder;

        //如果外部引用自定义了OkHttpClient.Builder 这里处理合并，否则走框架默认创建方法
        if (networkConfig.getOkHttpClientBuild() != null) {
            builder = networkConfig.getOkHttpClientBuild();
        } else {
            builder = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);
        }
        //外部引用如果开启缓存，需要设置缓存拦截器，无网络时自动获取缓存数据
        if (networkConfig.getOpenCache()) {
            File httpCacheDirectory = new File(networkConfig.getApplication().getCacheDir(), "common_net_cache");
            try {
                Cache cache = new Cache(httpCacheDirectory, CACHE_MAX_SIZE);
                builder.cache(cache);
            } catch (Exception e) {
                Log.e(TAG, "Could not create http cache", e);
            }

            builder.addInterceptor(new NetworkCacheInterceptor(networkConfig.getApplication()));
        }
        return builder
                //框架动态设置header头信息拦截器
                .addInterceptor(new NetworkInterceptor(networkConfig.getNetworkHeaderParams()))
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();
    }


    protected Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.DEBUG ? networkConfig.getDebugBaseUrl() : networkConfig.getReleaseBaseUrl())
                .client(getOkHttpClientInstance())
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
