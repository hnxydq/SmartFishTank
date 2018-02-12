package com.tfxiaozi.smartfishtank.activity.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
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
import com.tfxiaozi.smartfishtank.activity.MainActivity;
import com.tfxiaozi.smartfishtank.utils.IntentUtil;
import com.tfxiaozi.smartfishtank.utils.NetworkUtils;
import com.tfxiaozi.smartfishtank.utils.ToastUtils;

/**
 * Created by Administrator on 2016/09/07.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText etUsername;
    private EditText etPassword;
    private ImageView ivClearName;
    private ImageView ivClearPwd;
    private ImageView ivPwdVisible;
    private ImageView ivPwdInvisible;
    private Button btnLogin;
    private TextView tvRegister;
    private TextView tvForget;
    private TextView tvSkipLogin;
    private ProgressDialog dialog;

    private enum handler_key {
        /** 登陆成功. */
        LOGIN_SUCCESS,
        /** 登陆失败. */
        LOGIN_FAIL,
        /** 登录超时. */
        LOGIN_TIMEOUT,
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handler_key key = handler_key.values()[msg.what];
            switch (key) {
                case LOGIN_SUCCESS:
                    handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    dialog.cancel();
                    IntentUtil.getInstance().startActivity(LoginActivity.this, MainActivity.class);
                    finish();
                    break;
                case LOGIN_FAIL:

                    break;
                case LOGIN_TIMEOUT:
                    handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    ToastUtils.showLong(LoginActivity.this, "登录超时");
                    dialog.cancel();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_login);

        initViews();
        initEvents();
    }

    private void initViews() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        ivClearName = (ImageView) findViewById(R.id.clear_username);
        ivClearPwd = (ImageView) findViewById(R.id.clear_password);
        ivPwdVisible = (ImageView) findViewById(R.id.login_eye_on);
        ivPwdInvisible = (ImageView) findViewById(R.id.login_eye_close);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvForget = (TextView) findViewById(R.id.tv_forget);
        tvSkipLogin = (TextView) findViewById(R.id.tv_skip_login);

        dialog = new ProgressDialog(this);
        dialog.setMessage("登录中，请稍候...");
        if(spfManager.getUserName() != null) {
            etUsername.setText(spfManager.getUserName());
        }
    }

    private void initEvents() {
        etUsername.addTextChangedListener(new TextWatcher() {

            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(temp.length() > 0) {
                    ivClearName.setVisibility(View.VISIBLE);
                } else {
                    ivClearName.setVisibility(View.INVISIBLE);
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    ivClearPwd.setVisibility(View.VISIBLE);
                    etPassword.setSelection(s.length());
                } else {
                    ivClearPwd.setVisibility(View.INVISIBLE);
                }
            }
        });

        etUsername.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        ivPwdVisible.setOnClickListener(this);
        ivPwdInvisible.setOnClickListener(this);
        ivClearName.setOnClickListener(this);
        ivClearPwd.setOnClickListener(this);
        tvSkipLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.clear_username:
                etUsername.setText("");
                ivClearName.setVisibility(View.INVISIBLE);
                break;
            case R.id.clear_password:
                etPassword.setText("");
                ivClearPwd.setVisibility(View.INVISIBLE);
                break;
            case R.id.login_eye_on:
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivPwdInvisible.setVisibility(View.VISIBLE);
                ivPwdVisible.setVisibility(View.INVISIBLE);
                break;
            case R.id.login_eye_close:
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivPwdInvisible.setVisibility(View.INVISIBLE);
                ivPwdVisible.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_login:
                if(!NetworkUtils.isNetworkConnected(this)) {
                    ToastUtils.showShort(this, "请检查网络连接");
                    return;
                }
                if(TextUtils.isEmpty(etUsername.getText().toString())) {
                    ToastUtils.showLong(this, "请输入用户名");
                    return;
                }
                if(TextUtils.isEmpty(etPassword.getText().toString())) {
                    ToastUtils.showLong(this, "请输入密码");
                    return;
                }
                dialog.show();
                //获取账号密码登录
                String uname = etUsername.getText().toString().trim();
                String passwd = etPassword.getText().toString().trim();

                //TODO: handle login
                //与服务端验证登陆，成功后会执行回调方法
                handler.sendEmptyMessageDelayed(handler_key.LOGIN_SUCCESS.ordinal(), 1000);
                //如果15秒还没登录成功，则提示超时
                handler.sendEmptyMessageDelayed(handler_key.LOGIN_TIMEOUT.ordinal(), 15000);
                break;
            case R.id.tv_register:
                if(NetworkUtils.isNetworkConnected(this)) {
                    IntentUtil.getInstance().startActivity(LoginActivity.this, RegisterActivity.class);
                } else {
                    ToastUtils.showShort(this, "请检查网络连接");
                }
                break;
            case  R.id.tv_forget:
                if(NetworkUtils.isNetworkConnected(this)) {
                    IntentUtil.getInstance().startActivity(LoginActivity.this, ForgetPwdsActivity.class);
                } else {
                    ToastUtils.showShort(this, "请检查网络连接");
                }
                break;
            case R.id.tv_skip_login:
                IntentUtil.getInstance().startActivity(LoginActivity.this, MainActivity.class);
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                finish();
                break;
        }
    }
}
