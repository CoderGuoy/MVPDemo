package com.guoy.mvpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.guoy.mvpdemo.login.presenter.LoginPresenterImpl;
import com.guoy.mvpdemo.login.view.LoginView;

/**
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0414/4143.html	选择恐惧症的福音！教你认清MVC，MVP和MVVM	泡在网上的日志
 * https://segmentfault.com/a/1190000003927200					            Android MVP模式 简单易懂的介绍方式		    Kaede
 * http://rocko.xyz/2015/02/06/Android%E4%B8%AD%E7%9A%84MVP/			        Android中的MVP					            Rocko
 * http://www.cnblogs.com/BoBoMEe/p/5573447.html    				            浅谈Android架构之MVP,MVVM			        BoBoMEe
 * http://www.linuxidc.com/Linux/2015-10/124622.htm 				            界面之下：还原真实的 MVC、MVP、MVVM 模式	戴嘉华
 * http://blog.csdn.net/vector_yi/article/details/24719873  			        MVP模式在Android开发中的应用			    Vector_Yi
 */
public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {
    private LoginPresenterImpl mLoginPresenter;
    private EditText userName, passWord;
    private Button login, clear;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        initListener();
    }

    private void init() {
        tvMessage = (TextView) findViewById(R.id.tv_message);
        userName = (EditText) findViewById(R.id.et_username);
        passWord = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.btn_login);
        clear = (Button) findViewById(R.id.btn_clear);
    }

    private void initListener() {
        mLoginPresenter = new LoginPresenterImpl(this);
        login.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public String getUserName() {
        return userName.getText().toString();
    }

    @Override
    public String getPassWord() {
        return passWord.getText().toString();
    }

    @Override
    public void setUserName(String username) {
        userName.setText(username);
    }

    @Override
    public void setPassWord(String password) {
        passWord.setText(password);
    }

    @Override
    public void setMessage(String message) {
        tvMessage.setText(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (mLoginPresenter.Login(getUserName(), getPassWord())) {
                    Toast.makeText(this, "恭喜您!账号密码正确!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "对不起!账号密码错误!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_clear:
                mLoginPresenter.Claer();
                break;
        }
    }
}
