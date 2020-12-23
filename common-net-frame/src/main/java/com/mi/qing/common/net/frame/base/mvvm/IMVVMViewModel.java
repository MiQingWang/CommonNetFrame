package com.mi.qing.common.net.frame.base.mvvm;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import org.jetbrains.annotations.NotNull;

public interface IMVVMViewModel extends LifecycleObserver {
    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 用于Presenter中销毁逻辑的处理
     */
    void onDestroy();


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(@NotNull LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(@NotNull LifecycleOwner owner);

}
