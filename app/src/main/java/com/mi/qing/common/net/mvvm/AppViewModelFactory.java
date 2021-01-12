package com.mi.qing.common.net.mvvm;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mi.qing.common.net.frame.base.mvvm.IMVVMModel;


/**
 * Created by goldze on 2019/3/26.
 */
public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final BaseModel baseModel;

    public static AppViewModelFactory  getInstance(Application application, BaseModel baseModel) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppViewModelFactory(application,baseModel);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private AppViewModelFactory(Application application, BaseModel baseModel) {
        this.mApplication = application;
        this.baseModel = baseModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TestViewModel.class)) {
            return (T) new TestViewModel(mApplication, (TestModel) baseModel);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
