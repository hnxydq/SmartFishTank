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
public class PolicyActivity extends BaseActivity {

    private static final String TAG = "ProtocolActivity";
    private ImageView ivGoBack;
    private TextView policyText;
    private TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_policy);
        initViews();
        initEvents();
        initData();
    }

    private void initData() {
        try {
            String text = AssetsUtils.getFromAssets(this, "policy_chn.txt");
            policyText.setText(text);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    private void initViews() {
        ivGoBack = (ImageView) findViewById(R.id.title_back);
        policyText = (TextView) findViewById(R.id.tv_policy_text);
        ivGoBack.setVisibility(View.VISIBLE);
        mainTitle = (TextView) findViewById(R.id.title_main_text);
        mainTitle.setVisibility(View.VISIBLE);
        mainTitle.setText("隐私政策");
    }

    private void initEvents() {
        ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PolicyActivity.this.finish();
            }
        });
    }
}
