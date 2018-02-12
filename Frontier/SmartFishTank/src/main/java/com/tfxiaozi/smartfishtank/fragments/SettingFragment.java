package com.tfxiaozi.smartfishtank.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.setting.CloudSettingActivity;
import com.tfxiaozi.smartfishtank.activity.setting.FactoryPreModeActivity;
import com.tfxiaozi.smartfishtank.activity.setting.ManualSettingActivity;
import com.tfxiaozi.smartfishtank.activity.setting.NightModeSettingActivity;
import com.tfxiaozi.smartfishtank.activity.setting.ThunderLightActivity;
import com.tfxiaozi.smartfishtank.activity.setting.TimingSettingActivity;
import com.tfxiaozi.smartfishtank.activity.setting.VideoWaveActivity;
import com.tfxiaozi.smartfishtank.utils.IntentUtil;
import com.tfxiaozi.smartfishtank.widget.LabelLayout;

/**
 * Created by dongqiang on 2016/9/11.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private TextView tvMainTitle;
    private LabelLayout llAuto, llManual, llThunderLight, llCloud, llTimer, llNightMode, llVideoAndWave;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_frament, container, false);
        context = getActivity();
        initViews(view);
        initData();
        initEvents();
        return view;
    }

    private void initViews(View view) {
        tvMainTitle = (TextView) view.findViewById(R.id.title_main_text);
        tvMainTitle.setVisibility(View.VISIBLE);
        tvMainTitle.setText(getString(R.string.setting));

        llAuto = (LabelLayout) view.findViewById(R.id.ll_auto);
        llManual = (LabelLayout) view.findViewById(R.id.ll_manual);
        llThunderLight = (LabelLayout) view.findViewById(R.id.ll_thunderlight);
        llCloud = (LabelLayout) view.findViewById(R.id.ll_cloud);
        llTimer = (LabelLayout) view.findViewById(R.id.ll_timer);
        llNightMode = (LabelLayout) view.findViewById(R.id.ll_night_mode);
        llVideoAndWave = (LabelLayout) view.findViewById(R.id.ll_video_wave);

    }

    private void initEvents() {
        llAuto.setOnClickListener(this);
        llManual.setOnClickListener(this);
        llThunderLight.setOnClickListener(this);
        llCloud.setOnClickListener(this);
        llTimer.setOnClickListener(this);
        llNightMode.setOnClickListener(this);
        llVideoAndWave.setOnClickListener(this);
    }


    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_auto:
                IntentUtil.getInstance().startActivity(context, FactoryPreModeActivity.class);
                break;
            case R.id.ll_manual:
                IntentUtil.getInstance().startActivity(context, ManualSettingActivity.class);
                break;

            case R.id.ll_thunderlight:
                IntentUtil.getInstance().startActivity(context, ThunderLightActivity.class);
                break;

            case R.id.ll_cloud:
                IntentUtil.getInstance().startActivity(context, CloudSettingActivity.class);
                break;

            case R.id.ll_timer:
                IntentUtil.getInstance().startActivity(context, TimingSettingActivity.class);
                break;
            case R.id.ll_night_mode:
                IntentUtil.getInstance().startActivity(context, NightModeSettingActivity.class);
                break;
            case R.id.ll_video_wave:
                IntentUtil.getInstance().startActivity(context, VideoWaveActivity.class);
                //getActivity().overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                break;
        }
    }
}
