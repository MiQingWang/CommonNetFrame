package com.mi.qing.common.net.source.impl;

import com.mi.qing.common.net.frame.base.BaseServiceImpl;
import com.mi.qing.common.net.frame.bean.BaseApiResult;
import com.mi.qing.common.net.frame.rxbus.RxBus;
import com.mi.qing.common.net.source.controller.TestController;
import com.mi.qing.common.net.source.service.TestService;

import io.reactivex.Observable;

public class TestServiceImpl extends BaseServiceImpl<TestController> implements TestService {

    private static TestServiceImpl instance = null;

    private TestServiceImpl() {
        super(TestController.class);
    }

    public static TestServiceImpl getInstance() {
        if (instance == null) {
            synchronized (TestServiceImpl.class) {
                if (instance == null) {
                    instance = new TestServiceImpl();
                }
            }
        }
        return instance;
    }


    @Override
    public Observable<BaseApiResult<String>> getTop() {
        return getApi().getTop().compose(RxBus.ApplySchedulers());
    }
}



