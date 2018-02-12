package com.tfxiaozi.smartfishtank.activity.account;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.utils.AssetsUtils;

/**
 * Created by Administrator on 2016/09/09.
 */
public class ProtocolActivity extends BaseActivity{

    private static final String TAG = "ProtocolActivity";
    private ImageView ivGoBack;
    private TextView protocolText;
    private TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_protocol);
        initViews();
        initEvents();
        initData();
    }

    private void initData() {
        try {
            String text = AssetsUtils.getFromAssets(this, "agreement_chn.txt");
            protocolText.setText(text);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    private void initViews() {
        ivGoBack = (ImageView) findViewById(R.id.title_back);
        protocolText = (TextView) findViewById(R.id.tv_protocol_text);
        ivGoBack.setVisibility(View.VISIBLE);
        mainTitle = (TextView) findViewById(R.id.title_main_text);
        mainTitle.setVisibility(View.VISIBLE);
        mainTitle.setText("用户协议");
    }

    private void initEvents() {
        ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProtocolActivity.this.finish();
            }
        });
    }

}
