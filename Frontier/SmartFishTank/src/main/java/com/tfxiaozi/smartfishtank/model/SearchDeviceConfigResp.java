package com.tfxiaozi.smartfishtank.model;

/**
 * Created by dongqiang on 2016/10/3.
 */

public class SearchDeviceConfigResp {
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

    public SearchDeviceConfigResp.Param getParam() {
        return Param;
    }

    public void setParam(SearchDeviceConfigResp.Param param) {
        Param = param;
    }

    @Override
    public String toString() {
        return "SearchDeviceConfigResp{" +
                "Auth='" + Auth + '\'' +
                ", Commond='" + Commond + '\'' +
                ", Param=" + Param +
                '}';
    }

    public class Param {
        private String serverip;
        private String name;
        private String sn;
        private String rtspaddr;

        public String getServerip() {
            return serverip;
        }

        public void setServerip(String serverip) {
            this.serverip = serverip;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getRtspaddr() {
            return rtspaddr;
        }

        public void setRtspaddr(String rtspaddr) {
            this.rtspaddr = rtspaddr;
        }

        @Override
        public String toString() {
            return "Param{" +
                    "serverip='" + serverip + '\'' +
                    ", name='" + name + '\'' +
                    ", sn='" + sn + '\'' +
                    ", rtspaddr='" + rtspaddr + '\'' +
                    '}';
        }
    }
}
