package com.tfxiaozi.smartfishtank.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference处理类
 */
public class SpfManager {

    private SharedPreferences spf;

    private Context context;

    //SharePreference文件中的变量名字列表

    /**
     * spf 名字
     */
    private final String SHARED_PREFERENCES = "spf";

    /**
     * 用户名
     */
    private final String USER_NAME = "username";

    /**
     * 密码
     */
    private final String PASSWORD = "password";

    /**
     * 令牌
     */
    private final String TOKEN = "token";

    /**
     * 用户id
     */
    private final  String UID = "uid";

    private final String WIFI_SSID = "wifi_ssid";

    private final String WIFI_PWD = "wifi_pwd";

    /**
     * 电话号码
     */
    private final String PHONE_NUM = "phonenumber";

    public SpfManager(Context context) {
        this.context = context;
        spf = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setUserName(String userName) {
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public String getUserName() {
        return spf.getString(USER_NAME, "");
    }

    public void setPassword(String password) {
        spf.edit().putString(PASSWORD, password).commit();
    }

    public String getPassword() {
        return spf.getString(PASSWORD, "");
    }

    public void setUid(String uid) {
        spf.edit().putString(UID, uid).commit();
    }

    public String getUid() {
        return spf.getString(UID, "");
    }

    public void setToken(String token) {
        spf.edit().putString(TOKEN, token).commit();
    }

    public String getToken() {
        return spf.getString(TOKEN, "");
    }

    public void setPhoneNum(String phoneNum) {
        spf.edit().putString(PHONE_NUM, phoneNum).commit();
    }

    public String getPhoneNum() {
        return spf.getString(PHONE_NUM, "");
    }

    public void setWifiSsid(String wifiSsid) {
        spf.edit().putString(WIFI_SSID, wifiSsid);
    }

    public String getWifiSsid() {
        return spf.getString(WIFI_SSID, "");
    }

    public void setWifiPwd(String wifiPwd) {
        spf.edit().putString(WIFI_PWD, wifiPwd);
    }

    public String getWifiPwd() {
        return spf.getString(WIFI_PWD, "");
    }

    public SharedPreferences getSpf() {
        if (this.spf == null) {
            spf = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        }
        return spf;
    }

}
