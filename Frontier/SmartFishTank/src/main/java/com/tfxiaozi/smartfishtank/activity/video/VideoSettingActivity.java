package com.tfxiaozi.smartfishtank.activity.video;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.constants.Constant;
import com.tfxiaozi.smartfishtank.model.LocalWifiConfig;
import com.tfxiaozi.smartfishtank.model.SearchDeviceConfig;
import com.tfxiaozi.smartfishtank.model.SearchDeviceConfigResp;
import com.tfxiaozi.smartfishtank.net.ConfigWifiThread;
import com.tfxiaozi.smartfishtank.net.SearchDeviceThread;
import com.tfxiaozi.smartfishtank.utils.NetworkUtils;
import com.tfxiaozi.smartfishtank.utils.ToastUtils;

/**
 * Created by dongqiang on 2016/9/27.
 */

public class VideoSettingActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = VideoSettingActivity.class.getSimpleName();
    private ImageView ivback;
    private Button btnConfigWifi;
    private Button btnSearchDevice;
    private ProgressDialog progressDialog;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SEARCH_DEVICE_SUCCESS:
                    removeMessages(Constant.SEARCH_DEVICE_TIMEOUT);
                    SearchDeviceConfigResp searchDeviceConfigResp = (SearchDeviceConfigResp) msg.obj;
                    Log.d(TAG, "search success.\n" + searchDeviceConfigResp.toString());
                    break;

                case Constant.SEARCH_DEVICE_FAIL:
                    removeMessages(Constant.SEARCH_DEVICE_TIMEOUT);
                    Log.d(TAG, "search fail.");
                    break;

                case Constant.SEARCH_DEVICE_TIMEOUT:
                    Log.d(TAG, "search timeout.");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_setting);
        initViews();
        initEvents();

    }

    private void initViews() {
        ivback = (ImageView) findViewById(R.id.title_back);
        btnConfigWifi = (Button) findViewById(R.id.btn_config_wifi);
        btnSearchDevice = (Button) findViewById(R.id.btn_search_device);
        ivback.setVisibility(View.VISIBLE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在配置设备，请等待...");
    }

    private void initEvents() {
        btnConfigWifi.setOnClickListener(this);
        btnSearchDevice.setOnClickListener(this);
        ivback.setOnClickListener(this);
    }


    private void configWifi() {
        View view = LayoutInflater.from(VideoSettingActivity.this).inflate(R.layout.dialog_wifi_setting, null);
        final EditText etSsid = (EditText) view.findViewById(R.id.wifi_ssid);
        final EditText etPassword = (EditText) view.findViewById(R.id.wifi_et_password);
        final ImageView ivPwdVisible = (ImageView) view.findViewById(R.id.wifi_eye_on);
        final ImageView ivPwdInvisible = (ImageView) view.findViewById(R.id.wifi_eye_close);
        final Button confirm = (Button) view.findViewById(R.id.btn_confirm_wifi_config);
        final Button cancel = (Button) view.findViewById(R.id.btn_cancel_wifi_config);
        final AlertDialog.Builder builder = new AlertDialog.Builder(VideoSettingActivity.this).setCancelable(false);
        final AlertDialog dialog = builder.setView(view).create();
        dialog.show();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constant.LOCAL_WIFI_CONFIG_SUCCESS:
                        progressDialog.cancel();
                        dialog.dismiss();
                        ToastUtils.showLong(VideoSettingActivity.this, "已成功配置，请将手机接入相同wifi");
                        break;

                    case Constant.LOCAL_WIFI_CONFIG_FAIL:
                        progressDialog.cancel();
                        ToastUtils.showLong(VideoSettingActivity.this, "配置失败");
                        break;
                    case Constant.LOCAL_WIFI_CONFIG_TIMEOUT:
                        progressDialog.cancel();
                        ToastUtils.showLong(VideoSettingActivity.this, "配置超时");
                        break;
                }
            }
        };

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    //ivClearPwd.setVisibility(View.VISIBLE);
                    etPassword.setSelection(s.length());
                } else {
                    //ivClearPwd.setVisibility(View.INVISIBLE);
                }
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_confirm_wifi_config:
                        String ssid = etSsid.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();
                        if(TextUtils.isEmpty(ssid) || TextUtils.isEmpty(password)) {
                            ToastUtils.showLong(VideoSettingActivity.this, "WiFi ssid和密码都需要填写!");
                            return;
                        } else {
                            //request network to pass data.
                            progressDialog.show();
                            //成功配置后返回消息：
                            String data = "";
                            LocalWifiConfig wifiConfig = new LocalWifiConfig();
                            wifiConfig.setAuth(Constant.LOCAL_WIFI_CONFIG_AUTH);
                            wifiConfig.setCommond(Constant.LOCAL_WIFI_CONFIG_COMMOND);
                            LocalWifiConfig.Param param = wifiConfig.new Param();
                            param.setSsid(ssid);
                            param.setPwd(password);
                            wifiConfig.setParam(param);
                            data = new Gson().toJson(wifiConfig);
                            Log.e(TAG, data);
                            new ConfigWifiThread(Constant.LOCAL_SERVER_HOST, Constant.PORT, data, handler).start();
                            //取消进度条
                            //handler.sendEmptyMessageDelayed(handler_key.CONFIG_SUCCESs.ordinal(), 1000);
                            handler.sendEmptyMessageDelayed(Constant.LOCAL_WIFI_CONFIG_TIMEOUT, 15000);
                        }
                        break;

                    case R.id.btn_cancel_wifi_config:
                        dialog.cancel();
                        break;

                    case R.id.wifi_eye_on:
                        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        ivPwdInvisible.setVisibility(View.VISIBLE);
                        ivPwdVisible.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.wifi_eye_close:
                        etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        ivPwdInvisible.setVisibility(View.INVISIBLE);
                        ivPwdVisible.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };

        confirm.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        ivPwdVisible.setOnClickListener(onClickListener);
        ivPwdInvisible.setOnClickListener(onClickListener);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_config_wifi:
                configWifi();
                break;


            case R.id.btn_search_device:
                searchDevice();
                break;

            case R.id.title_back:
                this.finish();
                break;
        }
    }

    private void searchDevice() {
        boolean wifiConnected = NetworkUtils.isWifiConnected(this);
        if(!wifiConnected) {
            ToastUtils.showLong(this, "请检查wifi是否连接");
            return;
        }

        SearchDeviceConfig searchDeviceConfig = new SearchDeviceConfig();
        searchDeviceConfig.setAuth(Constant.LOCAL_WIFI_CONFIG_AUTH);
        searchDeviceConfig.setCommond(Constant.SEARCH_DEVICE_CONFIG_COMMOND);
        SearchDeviceConfig.Param param = searchDeviceConfig.new Param();
        searchDeviceConfig.setParam(param);
        String data = new Gson().toJson(searchDeviceConfig);
        Log.e(TAG, data);

        String ip = NetworkUtils.getLocalIpAddress(this);
        if(!TextUtils.isEmpty(ip)) {
            ip = ip.substring(0, ip.lastIndexOf(".")+1) + "255";
        } else {
            ToastUtils.showLong(this, "网络异常");
            return;
        }
        Log.d(TAG, "broadcast ip: " + ip);
        new SearchDeviceThread(ip, Constant.PORT, data, mHandler).start();
        mHandler.sendEmptyMessageDelayed(Constant.SEARCH_DEVICE_TIMEOUT, 15000);

    }
}
