package com.mi.qing.common.net;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RxLifecycleTestActivity extends AppCompatActivity {
    private static final String TAG = RxLifecycleTestActivity.class.getSimpleName();
    private Disposable dispose;

//    private final LifecycleProvider<Lifecycle.Event> provider
//            = AndroidLifecycle.createLifecycleProvider(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxlifecycle_test);
        dispose = Observable.interval(0, 1, TimeUnit.MILLISECONDS)
                .doOnDispose(() -> {
                    Log.e(TAG, "Unsubscribing subscription from onCreate()");
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_STOP)))
                .subscribe(data -> {
                    Log.e(TAG, "onCreate: " + data.toString());
                }, Throwable::printStackTrace);


//        dispose = Observable.interval(0, 1, TimeUnit.MILLISECONDS)
//                .doOnDispose(()->{
//                    Log.e(TAG, "Unsubscribing subscription from onCreate()");
//                })
//                .compose(provider.bindUntilEvent(Lifecycle.Event.ON_STOP))
//                .subscribe(data -> {
//                    Log.e(TAG, "onCreate: " + data.toString());
//                }, Throwable::printStackTrace);
//
//
//        dispose = Observable.interval(0, 1, TimeUnit.MILLISECONDS)
//                .doOnDispose(()->{
//                    Log.e(TAG, "Unsubscribing subscription from onCreate()");
//                })
//                .compose(this.bindToLifecycle())
//                .subscribe(data -> {
//                    Log.e(TAG, "onCreate: " + data.toString());
//                }, Throwable::printStackTrace);
//
//        dispose = Observable.interval(0, 1, TimeUnit.MILLISECONDS)
//                .doOnDispose(()->{
//                    Log.e(TAG, "Unsubscribing subscription from onCreate()");
//                })
//                .compose(this.bindUntilEvent(ActivityEvent.STOP))
//                .subscribe(data -> {
//                    Log.e(TAG, "onCreate: " + data.toString());
//                }, Throwable::printStackTrace);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (dispose != null && !dispose.isDisposed()) {
//            dispose.dispose();
            Log.e(TAG, "onStart: Observable订阅是否关闭：" + dispose.isDisposed());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dispose != null) {
            Log.e(TAG, "onResume: Observable订阅是否关闭：" + dispose.isDisposed());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dispose != null) {
            Log.e(TAG, "onPause: Observable订阅是否关闭：" + dispose.isDisposed());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dispose != null) {
            Log.e(TAG, "onStop: Observable订阅是否关闭：" + dispose.isDisposed());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dispose != null) {
            Log.e(TAG, "onDestroy: Observable订阅是否关闭：" + dispose.isDisposed());
        }
    }

    public void back(View view) {
        finish();
    }
}