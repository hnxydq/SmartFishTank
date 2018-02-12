package com.tfxiaozi.smartfishtank.dao;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tfxiaozi.smartfishtank.db.DbManager;
import com.tfxiaozi.smartfishtank.db.DomesticCity;
import com.tfxiaozi.smartfishtank.db.DomesticCityDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/30.
 */

public class WeatherUtils {

    public static int getDomesticCityCount(Context context) {
        DbManager dbManager = DbManager.getInstance(context);
        DomesticCityDao domesticCityDao = dbManager.getDaoSession().getDomesticCityDao();
        List<DomesticCity> list = domesticCityDao.loadAll();
        dbManager.closeConnction();
        return list.size();
    }

    public static void insertDomesticCity(Context context, DomesticCity domesticCity) {
        DbManager dbManager = DbManager.getInstance(context);
        DomesticCityDao domesticCityDao = dbManager.getDaoSession().getDomesticCityDao();
        domesticCityDao.insertOrReplace(domesticCity);
        dbManager.closeConnction();
    }

    public static void insertDomesticCityList(Context context, List<DomesticCity> cityList) {
        if(cityList != null || !cityList.isEmpty()) {
            DbManager dbManager = DbManager.getInstance(context);
            DomesticCityDao domesticCityDao = dbManager.getDaoSession().getDomesticCityDao();
            domesticCityDao.insertOrReplaceInTx(cityList);
            dbManager.closeConnction();
        }
    }

    public static DomesticCity selectOne(Context context, String id) {
        if(!TextUtils.isEmpty(id)) {
            DbManager dbManager = DbManager.getInstance(context);
            DomesticCityDao domesticCityDao = dbManager.getDaoSession().getDomesticCityDao();
            QueryBuilder queryBuilder = domesticCityDao.queryBuilder();
            queryBuilder.where(DomesticCityDao.Properties.Id.eq(id));
            List<DomesticCity> list = queryBuilder.list();
            Log.d("WeatherUtils", list.toString());
            if(list != null && list.size() > 0) {
                return list.get(0);
            }
        }
        return null;
    }


}
