package com.tfxiaozi.smartfishtank.activity.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.utils.YuexiangUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by dongqiang on 2016/10/16.
 */

public class NightModeSettingActivity extends BaseActivity implements View.OnClickListener {

    private static final String NIGHT_MODE_SWITCH_STATE = "cloud_switch_state";
    private static final String CLOUD_INSTANT_RUN_SWITCH_STATE = "cloud_instant_run_switch_state";
    private TextView tvMainTitle;
    private ImageView ivBack;
    private Button btnSave;
    private ImageView ivSwitch;
    private SharedPreferences spf;
    private boolean isSwicthOn;
    private Context context;
    private ImageView yuexiang_iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_mode_setting);
        spf = spfManager.getSpf();
        context = this;
        initViews();
        initEvents();

        loadYuexiangImg();

    }

    private void initViews() {
        tvMainTitle = (TextView) findViewById(R.id.title_main_text);
        tvMainTitle.setText(getString(R.string.night_mode));
        tvMainTitle.setVisibility(View.VISIBLE);
        ivBack = (ImageView) findViewById(R.id.title_back);
        ivBack.setVisibility(View.VISIBLE);
        btnSave = (Button) findViewById(R.id.btn_save);
        ivSwitch = (ImageView) findViewById(R.id.night_mode_switch);
        yuexiang_iv = (ImageView)findViewById(R.id.yuexiang_iv);
        initSwitch();
    }

    private void initEvents() {
        btnSave.setOnClickListener(this);
        ivSwitch.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.night_mode_switch:
                break;

            case R.id.title_back:
                finish();
                break;


            case R.id.btn_save:
                break;
        }
    }


    private void initSwitch() {
        isSwicthOn = spf.getBoolean(NIGHT_MODE_SWITCH_STATE, false);
        if(isSwicthOn) {
            ivSwitch.setImageResource(R.mipmap.icon_switch_on);
        } else {
            ivSwitch.setImageResource(R.mipmap.icon_switch_off);
        }
    }

    private void loadYuexiangImg(){
        new Thread() {
            @Override
            public void run() {
                try {
                    final Document doc = Jsoup.connect("http://yueliangyuanque.51240.com/").get();
                    final String title = doc.title();
                    Log.i("classNames", "name size:" + doc.classNames().size());
                    for (String s : doc.classNames()) {
                        Log.i("classNames", "name:" + s);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Elements es = doc.select("div.wnrl_riqi");
                            StringBuffer buf = new StringBuffer();
                            String newStr = "";
                            for (Element e : es) {
                                Elements es1 = e.select("span.wnrl_riqi_jintian");
                                if (!TextUtils.isEmpty(es1.toString())) {
                                    Element p2 = e.parent();
                                    Element c1 = p2.child(0);
                                    Elements img = c1.getElementsByTag("img").get(0).getElementsByAttribute("src");
//                                    <img src="http://f.51240.com/file/yueliangyuanque/pic/1_m30.jpg">
                                    String imgStr = img.toString();
                                    newStr = imgStr.substring(imgStr.lastIndexOf("_") + 1, imgStr.lastIndexOf("."));
                                    buf.append(newStr + "\n");
                                }
                            }
                            yuexiang_iv.setImageResource(YuexiangUtils.getImgId(newStr));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
