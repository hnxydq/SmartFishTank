package com.tfxiaozi.smartfishtank.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.fragments.SettingFragment;
import com.tfxiaozi.smartfishtank.fragments.StateFragment;
import com.tfxiaozi.smartfishtank.fragments.SupportFragment;
import com.tfxiaozi.smartfishtank.fragments.UserFragment;
import com.tfxiaozi.smartfishtank.utils.SpfManager;

/**
 * Created by dongqiang on 2016/9/10.
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static int tabIndex = 0;
    private RadioGroup rgGroup;
    private RadioButton rbState;
    private RadioButton rbSetting;
    private RadioButton rbSupport;
    private RadioButton rbUser;
    private int[] tabViewIds = new int[]{R.id.rb_state, R.id.rb_setting, R.id.rb_support, R.id.rb_user};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        spfManager = new SpfManager(getApplicationContext());
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();
        if(savedInstanceState != null) {
            int index = savedInstanceState.getInt("BUNDLE_CHECKED_TAB", 0);
            Log.d(TAG, "onCreate" + index);
            if(index == 0 || index == 1 || index == 2 || index == 3) {
                rgGroup.check(tabViewIds[index]);
                tabIndex = index;
            } else {
                rgGroup.check(R.id.rb_state);
                tabIndex = 0;
            }
        } else {
            rgGroup.check(R.id.rb_state);
            tabIndex = 0;
        }
    }

    private void initViews() {

        rgGroup = (RadioGroup) findViewById(R.id.rg_tab);
        rbState = (RadioButton) findViewById(R.id.rb_state);
        rbSetting = (RadioButton) findViewById(R.id.rb_setting);
        rbSupport = (RadioButton) findViewById(R.id.rb_support);
        rbUser = (RadioButton) findViewById(R.id.rb_user);

        //adjustTabLayout(rbState, rbSetting, rbVideo, rbWave, rbSupport);
        //http://www.cnblogs.com/smyhvae/p/4463931.html
        //http://blog.csdn.net/u010648159/article/details/49385485
        //http://blog.csdn.net/u012246458/article/details/50387308


        //关于drawable  http://blog.csdn.net/u010142437/article/details/51986603
        /**
         * SDK升级到1.5以后，当文本输入框（EditText及其子类）获得焦点后，会弹出系统自带的软键盘
         为了实现一些自定义的功能，就稍微研究了下

         当layout中有多个EditText，把每个控件的android:singleLine的属性都被设置成true的情况下，软键盘的Enter键上的文字会变成“Next”，按下后下个EditText会自动获得焦点（实现了“Next”的功能）；当最后一个控件获得焦点的时候，Enter键上的文字会变成“Done”，按下后软键盘会自动隐藏起来

         把EditText的Ime Options属性设置成不同的值，Enter键上可以显示不同的文字或图案
         actionNone : 回车键，按下后光标到下一行
         actionGo ： Go，
         actionSearch ： 一个放大镜
         actionSend ： Send
         actionNext ： Next
         actionDone ： Done，隐藏软键盘，即使不是最后一个文本输入框
         （还有其他一些值可以设定，不过偷懒，没有看：-））

         设置文本框的OnKeyListener，当keyCode = 66的时候，表明Enter键被按下，就可以编写自己希望的Action了
         */
    }

    /*private void adjustTabLayout(RadioButton rbState, RadioButton rbSetting, RadioButton rbVideo, RadioButton rbWave, RadioButton rbSupport) {
        Drawable drawableState = ContextCompat.getDrawable(this, R.drawable.main_tab_setting_selector);
        drawableState.setBounds(0, 0, 56, 58);
        rbState.setCompoundDrawables(null, drawableState, null, null);

        Drawable drawableSetting = ContextCompat.getDrawable(this, R.drawable.main_tab_setting_selector);
        drawableSetting.setBounds(0, 0, 56, 58);
        rbSetting.setCompoundDrawables(null, drawableSetting, null, null);

        Drawable drawableVideo = ContextCompat.getDrawable(this, R.drawable.main_tab_camera_selector);
        drawableVideo.setBounds(0, 0, 45, 58);
        rbVideo.setCompoundDrawables(null, drawableVideo, null, null);

        Drawable drawableWave = ContextCompat.getDrawable(this, R.drawable.main_tab_wave_selector);
        drawableWave.setBounds(0, 0, 56, 58);
        rbWave.setCompoundDrawables(null, drawableWave, null, null);

        Drawable drawableSupport = ContextCompat.getDrawable(this, R.drawable.main_tab_support_selector);
        drawableSupport.setBounds(0, 0, 56, 58);
        rbSupport.setCompoundDrawables(null, drawableSupport, null, null);
    }*/

    private void initEvents() {
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = null;
                switch (checkedId) {
                    case R.id.rb_state:
                        StateFragment stateFragment = new StateFragment();
                        fragment = stateFragment;
                        tabIndex = 0;
                        break;

                    case R.id.rb_setting:
                        SettingFragment settingFragment = new SettingFragment();
                        fragment = settingFragment;
                        tabIndex = 1;
                        break;

                    case R.id.rb_support:
                        SupportFragment supportFragment = new SupportFragment();
                        fragment = supportFragment;
                        tabIndex = 2;
                        break;

                    case R.id.rb_user:
                        UserFragment userFragment = new UserFragment();
                        fragment = userFragment;
                        tabIndex = 3;
                        break;
                }
                transaction.replace(R.id.fl_main_content, fragment);
                transaction.commit();
            }
        });
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        rgGroup.check(tabViewIds[tabIndex]);
        Log.e(TAG, "onResume" + tabIndex);
    }*/


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("BUNDLE_CHECKED_TAB", tabIndex);
    }
}
