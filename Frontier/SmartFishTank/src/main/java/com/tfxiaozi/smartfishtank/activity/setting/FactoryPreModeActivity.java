package com.tfxiaozi.smartfishtank.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.utils.ToastUtils;
import com.tfxiaozi.smartfishtank.widget.LabelLayout;

/**
 * Created by dongqiang on 2016/10/16.
 */

public class FactoryPreModeActivity extends BaseActivity implements View.OnClickListener{

    private LabelLayout llModeBaozao, llModeSps, llModeLps, llModeNps, llModeUser;
    private TextView tvMainTile;
    private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory_pre_mode);
        initViews();
        initEvents();
    }

    private void initViews() {
        tvMainTile = (TextView) findViewById(R.id.title_main_text);
        tvMainTile.setVisibility(View.VISIBLE);
        tvMainTile.setText(getString(R.string.factory_pre_mode));
        ivBack = (ImageView) findViewById(R.id.title_back);
        ivBack.setVisibility(View.VISIBLE);
        llModeBaozao = (LabelLayout) findViewById(R.id.ll_mode_baozao);
        llModeSps = (LabelLayout) findViewById(R.id.ll_mode_sps);
        llModeLps = (LabelLayout) findViewById(R.id.ll_mode_lps);
        llModeNps = (LabelLayout) findViewById(R.id.ll_mode_nps);
        llModeUser = (LabelLayout) findViewById(R.id.ll_mode_user);
    }

    private void initEvents() {
        llModeBaozao.setOnClickListener(this);
        llModeSps.setOnClickListener(this);
        llModeLps.setOnClickListener(this);
        llModeNps.setOnClickListener(this);
        llModeUser.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mode_baozao:

                break;
            case R.id.ll_mode_sps:

                break;
            case R.id.ll_mode_lps:

                break;
            case R.id.ll_mode_nps:

                break;
            case R.id.ll_mode_user:
//                ToastUtils.showLong(this, "用户自定义");
                Intent intent = new Intent(FactoryPreModeActivity.this,TimingSettingActivity.class);
                intent.putExtra(TimingSettingActivity.EXTRA__FROM_FALG,TimingSettingActivity.FROM_DISPLAY);
                startActivity(intent);
                break;
            case R.id.title_back:
                finish();
                break;
        }


    }
}
