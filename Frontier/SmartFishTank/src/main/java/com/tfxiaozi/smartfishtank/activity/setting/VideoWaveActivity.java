package com.tfxiaozi.smartfishtank.activity.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.utils.ToastUtils;
import com.tfxiaozi.smartfishtank.widget.ImgTextButton;
import com.tfxiaozi.smartfishtank.widget.PowerFreqencyView;

/**
 * Created by dongqiang on 2016/10/16.
 */

public class VideoWaveActivity extends BaseActivity implements View.OnClickListener{

    private static final String POWER_FREQ_MODE = "power_freq_mode";
    private static final String CURRENT_POWER_LEVEL = "current_power_level";
    private static final String CURRENT_FREQUENCY_LEVEL = "current_frequency_level";
    private static final int MODE_POWER = 1;
    private static final int MODE_FREQUENCY = 2;
    private TextView tvMainTitle;
    private ImageView ivBack;
    private ImgTextButton itbFeed, itbMode;
    private Context context;
    private TextView tvAdd, tvModeSwitch, tvReduce;
    private int mode = MODE_POWER;
    private int currentPowerLevel = 0;
    private int currentFrequencyLevel = 0;
    private SharedPreferences spf;
    private PowerFreqencyView powerFreqencyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_wave);
        context = this;
        initViews();
        initEvents();
    }


    private void initViews() {
        tvMainTitle = (TextView) findViewById(R.id.title_main_text);
        tvMainTitle.setText(getString(R.string.videoandwave));
        tvMainTitle.setVisibility(View.VISIBLE);
        ivBack = (ImageView) findViewById(R.id.title_back);
        ivBack.setVisibility(View.VISIBLE);

        itbFeed = (ImgTextButton) findViewById(R.id.itb_fun_feed);
        itbMode = (ImgTextButton) findViewById(R.id.itb_fun_mode);
        itbFeed.setImageResource(R.mipmap.icon_func_off);
        itbFeed.setText(getString(R.string.feed));

        itbMode.setImageResource(R.mipmap.icon_func_on);
        itbMode.setText(getString(R.string.mode1));

        powerFreqencyView = (PowerFreqencyView) findViewById(R.id.power_frequency_view);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        tvModeSwitch = (TextView) findViewById(R.id.tv_mode_switch);
        tvReduce = (TextView) findViewById(R.id.tv_reduce);
        refreshPowerFreqView();
    }


    private void initEvents() {
        ivBack.setOnClickListener(this);
        itbFeed.setOnClickListener(this);
        itbMode.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvModeSwitch.setOnClickListener(this);
        tvReduce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = spf.edit();
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.itb_fun_feed:
                ToastUtils.showShort(context, "feed");
                break;

            case R.id.itb_fun_mode:
                ToastUtils.showShort(context, "mode");
                break;

            case R.id.tv_add:
                if(mode == MODE_POWER) {
                    currentPowerLevel = spf.getInt(CURRENT_POWER_LEVEL, 0);
                    if(currentPowerLevel < 10) {
                        currentPowerLevel++;
                        editor.putInt(CURRENT_POWER_LEVEL, currentPowerLevel).commit();
                    }
                } else {
                    if(currentFrequencyLevel < 10) {
                        currentFrequencyLevel = spf.getInt(CURRENT_FREQUENCY_LEVEL, 0);
                        currentFrequencyLevel++;
                        editor.putInt(CURRENT_FREQUENCY_LEVEL, currentFrequencyLevel).commit();
                    }
                }
                refreshPowerFreqView();
                break;

            case R.id.tv_mode_switch:
                ToastUtils.showShort(context, "switch");
                if(mode == MODE_POWER) {
                    mode = MODE_FREQUENCY;
                    tvModeSwitch.setText(getString(R.string.frequency));
                    editor.putInt(POWER_FREQ_MODE, mode).commit();
                } else {
                    mode = MODE_POWER;
                    tvModeSwitch.setText(getString(R.string.power));
                    editor.putInt(POWER_FREQ_MODE, mode).commit();
                }
                refreshPowerFreqView();
                break;

            case R.id.tv_reduce:
                if(mode == MODE_POWER) {
                    currentPowerLevel = spf.getInt(CURRENT_POWER_LEVEL, 0);
                    if(currentPowerLevel > 0) {
                        currentPowerLevel--;
                        editor.putInt(CURRENT_POWER_LEVEL, currentPowerLevel).commit();
                    }
                } else {
                    currentFrequencyLevel = spf.getInt(CURRENT_FREQUENCY_LEVEL, 0);
                    if(currentFrequencyLevel > 0) {
                        currentFrequencyLevel--;
                        editor.putInt(CURRENT_FREQUENCY_LEVEL, currentFrequencyLevel).commit();
                    }
                }
                refreshPowerFreqView();
                break;
        }
    }


    private void refreshPowerFreqView() {
        spf = spfManager.getSpf();
        mode = spf.getInt(POWER_FREQ_MODE, MODE_POWER);
        powerFreqencyView.setMaxLevel(10);
        currentPowerLevel = spf.getInt(CURRENT_POWER_LEVEL, 0);
        currentFrequencyLevel = spf.getInt(CURRENT_FREQUENCY_LEVEL, 0);
        if(mode == MODE_POWER) {
            powerFreqencyView.setBorderColor(Color.parseColor("#666666"));
            powerFreqencyView.setContentColor(Color.parseColor("#049C8D"));
            powerFreqencyView.setCurrentLevel(currentPowerLevel);
            tvModeSwitch.setText(getString(R.string.power));
        } else {
            powerFreqencyView.setBorderColor(Color.parseColor("#666666"));
            powerFreqencyView.setContentColor(Color.parseColor("#FF0000"));
            powerFreqencyView.setCurrentLevel(currentFrequencyLevel);
            tvModeSwitch.setText(getString(R.string.frequency));
        }
    }
}
