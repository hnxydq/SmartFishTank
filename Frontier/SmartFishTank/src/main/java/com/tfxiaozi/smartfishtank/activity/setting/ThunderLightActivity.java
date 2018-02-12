package com.tfxiaozi.smartfishtank.activity.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.utils.ToastUtils;

import java.util.Calendar;

/**
 * Created by dongqiang on 2016/10/16.
 */

public class ThunderLightActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener{

    private static final String THUNDER_SWITCH_STATE = "thunder_switch_state";
    private static final String THUNDER_RUN_INSTANT_STATE = "thunder_run_instant_state";
    private static final String TAG = ThunderLightActivity.class.getSimpleName();
    private TextView tvMainTitle;
    private ImageView ivBack;
    private Button btnSave;
    private ImageView ivSwitch;
    private EditText etBeginTime, etEndTime;
    private ImageView ivRunInstant;
    private SharedPreferences spf;
    private boolean isSwitchOn;
    private boolean isRunInstant;
    private Context context;
    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_thunderlight);
        spf = spfManager.getSpf();
        context = this;
        initViews();
        initEvents();

    }

    private void initViews() {
        tvMainTitle = (TextView) findViewById(R.id.title_main_text);
        tvMainTitle.setText(getString(R.string.thunderlight));
        tvMainTitle.setVisibility(View.VISIBLE);
        ivBack = (ImageView) findViewById(R.id.title_back);
        ivBack.setVisibility(View.VISIBLE);
        btnSave = (Button) findViewById(R.id.btn_save);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.sdv_thunder);
        ivSwitch = (ImageView) findViewById(R.id.thunder_mode_switch);
        etBeginTime = (EditText) findViewById(R.id.et_input_begin_time);
        etEndTime = (EditText) findViewById(R.id.et_input_end_time);
        ivRunInstant = (ImageView) findViewById(R.id.iv_thunder_instant_run);
        Uri uri = Uri.parse("res://"+getPackageName()+"/"+R.mipmap.thunder);
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)//路径
                .setAutoPlayAnimations(true)//自动播放动画
                .build();
        simpleDraweeView.setController(draweeController);

        initSwitch();
        initInstantRun();
    }

    private void initEvents() {
        btnSave.setOnClickListener(this);
        ivSwitch.setOnClickListener(this);
        ivRunInstant.setOnClickListener(this);
        etBeginTime.setOnTouchListener(this);
        etEndTime.setOnTouchListener(this);
        ivBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thunder_mode_switch:
                isSwitchOn = !isSwitchOn;
                if(isSwitchOn) {
                    ivSwitch.setImageResource(R.mipmap.icon_switch_on);
                } else {
                    ivSwitch.setImageResource(R.mipmap.icon_switch_off);
                }
                break;
            case R.id.title_back:
                finish();
                break;

            case R.id.iv_thunder_instant_run:
                isRunInstant = !isRunInstant;
                if(isRunInstant) {
                    ivRunInstant.setImageResource(R.mipmap.run_instant_on);
                } else {
                    ivRunInstant.setImageResource(R.mipmap.run_instant_off);
                }
                break;

            case R.id.btn_save:
                spf.edit().putBoolean(THUNDER_SWITCH_STATE, isSwitchOn).commit();
                spf.edit().putBoolean(THUNDER_RUN_INSTANT_STATE, isRunInstant).commit();
                ToastUtils.showShort(context, getString(R.string.save_success));
                break;
        }
    }

    private void initInstantRun() {
        boolean isRunInstant = spf.getBoolean(THUNDER_RUN_INSTANT_STATE, false);
        if(isRunInstant) {
            ivRunInstant.setImageResource(R.mipmap.run_instant_on);
        } else {
            ivRunInstant.setImageResource(R.mipmap.run_instant_off);
        }
    }

    private void initSwitch() {
        boolean isOn = spf.getBoolean(THUNDER_SWITCH_STATE, false);
        if(isOn) {
            ivSwitch.setImageResource(R.mipmap.icon_switch_on);
        } else {
            ivSwitch.setImageResource(R.mipmap.icon_switch_off);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.dialog_time, null);
            final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
            Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
            Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
            builder.setView(view);
            final AlertDialog dialog = builder.create();

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(Calendar.MINUTE);
            if (v.getId() == R.id.et_input_begin_time) {
                final int inType = etBeginTime.getInputType();
                etBeginTime.setInputType(InputType.TYPE_NULL);
                etBeginTime.onTouchEvent(event);
                etBeginTime.setInputType(inType);
                etBeginTime.setSelection(etBeginTime.getText().length());

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(timePicker.getCurrentHour() < 10 ? "0" + timePicker.getCurrentHour() : timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute() < 10 ? "0" + timePicker.getCurrentMinute() : timePicker.getCurrentMinute());
                        etBeginTime.setText(sb);
                        etEndTime.requestFocus();
                        dialog.cancel();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            } else if (v.getId() == R.id.et_input_end_time) {
                int inType = etEndTime.getInputType();
                etEndTime.setInputType(InputType.TYPE_NULL);
                etEndTime.onTouchEvent(event);
                etEndTime.setInputType(inType);
                etEndTime.setSelection(etEndTime.getText().length());
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(timePicker.getCurrentHour()< 10 ? "0" + timePicker.getCurrentHour() : timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute() < 10 ? "0" + timePicker.getCurrentMinute() : timePicker.getCurrentMinute());
                        etEndTime.setText(sb);

                        dialog.cancel();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
            dialog.show();
        }
        return true;
    }
}
