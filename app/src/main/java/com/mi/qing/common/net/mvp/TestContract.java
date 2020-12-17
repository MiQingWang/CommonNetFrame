package com.mi.qing.common.net.mvp;

import com.mi.qing.common.net.frame.base.mvp.IModel;
import com.mi.qing.common.net.frame.base.mvp.IView;
import com.mi.qing.common.net.frame.bean.BaseApiResult;

import io.reactivex.Observable;

public interface TestContract {

    interface View extends IView {
        /**
         * 登录成功
         */
        void onLoginSuccess(String message);

    }

    interface Presenter {
        void login();
    }

    interface Model extends IModel {
        Observable<BaseApiResult<String>> getTop();
    }

}
