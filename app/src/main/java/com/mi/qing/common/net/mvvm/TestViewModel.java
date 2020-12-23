package com.mi.qing.common.net.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.mi.qing.common.net.frame.base.binding.command.BindingAction;
import com.mi.qing.common.net.frame.base.binding.command.BindingCommand;
import com.mi.qing.common.net.frame.base.mvvm.BaseViewModel;

import java.util.Random;

public class TestViewModel extends BaseViewModel<TestModel> {

    //用户绑定
    public ObservableField<User> userObservableField = new ObservableField<>();

    public TestViewModel(@NonNull Application application) {
        super(application);
    }

    public TestViewModel(@NonNull Application application, TestModel model) {
        super(application, model);
    }


    //登录按钮的点击事件
    public BindingCommand loginUserClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            loginUser();
        }
    });

    //登录按钮的点击事件
    public BindingCommand updateUserClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            updateUser();
        }
    });


    public void loginUser(){
        //展示Loading
        showLoading();
        mModel.getTop()
                //使用AutoDispose处理请求销毁，防止内存泄漏
                .as(bindLifecycle())
                .subscribe(data -> {
                    Log.e("TestViewModel", "loginUser: "+ JSON.toJSONString(data));
                    hideLoading();
                    User user = new User();
                    user.setUserName("起飞的米青");
                    user.setMoney("100");
                    user.setIconUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201712%2F19%2F20171219234358_VRdrH.thumb.700_0.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611286677&t=1d5e448cc72899d3d1a82f580b2c708b");
                    userObservableField.set(user);
                    showMessage("登录成功");
                }, throwable -> {
                    hideLoading();
                    throwable.printStackTrace();
                });
    }


    public void updateUser(){
        showLoading();
        mModel.getTop()
                //使用AutoDispose处理请求销毁，防止内存泄漏
                .as(bindLifecycle())
                .subscribe(data -> {
                    Log.e("TestViewModel", "updateUser: "+ JSON.toJSONString(data));
                    hideLoading();
                    User user = new User();
                    user.setUserName("起飞的米青");
                    user.setIconUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171114%2F0fc43e9ad58f4a5cb41a018925b0e475.jpeg&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611286806&t=ff6acd8099796b47f65ad3cf6a84c978");
                    user.setMoney((100 + new Random().nextInt(10)) + "");
                    userObservableField.set(user);
                    showMessage("更新成功");
                }, throwable -> {
                    hideLoading();
                    throwable.printStackTrace();
                });
    }
}
