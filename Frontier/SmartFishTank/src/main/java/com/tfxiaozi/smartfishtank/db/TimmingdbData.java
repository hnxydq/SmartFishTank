package com.tfxiaozi.smartfishtank.db;

import com.tfxiaozi.smartfishtank.model.TimmingData;
import com.tfxiaozi.smartfishtank.utils.TimeUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.apihint.Internal;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TimmingdbData {
    @Id(autoincrement = true)
    private Long id ;
    @Property(nameInDb = "time")
    private String time;
    @Property(nameInDb = "d1")
    private int d1;
    @Property(nameInDb = "d2")
    private int d2;
    @Property(nameInDb = "d3")
    private int d3;
    @Property(nameInDb = "d4")
    private int d4;
    @Property(nameInDb = "d5")
    private int d5;
    @Property(nameInDb = "d6")
    private int d6;
    @Property(nameInDb = "d7")
    private int d7;

    @Generated(hash = 1866300822)
    public TimmingdbData(Long id, String time, int d1, int d2, int d3, int d4, int d5, int d6,
            int d7) {
        this.id = id;
        this.time = time;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.d4 = d4;
        this.d5 = d5;
        this.d6 = d6;
        this.d7 = d7;
    }

    @Generated(hash = 1485445695)
    public TimmingdbData() {
    }


    public TimmingData toTimmingData() {
        TimmingData data = new TimmingData();
        data.setTime(getTime());
        data.setId(getId());
        int[] ds = new int[7];
        ds[0] = getD1();
        ds[1] = getD2();
        ds[2] = getD3();
        ds[3] = getD4();
        ds[4] = getD5();
        ds[5] = getD6();
        ds[6] = getD7();
        data.setDatas(ds);
        return data ;

    }


    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getD1() {
        return this.d1;
    }

    public void setD1(int d1) {
        this.d1 = d1;
    }

    public int getD2() {
        return this.d2;
    }

    public void setD2(int d2) {
        this.d2 = d2;
    }

    public int getD3() {
        return this.d3;
    }

    public void setD3(int d3) {
        this.d3 = d3;
    }

    public int getD4() {
        return this.d4;
    }

    public void setD4(int d4) {
        this.d4 = d4;
    }

    public int getD5() {
        return this.d5;
    }

    public void setD5(int d5) {
        this.d5 = d5;
    }

    public int getD6() {
        return this.d6;
    }

    public void setD6(int d6) {
        this.d6 = d6;
    }

    public int getD7() {
        return this.d7;
    }

    public void setD7(int d7) {
        this.d7 = d7;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
