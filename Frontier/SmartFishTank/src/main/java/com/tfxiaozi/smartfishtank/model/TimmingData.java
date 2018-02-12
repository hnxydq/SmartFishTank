package com.tfxiaozi.smartfishtank.model;

import com.tfxiaozi.smartfishtank.db.TimmingdbData;
import com.tfxiaozi.smartfishtank.utils.TimeUtils;


public class TimmingData implements Comparable<TimmingData>{
    private Long id ;
    private String time;
    private int[] datas = new int[7];

    public TimmingData() {

    }

    public TimmingData(String time, int[] datas) {
        this.time = time;
        this.datas = datas;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int[] getDatas() {
        return datas;
    }

    public void setDatas(int[] datas) {
        this.datas = datas;
    }

    @Override
    public int compareTo(TimmingData o) {
        if(TimeUtils.parseDateTime(getTime()) >= TimeUtils.parseDateTime(o.getTime())){
            return -1 ;
        }
        return 1;
    }

    public TimmingdbData toTimmingdbData(){
        TimmingdbData data = new TimmingdbData();
        data.setTime(getTime());
        data.setId(getId());
        data.setD1(datas[0]);
        data.setD2(datas[1]);
        data.setD3(datas[2]);
        data.setD4(datas[3]);
        data.setD5(datas[4]);
        data.setD6(datas[5]);
        data.setD7(datas[6]);

        return data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
