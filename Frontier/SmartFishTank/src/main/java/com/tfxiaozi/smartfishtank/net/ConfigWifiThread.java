package com.tfxiaozi.smartfishtank.net;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.tfxiaozi.smartfishtank.constants.Constant;
import com.tfxiaozi.smartfishtank.model.LocalWifiConfigResp;


/**
 * Created by dongqiang on 2016/9/28.
 */

public class ConfigWifiThread extends Thread {

    private static final String TAG = ConfigWifiThread.class.getSimpleName();

    private String host;
    private int port;
    private String data;
    private Handler handler;

    public ConfigWifiThread(String host, int port, String data, Handler handler) {
        this.host = host;
        this.port = port;
        this.data = data;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            UDPXutil udpXutil = new UDPXutil();
            Log.e(TAG, "data--->" + data);
            udpXutil.send(host, port, data.getBytes());
            String result = udpXutil.receive(host, port);
            udpXutil.close();
            Log.e(TAG, "result-->" + result);
            LocalWifiConfigResp configResp = new Gson().fromJson(result, LocalWifiConfigResp.class);
            if(configResp != null && configResp.getAuth().equals(Constant.LOCAL_WIFI_CONFIG_AUTH) &&
                    configResp.getResult().equals(Constant.SUCCESS)) {
                handler.sendEmptyMessageDelayed(Constant.LOCAL_WIFI_CONFIG_SUCCESS, 1000);
            } else {
                handler.sendEmptyMessageDelayed(Constant.LOCAL_WIFI_CONFIG_FAIL, 1000);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
