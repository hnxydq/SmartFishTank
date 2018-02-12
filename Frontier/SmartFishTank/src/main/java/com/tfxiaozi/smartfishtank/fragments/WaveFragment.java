package com.tfxiaozi.smartfishtank.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.widget.LineView;
import com.tfxiaozi.smartfishtank.widget.PowerIndicator;

/**
 * Created by dongqiang on 2016/9/11.
 */
public class WaveFragment extends Fragment {

    private Context context;
    private PowerIndicator indicator;
    private LineView lineView;
    private static final int MSG_DATA_CHANGE = 0x11;
    private Handler mHandler;
    private int mX = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wave_frament, container, false);
        initViews(view);
        initData();
        initEvents();
        return view;
    }

    private void initViews(View view) {
        indicator = (PowerIndicator) view.findViewById(R.id.pi_indicator);
        lineView = (LineView) view.findViewById(R.id.line);
    }

    private void initData() {
        indicator.setMaxLevel(6);
        indicator.setCurrentLevel(3);
        indicator.setText("10");

    }

    private void initEvents() {

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case MSG_DATA_CHANGE:
                        lineView.setLinePoint(msg.arg1, msg.arg2);
                        break;

                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };

        new Thread(){
            public void run() {
                for (int index=0; index<20; index++)
                {
                    Message message = new Message();
                    message.what = MSG_DATA_CHANGE;
                    message.arg1 = mX;
                    message.arg2 = (int)(Math.random()*200);;
                    mHandler.sendMessage(message);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    mX += 30;
                }
            };
        }.start();

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
