package com.mi.qing.common.net.frame.base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.mi.qing.common.net.frame.base.mvvm.BaseViewModel;
import com.mi.qing.common.net.frame.base.mvvm.IMVVMView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class BaseMvvmActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements IMVVMView {

    protected V mViewDataBinding;
    protected VM mViewModel;
    private int viewModelId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化页面参数
        initCreate();
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();
        //注册公共调用事件LiveData
        registerCustomViewEvent();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        mViewDataBinding = DataBindingUtil.setContentView(this, initContentView());
        viewModelId = initVariableId();
        mViewModel = initViewModel();
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        mViewDataBinding.setVariable(viewModelId, mViewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        mViewDataBinding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(mViewModel);
    }

    //刷新布局
    public void refreshLayout() {
        if (mViewModel != null) {
            mViewDataBinding.setVariable(viewModelId, mViewModel);
        }
    }


    /**
     * 初始化页面参数
     */
    public abstract void initCreate();

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView();

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }


    public abstract void initViewObservable();


    public abstract void initData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 执行Presenter销毁逻辑
        if (mViewModel != null) {
            mViewModel.onDestroy();
            mViewModel = null;
        }
    }


    protected void registerCustomViewEvent() {
        if (mViewModel != null) {
            mViewModel.getBaseViewModelEvent().getShowLoadingEvent().observe(this, o -> {
                showLoading();
            });

            mViewModel.getBaseViewModelEvent().getHideLoadingEvent().observe(this, o -> {
                hideLoading();
            });

            mViewModel.getBaseViewModelEvent().getShowMessageEvent().observe(this, (Observer<String>) this::showMessage);

            mViewModel.getBaseViewModelEvent().getFinishEvent().observe(this, o -> {
                finish();
            });

            mViewModel.getBaseViewModelEvent().getOnBackPressedEvent().observe(this, o -> {
                onBackPressed();
            });
            mViewModel.getBaseViewModelEvent().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
                @Override
                public void onChanged(@Nullable Map<String, Object> params) {
                    Class<?> clz = (Class<?>) params.get("className");
                    Bundle bundle = (Bundle) params.get("paramsBundle");
                    Intent intent =new Intent(BaseMvvmActivity.this,clz);
                    if (bundle != null) {
                        intent.putExtras(bundle);
                    }
                    startActivity(intent);
                }
            });
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
    public void showMessage(@NotNull String message) {
        // 消息提示
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

}
