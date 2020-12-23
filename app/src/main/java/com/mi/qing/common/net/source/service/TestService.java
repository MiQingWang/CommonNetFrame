package com.mi.qing.common.net.source.service;

import com.mi.qing.common.net.frame.bean.BaseApiResult;

import io.reactivex.Observable;

public interface TestService {

    Observable<BaseApiResult<Object>> getTop();
}
