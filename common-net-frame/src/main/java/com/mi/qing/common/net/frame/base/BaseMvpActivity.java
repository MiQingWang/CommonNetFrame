package com.mi.qing.common.net.frame.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mi.qing.common.net.frame.base.mvp.IPresenter;
import com.mi.qing.common.net.frame.base.mvp.IView;

public abstract class BaseMvpActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        // 初始化Presenter
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            //mPresenter绑定Activity生命周期用于内部处理Rxjava流销毁
            getLifecycle().addObserver(mPresenter);
            mPresenter.onStart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 执行Presenter销毁逻辑
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public void showLoading() {
        // 这里实现自己的加载弹框
    }

    @Override
    public void hideLoading() {
        // 取消弹框
    }

    @Override
    public void showMessage(String message) {
        // 消息提示
    }

    /**
     * 创建Presenter
     *
     * @return Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取当前activity的id
     *
     * @return 当前xml的布局res ID
     */
    protected abstract int getLayoutId();

}
