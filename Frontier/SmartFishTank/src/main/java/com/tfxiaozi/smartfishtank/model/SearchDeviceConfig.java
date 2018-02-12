package com.tfxiaozi.smartfishtank.model;

/**
 * Created by dongqiang on 2016/10/3.
 */

public class SearchDeviceConfig {
    private String Auth;
    private String Commond;
    private Param Param;

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

    public SearchDeviceConfig.Param getParam() {
        return Param;
    }

    public void setParam(SearchDeviceConfig.Param param) {
        Param = param;
    }

    @Override
    public String toString() {
        return "SearchDeviceConfig{" +
                "Auth='" + Auth + '\'' +
                ", Commond='" + Commond + '\'' +
                ", Param=" + Param +
                '}';
    }

    public class Param {
        @Override
        public String toString() {
            return "Param{}";
        }
    }


}
