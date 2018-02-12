package com.tfxiaozi.smartfishtank.db;

import android.content.Context;

/**
 * Created by Administrator on 2016/9/30.
 */

public class DbManager {

    private static final String DB_NAME = "smartfish_db";

    private Context context;

    private static volatile DbManager mInstance;

    private DaoMaster.DevOpenHelper devOpenHelper;

    private DaoSession daoSession;

    private DbManager(Context context) {
        this.context = context;
        devOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        daoSession = new DaoMaster(devOpenHelper.getWritableDatabase()).newSession();
    }

    /**
     * obtain a database manager.
     * @param context
     * @return
     */
    public static final DbManager getInstance(Context context) {
        if(mInstance == null) {
            synchronized (DbManager.class) {
                if(mInstance == null) {
                    mInstance = new DbManager(context);
                }
            }
        }
        return mInstance;
    }

    public DaoMaster.DevOpenHelper getDevOpenHelper() {
        return devOpenHelper;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void closeConnction() {
        daoSession.clear();
        devOpenHelper.close();
        mInstance = null;
    }

}
