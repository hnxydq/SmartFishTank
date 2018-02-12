package com.tfxiaozi.smartfishtank.model;

/**
 * Created by dongqiang on 2016/10/15.
 */

public class LedListItemData {
    private boolean checked;
    private String ip;
    private String name;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LedListItemData{" +
                "checked=" + checked +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
