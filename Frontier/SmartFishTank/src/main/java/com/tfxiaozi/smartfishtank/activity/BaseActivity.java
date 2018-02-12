package com.tfxiaozi.smartfishtank.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.tfxiaozi.smartfishtank.utils.SpfManager;

/**
 * Created by Administrator on 2016/09/07.
 */
public class BaseActivity extends AppCompatActivity {

    protected SpfManager spfManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //设置竖屏显示
        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        spfManager = new SpfManager(getApplicationContext());



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
