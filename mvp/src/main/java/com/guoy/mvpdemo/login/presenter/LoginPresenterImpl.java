package com.guoy.mvpdemo.login.presenter;

import android.os.Handler;
import android.os.Message;

import com.guoy.mvpdemo.login.view.LoginView;

/**
 * Author:      Guoy
 * Create Time: 2016/11/30.
 * Description: 登录逻辑处理实现类
 */

public class LoginPresenterImpl {
    private LoginView mLoginView;
    private Message msg;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mLoginView.setMessage("恭喜您!账号密码正确!");
                    break;
                case 2:
                    mLoginView.setMessage("对不起!账号密码错误!");
                    break;
            }
        }
    };

    public LoginPresenterImpl(LoginView view) {
        mLoginView = view;
    }

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return true 登录成功 false 登录失败
     */
    public boolean Login(String username, String password) {
        msg = new Message();
        if (username.equals("admin") && password.equals("123")) {
            //登录成功
            msg.what = 1;
            mHandler.sendMessage(msg);
            return true;
        } else {
            //登录失败
            msg.what = 2;
            mHandler.sendMessage(msg);
            return false;
        }
    }

    /**
     * 重新输入
     */
    public void Claer() {
        mLoginView.setUserName("");
        mLoginView.setPassWord("");
        mLoginView.setMessage("");
    }
}
