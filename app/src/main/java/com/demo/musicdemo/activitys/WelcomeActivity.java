package com.demo.musicdemo.activitys;

import android.content.Intent;
import android.os.Bundle;

import com.demo.musicdemo.R;
import com.demo.musicdemo.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

// 1.延迟 3s
// 2.跳转页面
public class WelcomeActivity extends BaseActivity {

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        final boolean isLogin = UserUtils.validateUserLogin(this);

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                Log.e("WelcomeActivity", "当前线程为：" + Thread.currentThread());
//                toMain();

                if (isLogin) {
                    toMain();
                } else {
                    toLogin();
                }
            }
        }, 1 * 1000); // 延迟时间
    }

    /**
     * 跳转到 MainActivity
     */
    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * 跳转到 LoginActivity
     */
    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
