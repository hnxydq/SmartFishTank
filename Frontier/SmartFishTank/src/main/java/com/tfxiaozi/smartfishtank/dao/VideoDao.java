package com.tfxiaozi.smartfishtank.dao;

import android.content.Context;

import com.tfxiaozi.smartfishtank.db.CameraResource;
import com.tfxiaozi.smartfishtank.db.CameraResourceDao;
import com.tfxiaozi.smartfishtank.db.DbManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by dongqiang on 2016/10/3.
 */

public class VideoDao {

    public static int getVideoServerCount(Context context) {
        DbManager dbManager = DbManager.getInstance(context);
        CameraResourceDao cameraResourceDao = dbManager.getDaoSession().getCameraResourceDao();
        List<CameraResource> list = cameraResourceDao.loadAll();
        dbManager.closeConnction();
        return list.size();
    }

    public static void insertOne(Context context, CameraResource cameraResource) {
        DbManager dbManager = DbManager.getInstance(context);
        CameraResourceDao cameraResourceDao = dbManager.getDaoSession().getCameraResourceDao();
        cameraResourceDao.insertOrReplace(cameraResource);
        dbManager.closeConnction();
    }

    public static void insertList(Context context, List<CameraResource> list) {
        DbManager dbManager = DbManager.getInstance(context);
        CameraResourceDao cameraResourceDao = dbManager.getDaoSession().getCameraResourceDao();
        cameraResourceDao.insertOrReplaceInTx(list);
        dbManager.closeConnction();
    }

    public static CameraResource selectOne(Context context) {
        DbManager dbManager = DbManager.getInstance(context);
        CameraResourceDao cameraResourceDao = dbManager.getDaoSession().getCameraResourceDao();
        QueryBuilder queryBuilder = cameraResourceDao.queryBuilder();
        queryBuilder.limit(1);
        List<CameraResource> list = queryBuilder.list();
        if(list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
