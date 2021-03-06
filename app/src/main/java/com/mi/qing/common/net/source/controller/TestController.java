package com.mi.qing.common.net.source.controller;

import com.mi.qing.common.net.frame.bean.BaseApiResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface TestController {

    @Headers({
            "token:false"
    })
    @GET("article/list/0/json")
    Observable<BaseApiResult<Object>> getTop();
}
