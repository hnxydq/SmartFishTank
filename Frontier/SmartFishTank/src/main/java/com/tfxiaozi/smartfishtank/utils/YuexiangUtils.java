package com.tfxiaozi.smartfishtank.utils;


import com.tfxiaozi.smartfishtank.R;

import java.util.HashMap;

/**
 */
public class YuexiangUtils {

    private static HashMap<String, Integer> imgs = new HashMap<>();

    public static int getImgId(String key) {
        Integer id = imgs.get(key);
        if (id == null) {
            return R.mipmap.m0;
        }
        return imgs.get(key);
    }

    static {
        imgs.put("m0", R.mipmap.m0);
        imgs.put("m1", R.mipmap.m1);
        imgs.put("m2", R.mipmap.m2);
        imgs.put("m3", R.mipmap.m3);
        imgs.put("m4", R.mipmap.m4);
        imgs.put("m5", R.mipmap.m5);
        imgs.put("m6", R.mipmap.m6);
        imgs.put("m7", R.mipmap.m7);
        imgs.put("m8", R.mipmap.m8);
        imgs.put("m9", R.mipmap.m9);
        imgs.put("m10", R.mipmap.m10);
        imgs.put("m11", R.mipmap.m11);
        imgs.put("m12", R.mipmap.m12);
        imgs.put("m13", R.mipmap.m13);
        imgs.put("m14", R.mipmap.m14);
        imgs.put("m15", R.mipmap.m15);
        imgs.put("m16", R.mipmap.m16);
        imgs.put("m17", R.mipmap.m17);
        imgs.put("m18", R.mipmap.m18);
        imgs.put("m19", R.mipmap.m19);
        imgs.put("m20", R.mipmap.m20);
        imgs.put("m21", R.mipmap.m21);
        imgs.put("m22", R.mipmap.m22);
        imgs.put("m23", R.mipmap.m23);
        imgs.put("m24", R.mipmap.m24);
        imgs.put("m25", R.mipmap.m25);
        imgs.put("m26", R.mipmap.m26);
        imgs.put("m27", R.mipmap.m27);
        imgs.put("m28", R.mipmap.m28);
        imgs.put("m29", R.mipmap.m29);
        imgs.put("m30", R.mipmap.m30);
        imgs.put("m31", R.mipmap.m31);

    }


}
