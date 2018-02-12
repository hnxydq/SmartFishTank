package com.tfxiaozi.smartfishtank.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.constants.Constant;
import com.tfxiaozi.smartfishtank.dao.VideoDao;
import com.tfxiaozi.smartfishtank.db.CameraResource;
import com.tfxiaozi.smartfishtank.model.LocalWifiConfig;
import com.tfxiaozi.smartfishtank.model.SearchDeviceConfig;
import com.tfxiaozi.smartfishtank.model.SearchDeviceConfigResp;
import com.tfxiaozi.smartfishtank.net.ConfigWifiThread;
import com.tfxiaozi.smartfishtank.net.SearchDeviceThread;
import com.tfxiaozi.smartfishtank.utils.DensityUtils;
import com.tfxiaozi.smartfishtank.utils.NetworkUtils;
import com.tfxiaozi.smartfishtank.utils.ToastUtils;
import com.tfxiaozi.smartfishtank.widget.CameraPop;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.File;

import static com.tfxiaozi.smartfishtank.R.mipmap.recoding;
import static com.tfxiaozi.smartfishtank.R.mipmap.record;

/**
 * Created by dongqiang on 2016/9/17.
 */
public class VideoFragment extends Fragment {

    private static final String TAG = VideoFragment.class.getSimpleName();
    private TextView tvMainTitle;
    private Context context;
    private ImageView ivSetting, ivSnap, ivRecord;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private CameraPop cameraPop;
    private LibVLC libVLC;
    private MediaRecorder mRecorder;
    private boolean isRecording = false;
    private File videoFile;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case Constant.SEARCH_DEVICE_SUCCESS:
                    progressDialog.cancel();
                    removeMessages(Constant.SEARCH_DEVICE_TIMEOUT);
                    SearchDeviceConfigResp searchDeviceConfigResp = (SearchDeviceConfigResp) msg.obj;
                    ToastUtils.showLong(context, "成功搜索到设备：" + searchDeviceConfigResp.getParam().getServerip());
                    prepareRtsp(searchDeviceConfigResp);
                    break;

                case Constant.SEARCH_DEVICE_FAIL:
                    progressDialog.cancel();
                    removeMessages(Constant.SEARCH_DEVICE_TIMEOUT);
                    Log.d(TAG, "search fail.");
                    break;

                case Constant.SEARCH_DEVICE_TIMEOUT:
                    progressDialog.cancel();
                    Log.d(TAG, "search timeout.");
                    break;

                case Constant.VIDEO_PLAY:
                    final String rtspUri = (String)msg.obj;
                    new Thread(){
                        @Override
                        public void run() {
                            Log.d(TAG, "-------->url=" + rtspUri);
                            if(!TextUtils.isEmpty(rtspUri)) {
                                Uri uri = Uri.parse(rtspUri);
                                Media media = new Media(libVLC, uri);
                                mediaPlayer.setMedia(media);
                                mediaPlayer.play();
                            } else {
                                ToastUtils.showLong(context, "实时影像暂无法播放");
                            }
                        }
                    }.start();
                    break;

                case Constant.VIDEO_STOP:

                    break;
            }
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        libVLC = new LibVLC();
        mediaPlayer = new MediaPlayer(libVLC);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_frament, container, false);
        context = getActivity();
        initViews(view);
        initEvents();
        playdefault();
        return view;
    }

    private void playdefault() {
        int count = VideoDao.getVideoServerCount(context);
        if(count > 0) {
            CameraResource cameraResource = VideoDao.selectOne(context);
            if(cameraResource != null) {
                String rtspUrl = cameraResource.getRtspAddr();
                if(!TextUtils.isEmpty(rtspUrl)) {
                    Message msg = new Message();
                    msg.what = Constant.VIDEO_PLAY;
                    msg.obj = rtspUrl;
                    mHandler.sendMessage(msg);
                } else {
                    ToastUtils.showLong(context, "暂无可播放源，请搜索设备...");
                }
            }
        } else {
            ToastUtils.showLong(context, "暂无可播放源，请搜索设备...");
        }

    }

    private void initViews(View view) {
        ivSetting = (ImageView) view.findViewById(R.id.title_setting);
        ivSnap = (ImageView) view.findViewById(R.id.iv_snap);
        ivRecord = (ImageView) view.findViewById(R.id.iv_record);
        ivSetting.setVisibility(View.VISIBLE);
        surfaceView = (SurfaceView) view.findViewById(R.id.video_surface);
        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.setKeepScreenOn(true);
        IVLCVout vlcVout = mediaPlayer.getVLCVout();
        vlcVout.setVideoView(surfaceView);
        vlcVout.attachViews();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在配置设备，请等待...");
    }

    private void initEvents() {
        ivSetting.setOnClickListener(onClickListener);
        ivSnap.setOnClickListener(onClickListener);
        ivRecord.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            switch (viewId) {
                case R.id.title_setting:
                    /*Intent intent = new Intent(context, VideoSettingActivity.class);
                    context.startActivity(intent);*/
                    cameraPop = new CameraPop(context, popWinClickListner,
                            DensityUtils.dp2px(context, 140), DensityUtils.dp2px(context, 80));
                    cameraPop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(!hasFocus) {
                                cameraPop.dismiss();
                            }
                        }
                    });

                    cameraPop.setFocusable(true);
                    cameraPop.setTouchable(true);
                    cameraPop.showAsDropDown(ivSetting, 0, DensityUtils.dp2px(context, 5));
                    cameraPop.update();
                    break;

                case R.id.iv_snap:

                    break;

                case R.id.iv_record:
                    try {
                        if(!isRecording) {
                            // Check for permissions
                            int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                            // If we don't have permissions, ask user for permissions
                            if (permission != PackageManager.PERMISSION_GRANTED) {
                                String[] PERMISSIONS_STORAGE = {
                                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                                };
                                int REQUEST_EXTERNAL_STORAGE = 1;

                                ActivityCompat.requestPermissions(
                                        getActivity(),
                                        PERMISSIONS_STORAGE,
                                        REQUEST_EXTERNAL_STORAGE
                                );
                            }
                            if (!Environment.getExternalStorageState().equals( android.os.Environment.MEDIA_MOUNTED)) {
                                Toast.makeText(context, "SD卡不存在，请插入SD卡！" , Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // 创建保存录制视频的视频文件
                            videoFile = new File(Environment.getExternalStorageDirectory().getCanonicalFile() + "/" + System.currentTimeMillis()+ ".3gp");
                            // 创建MediaPlayer对象
                            mRecorder = new MediaRecorder();
                            mRecorder.reset();
                            // 解決豎屏視頻被旋轉90°的錯誤
                            Camera c = Camera.open();
                            Camera.Parameters parameters = c.getParameters();
                            c.startPreview();
                            parameters.setRotation(90);
                            c.setParameters(parameters);
                            c.unlock();
                            mRecorder.setCamera(c);
                            mRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());  //①
                            // 设置从麦克风采集声音(或来自录像机的声音AudioSource.CAMCORDER)
                            mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                            // 设置从摄像头采集图像
                            mRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
                            // 设置视频文件的输出格式
                            // 必须在设置声音编码格式、图像编码格式之前设置
                            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                            // 设置声音编码的格式
                            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                            // 设置图像编码的格式
                            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                            mRecorder.setOutputFile(videoFile.getAbsolutePath());
                            // 指定使用SurfaceView来预览视频

                            mRecorder.prepare();
                            // 开始录制
                            mRecorder.start();
                            System.out.println("---recording---");
                            // 让stop按钮可用。
                            isRecording = true;
                            ivRecord.setImageResource(recoding);
                        } else {
                            if(mRecorder!=null){
                                mRecorder.stop();
                                mRecorder.release();
                                mRecorder = null;
                                isRecording = false;
                                ivRecord.setImageResource(record);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };


    View.OnClickListener popWinClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_config_wifi:
                    configWifi();
                    break;

                case R.id.ll_search_device:
                    searchDevice();
                    break;
            }
            if(cameraPop != null) {
                cameraPop.dismiss();
            }

        }
    };

    private void configWifi() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_wifi_setting, null);
        final EditText etSsid = (EditText) view.findViewById(R.id.wifi_ssid);
        final EditText etPassword = (EditText) view.findViewById(R.id.wifi_et_password);
        final ImageView ivPwdVisible = (ImageView) view.findViewById(R.id.wifi_eye_on);
        final ImageView ivPwdInvisible = (ImageView) view.findViewById(R.id.wifi_eye_close);
        final Button confirm = (Button) view.findViewById(R.id.btn_confirm_wifi_config);
        final Button cancel = (Button) view.findViewById(R.id.btn_cancel_wifi_config);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context).setCancelable(false);
        final AlertDialog dialog = builder.setView(view).create();
        dialog.show();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constant.LOCAL_WIFI_CONFIG_SUCCESS:
                        progressDialog.cancel();
                        dialog.dismiss();
                        removeMessages(Constant.LOCAL_WIFI_CONFIG_TIMEOUT);
                        ToastUtils.showLong(context, "已成功配置，请将手机接入相同wifi");
                        break;

                    case Constant.LOCAL_WIFI_CONFIG_FAIL:
                        progressDialog.cancel();
                        removeMessages(Constant.LOCAL_WIFI_CONFIG_TIMEOUT);
                        ToastUtils.showLong(context, "配置失败");
                        break;
                    case Constant.LOCAL_WIFI_CONFIG_TIMEOUT:
                        progressDialog.cancel();
                        ToastUtils.showLong(context, "配置超时");
                        break;
                }
            }
        };

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
                    //ivClearPwd.setVisibility(View.VISIBLE);
                    etPassword.setSelection(s.length());
                } else {
                    //ivClearPwd.setVisibility(View.INVISIBLE);
                }
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_confirm_wifi_config:
                        String ssid = etSsid.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();
                        if(TextUtils.isEmpty(ssid) || TextUtils.isEmpty(password)) {
                            ToastUtils.showLong(context, "WiFi ssid和密码都需要填写!");
                            return;
                        } else {
                            //request network to pass data.
                            progressDialog.show();
                            //成功配置后返回消息：
                            String data = "";
                            LocalWifiConfig wifiConfig = new LocalWifiConfig();
                            wifiConfig.setAuth(Constant.LOCAL_WIFI_CONFIG_AUTH);
                            wifiConfig.setCommond(Constant.LOCAL_WIFI_CONFIG_COMMOND);
                            LocalWifiConfig.Param param = wifiConfig.new Param();
                            param.setSsid(ssid);
                            param.setPwd(password);
                            wifiConfig.setParam(param);
                            data = new Gson().toJson(wifiConfig);
                            Log.e(TAG, data);
                            new ConfigWifiThread(Constant.LOCAL_SERVER_HOST, Constant.PORT, data, handler).start();
                            //取消进度条
                            //handler.sendEmptyMessageDelayed(handler_key.CONFIG_SUCCESs.ordinal(), 1000);
                            handler.sendEmptyMessageDelayed(Constant.LOCAL_WIFI_CONFIG_TIMEOUT, 15000);
                        }
                        break;

                    case R.id.btn_cancel_wifi_config:
                        dialog.cancel();
                        break;

                    case R.id.wifi_eye_on:
                        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        ivPwdInvisible.setVisibility(View.VISIBLE);
                        ivPwdVisible.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.wifi_eye_close:
                        etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        ivPwdInvisible.setVisibility(View.INVISIBLE);
                        ivPwdVisible.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };

        confirm.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        ivPwdVisible.setOnClickListener(onClickListener);
        ivPwdInvisible.setOnClickListener(onClickListener);
    }

    private void searchDevice() {
        boolean wifiConnected = NetworkUtils.isWifiConnected(context);
        if(!wifiConnected) {
            ToastUtils.showLong(context, "请检查wifi是否连接");
            return;
        }

        progressDialog.show();
        SearchDeviceConfig searchDeviceConfig = new SearchDeviceConfig();
        searchDeviceConfig.setAuth(Constant.LOCAL_WIFI_CONFIG_AUTH);
        searchDeviceConfig.setCommond(Constant.SEARCH_DEVICE_CONFIG_COMMOND);
        SearchDeviceConfig.Param param = searchDeviceConfig.new Param();
        searchDeviceConfig.setParam(param);
        String data = new Gson().toJson(searchDeviceConfig);
        Log.e(TAG, data);

        String ip = NetworkUtils.getLocalIpAddress(context);
        if(!TextUtils.isEmpty(ip)) {
            ip = ip.substring(0, ip.lastIndexOf(".")+1) + "255";
        } else {
            ToastUtils.showLong(context, "网络异常");
            mHandler.sendEmptyMessage(Constant.SEARCH_DEVICE_FAIL);
            return;
        }
        Log.d(TAG, "broadcast ip: " + ip);
        new SearchDeviceThread(ip, Constant.PORT, data, mHandler).start();
        mHandler.sendEmptyMessageDelayed(Constant.SEARCH_DEVICE_TIMEOUT, 15000);
    }


    private void prepareRtsp(SearchDeviceConfigResp deviceConfigResp) {
        SearchDeviceConfigResp.Param param = deviceConfigResp.getParam();
        String sn = param.getSn();
        String serverip = param.getServerip();
        String rtspAddr = param.getRtspaddr();
        CameraResource cameraResource = new CameraResource();
        cameraResource.setSn(sn);
        cameraResource.setServerIp(serverip);
        cameraResource.setRtspAddr(rtspAddr);
        VideoDao.insertOne(context, cameraResource);
        Log.d(TAG, "-->" + rtspAddr);
        Message msg = Message.obtain();
        msg.what = Constant.VIDEO_PLAY;
        msg.obj = rtspAddr;
        mHandler.sendMessageDelayed(msg, 1000);
    }


    @Override
    public void onPause() {
        super.onPause();
        mHandler.sendEmptyMessage(Constant.VIDEO_STOP);
    }
}
