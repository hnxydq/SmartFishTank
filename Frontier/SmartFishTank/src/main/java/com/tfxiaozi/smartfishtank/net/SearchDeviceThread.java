package com.tfxiaozi.smartfishtank.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.tfxiaozi.smartfishtank.constants.Constant;
import com.tfxiaozi.smartfishtank.model.SearchDeviceConfigResp;


/**
 * Created by dongqiang on 2016/9/28.
 */

public class SearchDeviceThread extends Thread {

    private static final String TAG = SearchDeviceThread.class.getSimpleName();

    private String host;
    private int port;
    private String data;
    private Handler handler;

    public SearchDeviceThread(String host, int port, String data, Handler handler) {
        this.host = host;
        this.port = port;
        this.data = data;
        this.handler = handler;
        Log.d(TAG, "host=" + host + ", port=" + port + ", data="+data);
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
            SearchDeviceConfigResp searchDeviceConfigResp = new Gson().fromJson(result, SearchDeviceConfigResp.class);
            Log.d(TAG, searchDeviceConfigResp.toString());
            if(searchDeviceConfigResp != null && searchDeviceConfigResp.getAuth().equals(Constant.LOCAL_WIFI_CONFIG_AUTH) &&
                    searchDeviceConfigResp.getCommond().equals(Constant.SEARCH_DEVICE_CONFIG_COMMOND)) {
                Message msg = Message.obtain();
                msg.what = Constant.SEARCH_DEVICE_SUCCESS;
                msg.obj = searchDeviceConfigResp;
                handler.sendMessage(msg);
            } else {
                handler.sendEmptyMessageDelayed(Constant.SEARCH_DEVICE_FAIL, 1000);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
