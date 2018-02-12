package com.tfxiaozi.smartfishtank.activity.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.utils.ToastUtils;
import com.tfxiaozi.smartfishtank.widget.VerticalColorSeekBar;


/**
 * Created by dongqiang on 2016/10/16.
 */

public class ManualSettingActivity extends BaseActivity implements View.OnClickListener, VerticalColorSeekBar.OnStateChangeListener {

    private TextView tvCurrentTemper, tvCurrentBrightness, tvMainTitle;
    private ImageView ivBack;
    private VerticalColorSeekBar vpbInnerTemper;
    private VerticalColorSeekBar vpbBrightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manual_setting);

        initViews();
        initEvents();
        initData();
    }

    private void initViews() {
        tvMainTitle = (TextView) findViewById(R.id.title_main_text);
        tvMainTitle.setText(getString(R.string.manual_setting));
        tvMainTitle.setVisibility(View.VISIBLE);
        ivBack = (ImageView) findViewById(R.id.title_back);
        ivBack.setVisibility(View.VISIBLE);

        tvCurrentTemper = (TextView) findViewById(R.id.tv_current_temper);
        tvCurrentBrightness = (TextView) findViewById(R.id.tv_current_brightness);
        vpbInnerTemper = (VerticalColorSeekBar)findViewById(R.id.vpb_inner_temper);
        vpbBrightness = (VerticalColorSeekBar) findViewById(R.id.vpb_brightness);
        vpbInnerTemper.setColor(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.TRANSPARENT);
        vpbBrightness.setColor(Color.BLUE, Color.WHITE, Color.YELLOW, Color.BLUE, Color.TRANSPARENT);
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        vpbInnerTemper.setOnStateChangeListener(this);
        vpbBrightness.setOnStateChangeListener(this);
    }

    private void initData() {
        vpbInnerTemper.setProgress(50);
        vpbBrightness.setProgress(70);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void OnStateChangeListener(View view, float progress) {


    }

    @Override
    public void onStopTrackingTouch(View view, float progress) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.vpb_inner_temper:
                if (progress < 0) {
                    progress = 0;
                }
                if(progress > 100) {
                    progress = 100;
                }
                ToastUtils.showShort(this, "progress= " + progress);
                //colorSeekBar.setProgress(progress);
                break;

            case R.id.vpb_brightness:
                if (progress < 0) {
                    progress = 0;
                }
                if(progress > 100) {
                    progress = 100;
                }
                ToastUtils.showShort(this, "progress1= " + progress);
                break;
        }

    }
}
