package com.mi.qing.common.net;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        RxPermissionsUtil.checkPermissionsHandler(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE}, new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) {
//                //权限都同意返回true，否者返回false
//            }
//        });
//
//
//        TestServiceImpl.getInstance().getTop().subscribe(data -> {
//            Log.e("MainActivity", "subscribe: "+JSON.toJSONString(data));
//        }, throwable -> {
//            Log.e("MainActivity", "onCreate: ", throwable);
//        });






    }

    public void clickNet(View view) {
//        Log.e("MainActivity", "clickNet ");
//        TestRxCacheServiceImpl.getInstance().getTop().subscribe(data -> {
//            Log.e("MainActivity", "subscribe: "+JSON.toJSONString(data));
//        }, throwable -> {
//            Log.e("MainActivity", "onCreate: ", throwable);
//        });
//
//
//        TestRxCacheServiceImpl.getInstance().getTop().subscribe(new ErrorHandleSubscriber<BaseApiResult<String>>(NetworkLoader.getInstance().getRxErrorHandler()) {
//            @Override
//            public void onNext(@NonNull BaseApiResult<String> longBaseApiResult) {
//                Log.e("MainActivity", "ErrorHandleSubscriber: "+JSON.toJSONString(longBaseApiResult));
//            }
//        });

        Intent intent = new Intent(this, MvpTestActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}