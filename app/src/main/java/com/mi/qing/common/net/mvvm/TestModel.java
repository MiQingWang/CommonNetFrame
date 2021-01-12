package com.mi.qing.common.net.mvvm;

import com.mi.qing.common.net.frame.base.mvvm.IMVVMModel;
import com.mi.qing.common.net.frame.bean.BaseApiResult;
import com.mi.qing.common.net.source.impl.TestServiceImpl;

import io.reactivex.Observable;

public class TestModel extends BaseModel {

    public Observable<BaseApiResult<Object>> getTop() {
        return TestServiceImpl.getInstance().getTop();
    }

}
