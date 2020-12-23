package com.mi.qing.common.net.frame.base.mvvm;

import androidx.annotation.NonNull;

public interface IMVVMView {

    /**
     * 显示加载
     */
    default void showLoading() {

    }

    /**
     * 隐藏加载
     */
    default void hideLoading() {

    }

    /**
     * 显示信息
     */
    void showMessage(@NonNull String message);

}
