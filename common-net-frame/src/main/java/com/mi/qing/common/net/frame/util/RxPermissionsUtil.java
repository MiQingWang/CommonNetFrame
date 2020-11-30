package com.mi.qing.common.net.frame.util;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

/**
 * Rx权限获取工具类
 */
public class RxPermissionsUtil {

    /**
     * fragment中使用
     * 验证权限是否获取成功
     * @param fragment  当前页面
     * @param permissionsGroup 多个权限数组
     * @param booleanConsumer 回调权限是否全部获取
     * @return
     */
    public static Disposable checkPermissionsHandler(@NonNull Fragment fragment, String[] permissionsGroup, Consumer<Boolean> booleanConsumer) {
        RxPermissions rxPermissions = new RxPermissions(fragment);
        return rxPermissions.request(permissionsGroup)
                .subscribe(booleanConsumer::accept);
    }

    /**
     * activity中使用
     * 验证权限是否获取成功
     * @param fragmentActivity 当前页面
     * @param permissionsGroup 多个权限数组
     * @param booleanConsumer 回调权限是否全部获取
     * @return
     */
    public static Disposable checkPermissionsHandler(@NonNull FragmentActivity fragmentActivity, String[] permissionsGroup, Consumer<Boolean> booleanConsumer) {
        RxPermissions rxPermissions = new RxPermissions(fragmentActivity);
        return rxPermissions.request(permissionsGroup)
                .subscribe(booleanConsumer::accept);
    }

    /**
     * 回收rx资源
     * @param disposable 创建的资源
     */
    public static void destroyDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


}
