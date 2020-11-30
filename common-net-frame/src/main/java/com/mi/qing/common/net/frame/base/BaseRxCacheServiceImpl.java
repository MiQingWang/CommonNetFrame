package com.mi.qing.common.net.frame.base;

import android.util.Log;

import com.mi.qing.common.net.frame.NetworkLoader;

public class BaseRxCacheServiceImpl<RxCacheController> {

    private RxCacheController rxCacheController;

    public BaseRxCacheServiceImpl(Class<RxCacheController> clazz) {
        try {
            rxCacheController =  NetworkLoader.getInstance().getRxCache().using(clazz);
        }catch (Exception e){
            Log.e(NetworkLoader.TAG, "RxCacheError: 配置网络框架时未开启RxCache配置,请检查" );
        }
    }

    protected RxCacheController getApi() {
        return rxCacheController;
    }

}
