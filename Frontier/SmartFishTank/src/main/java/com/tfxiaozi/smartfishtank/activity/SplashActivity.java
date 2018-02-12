package com.tfxiaozi.smartfishtank.activity;

import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.account.LoginActivity;
import com.tfxiaozi.smartfishtank.utils.IntentUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断用户是否登录
                if(TextUtils.isEmpty(spfManager.getToken())) {
                    IntentUtil.getInstance().startActivity(SplashActivity.this, LoginActivity.class);
                } else {

                }
                finish();
            }
        }, 1000);
    }
}
