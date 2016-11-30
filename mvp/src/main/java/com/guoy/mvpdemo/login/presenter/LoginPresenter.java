package com.guoy.mvpdemo.login.presenter;

/**
 * Author:      Guoy
 * Create Time: 2016/11/30.
 * Description: 登录逻辑处理接口
 */

public interface LoginPresenter {
    boolean Login(String username, String password);
    void Claer();
}
