package com.mi.qing.common.net.mvp;

import com.mi.qing.common.net.frame.bean.BaseApiResult;
import com.mi.qing.common.net.source.impl.TestServiceImpl;

import io.reactivex.Observable;

public class TestModel implements TestContract.Model {


    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<BaseApiResult<Object>> getTop() {
        return TestServiceImpl.getInstance().getTop();
    }
}
