package com.mi.qing.common.net;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import com.mi.qing.common.net.frame.base.BaseMvpActivity;
import com.mi.qing.common.net.mvp.TestContract;
import com.mi.qing.common.net.mvp.TestModel;
import com.mi.qing.common.net.mvp.TestPresenter;

public class MvpTestActivity extends BaseMvpActivity<TestPresenter> implements TestContract.View {


    @Override
    protected TestPresenter createPresenter() {
        TestModel testModel = new TestModel();
        return new TestPresenter(testModel, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp_test;
    }

    @SuppressLint("ShowToast")
    @Override
    public void onLoginSuccess(String message) {
        Toast.makeText(MvpTestActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void clickLogin(View view) {
        mPresenter.login();
    }
}