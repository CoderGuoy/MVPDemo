package com.guoy.mvpdemo.login.view;

/**
 * Author:Guoy
 * Create Time:2016/11/30.
 */

public interface LoginView {
    String getUserName();
    String getPassWord();
    void setUserName(String username);
    void setPassWord(String password);
    void setMessage(String message);
}
