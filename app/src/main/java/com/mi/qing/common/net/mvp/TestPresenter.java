package com.mi.qing.common.net.mvp;

import com.mi.qing.common.net.frame.base.mvp.BasePresenter;

public class TestPresenter extends BasePresenter<TestContract.Model, TestContract.View> implements TestContract.Presenter {

    /**
     * 注入Model和View
     *
     * @param model
     * @param rootView
     */
    public TestPresenter(TestContract.Model model, TestContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void login() {
        mRootView.showLoading();
        mModel.getTop()
                //使用AutoDispose处理请求销毁，防止内存泄漏
                .as(bindLifecycle())
                .subscribe(data -> {
                    mRootView.onLoginSuccess("登录成功啦");
                    mRootView.hideLoading();
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }
}
