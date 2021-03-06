package com.mi.qing.common.net.frame.base.mvp;

import androidx.annotation.NonNull;

public interface IView {

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
