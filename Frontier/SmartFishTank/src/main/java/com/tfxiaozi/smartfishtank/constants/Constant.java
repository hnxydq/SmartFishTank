package com.tfxiaozi.smartfishtank.constants;

/**
 * Created by Administrator on 2016/9/28.
 */

public final class Constant {
    public static final String LOCAL_SERVER_HOST = "192.168.2.1";
    public static final int PORT = 50001;

    public static final String LOCAL_WIFI_CONFIG_AUTH = "SmartFishTank";
    public static final String LOCAL_WIFI_CONFIG_COMMOND = "wificonfig";
    public static final String SEARCH_DEVICE_CONFIG_COMMOND = "searchdevice";
    public static final String SUCCESS = "success";

    public static final String HF_KEY = "019500a88dc146d7a238140c7645a36b";
    public static final String HF_WEATHER_URL = "http://api.heweather.com/x3/weather";
	
	/**
     * ap配置
     */
    public static final int LOCAL_WIFI_CONFIG_SUCCESS = 0;
    public static final int LOCAL_WIFI_CONFIG_FAIL = 1;
    public static final int LOCAL_WIFI_CONFIG_TIMEOUT = 2;


    
    public static final int SEARCH_DEVICE_SUCCESS = 0;
    public static final int SEARCH_DEVICE_TIMEOUT = 1;
    public static final int SEARCH_DEVICE_FAIL = 2;

    public static final int VIDEO_PLAY = 10;
    public static final int VIDEO_STOP = 11;


    /**
     * LBS
     */
    public static final int BAIDU_ACCESS_COARSE_LOCATION =100;
    public static final int BAIDU_ACCESS_FINE_LOCATION =101;
    public static final int BAIDU_WRITE_EXTERNAL_STORAGE =102;
    public static final int BAIDU_READ_EXTERNAL_STORAGE =103;

    public static final int LED_DELETE = 3;

}
