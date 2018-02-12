package com.tfxiaozi.smartfishtank.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dongqiang on 2016/10/3.
 */
@Entity
public class CameraResource {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "sn")
    private String sn;

    @Property(nameInDb = "serverIp")
    private String serverIp;

    @Property(nameInDb = "rtspAddr")
    private String rtspAddr;

    @Generated(hash = 614934363)
    public CameraResource(Long id, String sn, String serverIp, String rtspAddr) {
        this.id = id;
        this.sn = sn;
        this.serverIp = serverIp;
        this.rtspAddr = rtspAddr;
    }

    @Generated(hash = 1835212533)
    public CameraResource() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getRtspAddr() {
        return rtspAddr;
    }

    public void setRtspAddr(String rtspAddr) {
        this.rtspAddr = rtspAddr;
    }

    @Override
    public String toString() {
        return "CameraResource{" +
                "id=" + id +
                ", sn='" + sn + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", rtspAddr='" + rtspAddr + '\'' +
                '}';
    }
}
