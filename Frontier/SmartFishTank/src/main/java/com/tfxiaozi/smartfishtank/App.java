package com.tfxiaozi.smartfishtank;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tfxiaozi.smartfishtank.dao.WeatherUtils;
import com.tfxiaozi.smartfishtank.db.DomesticCity;
import com.tfxiaozi.smartfishtank.model.weather.DomesticCityResp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2016/09/07.
 */
public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        initWeatherData();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);

    }

    private void initWeatherData() {
        int count = WeatherUtils.getDomesticCityCount(getApplicationContext());
        if (count == 0) {
            String data = getJSONString("cn_city.json");
            DomesticCityResp resp = null;
            if(!TextUtils.isEmpty(data)) {
                resp = new Gson().fromJson(data, DomesticCityResp.class);
            } else {
                //get from network....
            }
            if(resp != null) {
                if(resp.getStatus().equals("ok")) {
                    List<DomesticCity> list = resp.getCity_info();
                    Log.d(TAG, "size=" + list.size());
                    WeatherUtils.insertDomesticCityList(getApplicationContext(), list);
                }
            } else {
                Log.e(TAG, "get domestic city data error...");
            }
        }
    }

    /**
     * 从.json中读取JSON字符串；
     */
    public String getJSONString(String name) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open(name), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//使用BufferReader读取输入流中的数据；
            String line;
            StringBuilder stringBuilder = new StringBuilder();//所有读取的json放到StringBuilder中，这里也可以使用StringBuffer,效果一样；
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();//按相反的顺序关闭流；
            inputStreamReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
