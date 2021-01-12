package com.mi.qing.common.net;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.mi.qing.common.net.databinding.ActivityMvvmTestBinding;
import com.mi.qing.common.net.frame.base.BaseMvvmActivity;
import com.mi.qing.common.net.mvvm.AppViewModelFactory;
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
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication(),new TestModel());
        return ViewModelProviders.of(this, factory).get(TestViewModel.class);
    }

    @Override
    public void initViewObservable() {
     mViewDataBinding.userName.setText("懂了不");
    }

    @Override
    public void initData() {


    }


}