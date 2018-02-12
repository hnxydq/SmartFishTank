package com.tfxiaozi.smartfishtank.activity.account;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.utils.IntentUtil;

/**
 * Created by Administrator on 2016/09/09.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private ImageView ivBack;
    private EditText etUsername;
    private EditText etPasswd;
    private EditText etVerifyCode;
    private ImageView ivClearVerifyCode;
    private ImageView ivClearUserName;
    private ImageView ivClearPasswd;
    private ImageView ivPasswdVisible;
    private ImageView ivPasswdInvisible;
    private Button btRegister;
    private TextView tvUserProtocol;
    private TextView tvPrivatePolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initEvents();
    }


    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.title_back);
        etUsername = (EditText) findViewById(R.id.register_username);
        etVerifyCode = (EditText) findViewById(R.id.et_verifycode);
        etPasswd = (EditText) findViewById(R.id.register_et_password);

        ivClearVerifyCode = (ImageView) findViewById(R.id.clear_verifycode);
        ivClearUserName = (ImageView) findViewById(R.id.register_clear_username);
        ivClearPasswd = (ImageView) findViewById(R.id.register_clear_password);

        ivPasswdVisible = (ImageView) findViewById(R.id.register_eye_on);
        ivPasswdInvisible = (ImageView) findViewById(R.id.register_eye_close);
        btRegister = (Button) findViewById(R.id.btn_register);

        tvUserProtocol = (TextView) findViewById(R.id.tv_protocol);
        tvUserProtocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        tvPrivatePolicy = (TextView) findViewById(R.id.tv_policy);
        tvPrivatePolicy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
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
                    ivClearUserName.setVisibility(View.VISIBLE);
                } else {
                    ivClearUserName.setVisibility(View.INVISIBLE);
                }
            }
        });
        etVerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    ivClearVerifyCode.setVisibility(View.VISIBLE);
                } else {
                    ivClearVerifyCode.setVisibility(View.INVISIBLE);
                }
            }
        });
        etPasswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    ivClearPasswd.setVisibility(View.VISIBLE);
                    etPasswd.setSelection(s.length());
                } else {
                    ivClearPasswd.setVisibility(View.INVISIBLE);
                }
            }
        });
        ivBack.setOnClickListener(this);
        ivClearUserName.setOnClickListener(this);
        ivClearVerifyCode.setOnClickListener(this);
        ivClearPasswd.setOnClickListener(this);
        ivPasswdVisible.setOnClickListener(this);
        ivPasswdInvisible.setOnClickListener(this);
        tvUserProtocol.setOnClickListener(this);
        tvPrivatePolicy.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();
        switch (viewId) {
            case R.id.title_back:
                finish();
                break;
            case R.id.register_clear_username:
                etUsername.setText("");
                ivClearUserName.setVisibility(View.INVISIBLE);
                break;

            case R.id.register_clear_password:
                etPasswd.setText("");
                ivClearPasswd.setVisibility(View.INVISIBLE);
                break;

            case R.id.clear_verifycode:
                etVerifyCode.setText("");
                ivClearVerifyCode.setVisibility(View.INVISIBLE);
                break;

            case R.id.register_eye_close:
                etPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivPasswdVisible.setVisibility(View.VISIBLE);
                ivPasswdInvisible.setVisibility(View.INVISIBLE);
                break;

            case R.id.register_eye_on:
                etPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivPasswdInvisible.setVisibility(View.VISIBLE);
                ivPasswdVisible.setVisibility(View.INVISIBLE);
                break;

            case R.id.tv_protocol:
                IntentUtil.getInstance().startActivity(RegisterActivity.this, ProtocolActivity.class);
                break;

            case R.id.tv_policy:
                IntentUtil.getInstance().startActivity(RegisterActivity.this, PolicyActivity.class);
                break;

            case R.id.btn_register:

                break;
        }
    }
}
