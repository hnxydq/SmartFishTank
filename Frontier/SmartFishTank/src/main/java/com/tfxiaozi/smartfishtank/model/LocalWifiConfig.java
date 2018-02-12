package com.tfxiaozi.smartfishtank.model;

/**
 * Created by dongqiang on 2016/9/27.
 */
public class LocalWifiConfig {

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

    public LocalWifiConfig.Param getParam() {
        return Param;
    }

    public void setParam(LocalWifiConfig.Param param) {
        Param = param;
    }

    @Override
    public String toString() {
        return "LocalWifiConfig{" +
                "Auth='" + Auth + '\'' +
                ", Commond='" + Commond + '\'' +
                ", Param=" + Param +
                '}';
    }

    public class Param {
        private String ssid;
        private String pwd;
        private String key_mgmt;
        private String proto;
        private String pairwise;
        private String group;

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getKey_mgmt() {
            return key_mgmt;
        }

        public void setKey_mgmt(String key_mgmt) {
            this.key_mgmt = key_mgmt;
        }

        public String getPairwise() {
            return pairwise;
        }

        public void setPairwise(String pairwise) {
            this.pairwise = pairwise;
        }

        public String getProto() {
            return proto;
        }

        public void setProto(String proto) {
            this.proto = proto;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        @Override
        public String toString() {
            return "Param{" +
                    "ssid='" + ssid + '\'' +
                    ", pwd='" + pwd + '\'' +
                    ", key_mgmt='" + key_mgmt + '\'' +
                    ", proto='" + proto + '\'' +
                    ", pairwise='" + pairwise + '\'' +
                    ", group='" + group + '\'' +
                    '}';
        }
    }
}
