package com.mi.qing.common.net.frame.base.mvvm;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.mi.qing.common.net.frame.util.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel<M extends IMVVMModel> extends AndroidViewModel implements IMVVMViewModel {
    protected CompositeDisposable mCompositeDisposable;
    protected M mModel;
    private LifecycleOwner lifecycleOwner;
    private BaseViewModelEvent baseViewModelEvent;


    public BaseViewModel(@NonNull Application application) {
        super(application);
        onStart();
    }


    /**
     * 注入Model
     *
     * @param model
     */
    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.mModel = model;
        onStart();
    }


    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        if (null == lifecycleOwner) {
            throw new NullPointerException("lifecycleOwner == null");
        }
        return RxLifecycleUtils.bindLifecycle(lifecycleOwner);
    }


    @Override
    public void onStart() {

    }

    /**
     * 处理框架销毁逻辑
     */
    @Override
    public void onDestroy() {
        unDispose();//解除订阅
        if (mModel != null) {
            mModel.onDestroy();
        }
        this.mModel = null;
        this.mCompositeDisposable = null;
    }


    public BaseViewModelEvent getBaseViewModelEvent() {
        if (baseViewModelEvent == null) {
            baseViewModelEvent = new BaseViewModelEvent();
        }
        return baseViewModelEvent;
    }

    /**
     * 只有当 ViewModel 实现了 {@link LifecycleOwner} 时, 此方法才会被调用
     */
    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {
        Log.e("BaseViewModel", "onCreate: LifecycleOwner ");
        this.lifecycleOwner = owner;

    }

    /**
     * 显示loading
     *
     */
    public void showLoading() {
        getBaseViewModelEvent().getShowLoadingEvent().call();
    }
    /**
     * 隐藏loading
     *
     */
    public void hideLoading() {
        getBaseViewModelEvent().getHideLoadingEvent().call();
    }
    /**
     * 提示词展示
     *
     */
    public void showMessage(String message) {
        getBaseViewModelEvent().getShowMessageEvent().setValue(message);
    }

    /**
     * 关闭页面
     *
     */
    public void finish() {
        getBaseViewModelEvent().getFinishEvent().call();
    }
    /**
     * 页面返回
     *
     */
    public void onBackPressed() {
        getBaseViewModelEvent().getOnBackPressedEvent().call();
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put("className", clz);
        if (bundle != null) {
            params.put("paramsBundle", bundle);
        }
        getBaseViewModelEvent().getStartActivityEvent().postValue(params);
    }


    /**
     * 只有当 ViewModel 实现了 {@link LifecycleOwner} 时, 此方法才会被调用
     */
    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
        Log.e("BaseViewModel", "onDestroy: LifecycleOwner ");
        owner.getLifecycle().removeObserver(this);
    }

    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 View层中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
     * 目前框架已使用 {@link com.uber.autodispose.AutoDispose} 避免内存泄漏,此方法作为备用方案
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入容器集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }


    public final class BaseViewModelEvent extends MVVMCustomViewEvent {

        private MVVMCustomViewEvent<Void> showLoadingEvent;
        private MVVMCustomViewEvent<Void> hideLoadingEvent;
        private MVVMCustomViewEvent<String> showMessageEvent;
        private MVVMCustomViewEvent<Map<String, Object>> startActivityEvent;
        private MVVMCustomViewEvent<Void> finishEvent;
        private MVVMCustomViewEvent<Void> onBackPressedEvent;

        public MVVMCustomViewEvent<Void> getShowLoadingEvent() {
            return showLoadingEvent = createLiveData(showLoadingEvent);
        }

        public MVVMCustomViewEvent<Void> getHideLoadingEvent() {
            return hideLoadingEvent = createLiveData(hideLoadingEvent);
        }

        public MVVMCustomViewEvent<String> getShowMessageEvent() {
            return showMessageEvent = createLiveData(showMessageEvent);
        }

        public MVVMCustomViewEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public MVVMCustomViewEvent<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        public MVVMCustomViewEvent<Void> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }

        private <T> MVVMCustomViewEvent<T> createLiveData(MVVMCustomViewEvent<T> liveData) {
            if (liveData == null) {
                liveData = new MVVMCustomViewEvent<>();
            }
            return liveData;
        }

        @Override
        public void observe(@NotNull LifecycleOwner owner, @NotNull Observer observer) {
            super.observe(owner, observer);
        }


    }

}
