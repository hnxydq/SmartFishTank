package com.tfxiaozi.smartfishtank.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/09/08.
 */
public class IntentUtil {
    private static IntentUtil instance;

    private IntentUtil() {

    }

    public static IntentUtil getInstance() {
        if(instance == null) {
            synchronized (IntentUtil.class) {
                if(instance == null) {
                    instance = new IntentUtil();
                }
            }
        }

        return instance;
    }

    /**
     * 启动activity
     * @param ctx
     * @param cls
     */
    public void startActivity(Context ctx, Class<?> cls) {
        Intent i = new Intent();
        i.setClass(ctx, cls);
        ctx.startActivity(i);
    }

    /**
     * 回收资源
     */
    public static void recycle() {
        if(instance != null) {
            instance = null;
        }
    }
}
