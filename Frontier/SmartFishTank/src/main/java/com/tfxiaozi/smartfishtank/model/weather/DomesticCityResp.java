package com.tfxiaozi.smartfishtank.model.weather;

import com.tfxiaozi.smartfishtank.db.DomesticCity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/30.
 */

public class DomesticCityResp {
    private List<DomesticCity> city_info;
    private String status;

    public List<DomesticCity> getCity_info() {
        return city_info;
    }

    public void setCity_info(List<DomesticCity> city_info) {
        this.city_info = city_info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
