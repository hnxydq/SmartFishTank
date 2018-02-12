package com.tfxiaozi.smartfishtank.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2016/9/30.
 */
@Entity
public class DomesticCity {

    @Id(autoincrement = true)
    private Long cid;
    /**
     * city
     */
    @Property(nameInDb = "CITY")
    private String city;
    /**
     * country
     */
    @Property(nameInDb = "COUNTRY")
    private String cnty;
    /**
     * id
     */
    @Property(nameInDb = "CITY_ID")
    private String id;
    /**
     * latitude
     */
    @Property(nameInDb = "LATITUDE")
    private String lat;
    /**
     * longitude
     */
    @Property(nameInDb = "LONGITUDE")
    private String lon;
    /**
     * province
     */
    @Property(nameInDb = "PROVINCE")
    private String prov;

    @Generated(hash = 549995405)
    public DomesticCity(Long cid, String city, String cnty, String id, String lat,
            String lon, String prov) {
        this.cid = cid;
        this.city = city;
        this.cnty = cnty;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.prov = prov;
    }

    @Generated(hash = 1891326997)
    public DomesticCity() {
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    @Override
    public String toString() {
        return "DomesticCity{" +
                "city='" + city + '\'' +
                ", cnty='" + cnty + '\'' +
                ", id='" + id + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", prov='" + prov + '\'' +
                '}';
    }
}
