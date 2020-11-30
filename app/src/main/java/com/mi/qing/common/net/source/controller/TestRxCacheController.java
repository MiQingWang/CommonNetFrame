package com.mi.qing.common.net.source.controller;

import com.mi.qing.common.net.frame.bean.BaseApiResult;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.LifeCache;
import io.rx_cache2.ProviderKey;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface TestRxCacheController {
    @ProviderKey("app-net-top")
    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)   //缓存有效期5分钟
    Observable<BaseApiResult<String>> getTop(Observable<BaseApiResult<String>> top);
}
