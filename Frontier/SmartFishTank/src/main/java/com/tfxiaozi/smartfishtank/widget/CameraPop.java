package com.tfxiaozi.smartfishtank.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.tfxiaozi.smartfishtank.R;

/**
 * Created by dongqiang on 2016/10/3.
 */

public class CameraPop extends PopupWindow {

    private View view;
    private LinearLayout llConfigWifi;
    private LinearLayout llSearchDevice;

    public CameraPop(Context context, View.OnClickListener onClickListener, int width, int height) {
        view = LayoutInflater.from(context).inflate(R.layout.pop_camera, null);
        llConfigWifi = (LinearLayout) view.findViewById(R.id.ll_config_wifi);
        llSearchDevice = (LinearLayout) view.findViewById(R.id.ll_search_device);

        if(onClickListener != null) {
            llConfigWifi.setOnClickListener(onClickListener);
            llSearchDevice.setOnClickListener(onClickListener);
        }

        setContentView(view);
        setWidth(width);
        setHeight(height);
        //set show/hide annimation
        setAnimationStyle(R.style.style_PopupCamera);
        setBackgroundDrawable(new ColorDrawable(0));
    }



}
