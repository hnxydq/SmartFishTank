package com.tfxiaozi.smartfishtank.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    static SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    /**
     * 格式化 MM-dd HH:mm
     *
     * @param time 时间戳，单位是毫秒
     */
    public static String formatDate(long time) {
        Date nowTime = new Date(time);
        return format.format(nowTime);
    }

    /**
     * 格式化 MM-dd HH:mm
     *
     * @param datetime 时间戳，单位是毫秒
     */
    public static long parseDateTime(String datetime) {
        try {
            if (!TextUtils.isEmpty(datetime)) {
                return format.parse(datetime).getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
