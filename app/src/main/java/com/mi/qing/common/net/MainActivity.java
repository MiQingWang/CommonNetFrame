package com.mi.qing.common.net;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.mi.qing.common.net.frame.NetworkLoader;
import com.mi.qing.common.net.frame.bean.BaseApiResult;
import com.mi.qing.common.net.frame.util.RxPermissionsUtil;
import com.mi.qing.common.net.source.impl.TestRxCacheServiceImpl;
import com.mi.qing.common.net.source.impl.TestServiceImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RxPermissionsUtil.checkPermissionsHandler(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE}, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                //权限都同意返回true，否者返回false
            }
        });


        TestServiceImpl.getInstance().getTop().subscribe(data -> {
            Log.e("MainActivity", "subscribe: "+JSON.toJSONString(data));
        }, throwable -> {
            Log.e("MainActivity", "onCreate: ", throwable);
        });




    }

    public void clickNet(View view) {
        Log.e("MainActivity", "clickNet ");
        TestRxCacheServiceImpl.getInstance().getTop().subscribe(data -> {
            Log.e("MainActivity", "subscribe: "+JSON.toJSONString(data));
        }, throwable -> {
            Log.e("MainActivity", "onCreate: ", throwable);
        });


        TestRxCacheServiceImpl.getInstance().getTop().subscribe(new ErrorHandleSubscriber<BaseApiResult<String>>(NetworkLoader.getInstance().getRxErrorHandler()) {
            @Override
            public void onNext(@NonNull BaseApiResult<String> longBaseApiResult) {
                Log.e("MainActivity", "ErrorHandleSubscriber: "+JSON.toJSONString(longBaseApiResult));
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}