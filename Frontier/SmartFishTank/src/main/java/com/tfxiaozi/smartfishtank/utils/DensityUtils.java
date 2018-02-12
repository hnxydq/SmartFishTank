package com.tfxiaozi.smartfishtank.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by dongqiang on 2016/9/11.
 */
public class DensityUtils {

    public static float px2dp(Context context, float pxVal) {
        final float scaleDensity = context.getResources().getDisplayMetrics().density;
        return (pxVal/scaleDensity);
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
