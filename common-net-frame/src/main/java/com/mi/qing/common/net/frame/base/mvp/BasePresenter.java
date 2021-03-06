package com.mi.qing.common.net.frame.base.mvp;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.mi.qing.common.net.frame.util.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<M extends IModel, V extends IView> implements IPresenter {
    protected CompositeDisposable mCompositeDisposable;
    protected M mModel;
    protected V mRootView;
    private LifecycleOwner lifecycleOwner;

    /**
     * 注入Model和View
     *
     * @param model
     * @param rootView
     */
    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mRootView = rootView;
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
        this.mRootView = null;
        this.mCompositeDisposable = null;
    }


    /**
     * 只有 {@code IPresenter} 实现了 {@link LifecycleOwner} 时, 此方法才会被调用
     */
    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {
        Log.e("BasePresenter", "onCreate: LifecycleOwner " );
        this.lifecycleOwner = owner;

    }

    /**
     * 只有并且 {@code IPresenter} 实现了 {@link LifecycleOwner} 时, 此方法才会被调用
     */
    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
        Log.e("BasePresenter", "onDestroy: LifecycleOwner " );
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

}
