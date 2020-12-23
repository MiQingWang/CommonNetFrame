package com.mi.qing.common.net.source.impl;

import com.mi.qing.common.net.frame.base.BaseRxCacheServiceImpl;
import com.mi.qing.common.net.frame.base.BaseServiceImpl;
import com.mi.qing.common.net.frame.bean.BaseApiResult;
import com.mi.qing.common.net.frame.rxbus.RxBus;
import com.mi.qing.common.net.source.controller.TestController;
import com.mi.qing.common.net.source.controller.TestRxCacheController;
import com.mi.qing.common.net.source.service.TestService;

import io.reactivex.Observable;

public class TestRxCacheServiceImpl extends BaseRxCacheServiceImpl<TestRxCacheController> implements TestService {

    private static TestRxCacheServiceImpl instance = null;

    private TestRxCacheServiceImpl() {
        super(TestRxCacheController.class);
    }

    public static TestRxCacheServiceImpl getInstance() {
        if (instance == null) {
            synchronized (TestRxCacheServiceImpl.class) {
                if (instance == null) {
                    instance = new TestRxCacheServiceImpl();
                }
            }
        }
        return instance;
    }


    @Override
    public Observable<BaseApiResult<Object>> getTop() {
        return getApi().getTop(TestServiceImpl.getInstance().getTop()).compose(RxBus.ApplySchedulers());
    }
}



