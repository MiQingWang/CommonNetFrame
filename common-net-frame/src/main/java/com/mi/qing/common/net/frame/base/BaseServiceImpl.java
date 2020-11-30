package com.mi.qing.common.net.frame.base;

import android.content.Context;
import android.util.Log;

import com.mi.qing.common.net.frame.NetworkLoader;

public class BaseServiceImpl<Controller> {

    private Controller controller;

    public BaseServiceImpl(Class<Controller> clazz) {
        try {
            controller =  NetworkLoader.getInstance().getRetrofit().create(clazz);
        }catch (Exception e){
            Log.e(NetworkLoader.TAG, "RetrofitError: 配置网络框架时未执行build初始化,请检查" );
        }

    }

    protected Controller getApi() {
        return controller;
    }

}
