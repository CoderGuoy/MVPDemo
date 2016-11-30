package com.guoy.mvpdemo.login.presenter;

import com.guoy.mvpdemo.login.view.LoginView;

/**
 * Author:      Guoy
 * Create Time: 2016/11/30.
 * Description: 登录逻辑处理实现类
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView view) {
        mLoginView = view;
    }

    /**
     * 点击登录
     */
    @Override
    public boolean Login(String username, String password) {
        if (username == "a" && password == "a") {
            //登录成功
            mLoginView.setMessage("恭喜您!账号密码正确!");
            return true;
        } else {
            //登录失败
            mLoginView.setMessage("对不起!账号密码错误!");
            return false;
        }
    }

    /**
     * 重置输入
     */
    @Override
    public void Claer() {
        mLoginView.setUserName("");
        mLoginView.setPassWord("");
        mLoginView.setMessage("");
    }
}
