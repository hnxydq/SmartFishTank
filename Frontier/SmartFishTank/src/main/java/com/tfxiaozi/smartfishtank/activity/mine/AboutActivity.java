package com.tfxiaozi.smartfishtank.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.activity.account.PolicyActivity;
import com.tfxiaozi.smartfishtank.activity.account.ProtocolActivity;
import com.tfxiaozi.smartfishtank.utils.IntentUtil;
import com.tfxiaozi.smartfishtank.widget.LabelLayout;

/**
 * Created by Administrator on 2016/09/13.
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "AboutActivity";
    private ImageView ivBack;
    private TextView tvMainText;
    private TextView tvAppVersion;
    private TextView tvUserProtocol;
    private TextView tvUserPolicy;
    private LabelLayout llFunctionIntro;
    private LabelLayout llFriendRecommend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        initViews();
        initEvents();
    }

    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.title_back);
        tvMainText = (TextView) findViewById(R.id.title_main_text);
        tvAppVersion = (TextView) findViewById(R.id.app_version);
        llFunctionIntro = (LabelLayout) findViewById(R.id.llFunction);
        llFriendRecommend = (LabelLayout) findViewById(R.id.llSNS);

        tvUserProtocol = (TextView) findViewById(R.id.tv_user_protocol);
        tvUserPolicy = (TextView) findViewById(R.id.tv_user_policy);

        ivBack.setVisibility(View.VISIBLE);
        tvMainText.setText(getString(R.string.about));
        tvMainText.setVisibility(View.VISIBLE);
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            tvAppVersion.setText(versionName);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        llFunctionIntro.setOnClickListener(this);
        llFriendRecommend.setOnClickListener(this);
        tvUserProtocol.setOnClickListener(this);
        tvUserPolicy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        int viewId = view.getId();
        switch (viewId) {
            case R.id.title_back:
                finish();
                break;
            case R.id.llFunction:
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("https://www.baidu.com"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.llSNS:
                intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", "智能鱼缸控制端");
                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;

            case R.id.tv_user_protocol:
                IntentUtil.getInstance().startActivity(this, ProtocolActivity.class);
                break;

            case R.id.tv_user_policy:
                IntentUtil.getInstance().startActivity(this, PolicyActivity.class);
                break;

        }
    }
}
