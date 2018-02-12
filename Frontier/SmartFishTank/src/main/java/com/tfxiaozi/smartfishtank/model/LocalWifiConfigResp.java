package com.tfxiaozi.smartfishtank.model;

/**
 * Created by dongqiang on 2016/9/28.
 */

public class LocalWifiConfigResp {
    private String Auth;
    private String Commond;
    private String Result;

    public String getAuth() {
        return Auth;
    }

    public void setAuth(String auth) {
        Auth = auth;
    }

    public String getCommond() {
        return Commond;
    }

    public void setCommond(String commond) {
        Commond = commond;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    @Override
    public String toString() {
        return "LocalWifiConfigResp{" +
                "Auth='" + Auth + '\'' +
                ", Commond='" + Commond + '\'' +
                ", Result='" + Result + '\'' +
                '}';
    }
}
