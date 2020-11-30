package com.mi.qing.common.net.frame.rxbus;


import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 用RxJava实现事件总线(Event Bus)
 */

public class RxBus {


    public static <T> ObservableTransformer<T, T> ApplySchedulers() {
        return tObservable -> tObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
