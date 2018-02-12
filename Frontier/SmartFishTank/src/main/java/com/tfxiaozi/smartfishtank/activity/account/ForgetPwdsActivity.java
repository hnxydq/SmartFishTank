package com.tfxiaozi.smartfishtank.activity.account;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.utils.ToastUtils;

/**
 * Created by dongqiang on 2016/9/9.
 */
public class ForgetPwdsActivity extends BaseActivity implements View.OnClickListener{
    private ImageView ivBack;
    private TextView tvPhoneRetrive;
    private TextView tvEmailRetrieve;
    private EditText etUsername;
    private EditText etVerifycode;
    private ImageView ivClearUsername;
    private ImageView ivVerifycodeImg;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwds);

        initViews();
        initEvents();
    }

    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.title_back);
        tvPhoneRetrive = (TextView) findViewById(R.id.phone_retrieve);
        tvEmailRetrieve = (TextView) findViewById(R.id.email_retrieve);
        etUsername = (EditText) findViewById(R.id.bind_username);
        etVerifycode = (EditText) findViewById(R.id.et_verifycode);
        ivClearUsername = (ImageView) findViewById(R.id.clear_bind_username);
        ivVerifycodeImg = (ImageView) findViewById(R.id.iv_verifycode_img);
        btnNext = (Button) findViewById(R.id.btn_next);

        tvPhoneRetrive.setTextColor(Color.parseColor("#313131"));
        tvEmailRetrieve.setTextColor(Color.parseColor("#7E7E7E"));
        etUsername.setHint(getString(R.string.bind_phone_hint));

    }

    private void initEvents() {
        etUsername.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    ivClearUsername.setVisibility(View.VISIBLE);
                } else {
                    ivClearUsername.setVisibility(View.INVISIBLE);
                }
            }
        });
        ivBack.setOnClickListener(this);
        tvPhoneRetrive.setOnClickListener(this);
        tvEmailRetrieve.setOnClickListener(this);
        ivClearUsername.setOnClickListener(this);
        ivVerifycodeImg.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.title_back:
                finish();
                break;
            case R.id.phone_retrieve:
                tvPhoneRetrive.setTextColor(Color.parseColor("#313131"));
                tvEmailRetrieve.setTextColor(Color.parseColor("#7E7E7E"));
                etUsername.setHint(getString(R.string.bind_phone_hint));
                break;
            case R.id.email_retrieve:
                tvEmailRetrieve.setTextColor(Color.parseColor("#313131"));
                tvPhoneRetrive.setTextColor(Color.parseColor("#7E7E7E"));
                etUsername.setHint(getString(R.string.bind_email_hint));
                break;
            case R.id.iv_verifycode_img:
                //request network to get verify code image
                break;
            case R.id.clear_bind_username:
                etUsername.setText("");
                ivClearUsername.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_next:
                String verifyCode = etVerifycode.getText().toString();
                if(TextUtils.isEmpty(verifyCode)) {
                    ToastUtils.showLong(this, "验证码不能为空");
                }
                break;
        }
    }
}
