package com.tfxiaozi.smartfishtank.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/09/09.
 */
public class AssetsUtils {

    public static String getFromAssets(Context context, String fileName) throws Exception{
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line + "\n";
            inputReader.close();
            bufReader.close();
            return Result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
