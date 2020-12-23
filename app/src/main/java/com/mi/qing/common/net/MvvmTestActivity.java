package com.mi.qing.common.net;

import android.view.View;

import com.mi.qing.common.net.databinding.ActivityMvvmTestBinding;
import com.mi.qing.common.net.frame.base.BaseMvvmActivity;
import com.mi.qing.common.net.mvvm.TestModel;
import com.mi.qing.common.net.mvvm.TestViewModel;
import com.mi.qing.common.net.mvvm.User;

import java.util.Random;

public class MvvmTestActivity extends BaseMvvmActivity<ActivityMvvmTestBinding, TestViewModel> {


    @Override
    public void initCreate() {

    }


    @Override
    public int initContentView() {
        return R.layout.activity_mvvm_test;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public TestViewModel initViewModel() {
        TestModel testModel = new TestModel();
        return new TestViewModel(getApplication(), testModel);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {


    }


}