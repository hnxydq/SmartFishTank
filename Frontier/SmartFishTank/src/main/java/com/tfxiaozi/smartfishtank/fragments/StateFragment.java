package com.tfxiaozi.smartfishtank.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.adapter.LedSearchAdapter;
import com.tfxiaozi.smartfishtank.constants.Constant;
import com.tfxiaozi.smartfishtank.model.LedListItemData;
import com.tfxiaozi.smartfishtank.model.weather.WeatherInfo;
import com.tfxiaozi.smartfishtank.utils.GsonImpl;
import com.tfxiaozi.smartfishtank.utils.LocationService;
import com.tfxiaozi.smartfishtank.utils.ToastUtils;
import com.tfxiaozi.smartfishtank.widget.MyListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.tfxiaozi.smartfishtank.R.id.temperature;

/**
 * Created by dongqiang on 2016/9/11.
 */
public class StateFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = StateFragment.class.getSimpleName();
    private static final int IP_CONFIG_SUCCESS = 0;
    private static final int IP_CONFIG_FAILED = 1;
    private static final int IP_CONFIG_TIMEOUT = 2;
    private final int SDK_PERMISSION_REQUEST = 127;

    private Context context;
    private TextView tvTitle;
    private ImageView ivWeather;
    private TextView tvCity;
    private TextView tvTemperature;
    private TextView tvWaterTemper;
    private TextView tvWeather;
    private ImageView ivPulse;
    private Button btnManualIp;
    private Button btnAutoSearch;
    private ProgressDialog progressDialog;
    private AlertDialog dialog;
    private LedSearchAdapter ledSearchAdapter;
    private MyListView searchLedListView;
    private ScrollView scrollView;
    private List<LedListItemData> list = new ArrayList<LedListItemData>();
    private String permissionInfo;
    private LocationClient mLocationClient;
    private BDLocationListener myListener = new MyLocationListener();
    private LocationService locationService;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IP_CONFIG_SUCCESS:
                    handler.removeMessages(IP_CONFIG_TIMEOUT);
                    progressDialog.cancel();
                    break;
                case IP_CONFIG_FAILED:
                    ToastUtils.showLong(context, getString(R.string.config_failed));
                    progressDialog.cancel();
                    break;
                case IP_CONFIG_TIMEOUT:
                    ToastUtils.showLong(context, getString(R.string.config_timeout));
                    progressDialog.cancel();
                    break;
                case Constant.LED_DELETE:
                    ToastUtils.showLong(context, "msg=" + msg.obj);
                    list.remove(msg.arg1);
                    ledSearchAdapter.notifyDataSetChanged();
                    //TODO:使用obj得到对应的ip，删除数据库记录

                    break;
            }

        }
    };


    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if(context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }
        }else{
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.state_frament, container, false);
        context = getActivity();
        // after andrioid m,must request Permiision on runtime
        getPersimmions();
        mLocationClient = new LocationClient(getActivity().getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initViews(view);
        initEvents();
        initData();
        return view;
    }

    private void initViews(View view) {
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        tvTitle = (TextView) view.findViewById(R.id.title_main_text);
        ivWeather = (ImageView) view.findViewById(R.id.weather_icon);
        tvWeather = (TextView) view.findViewById(R.id.tv_weather);
        tvCity = (TextView) view.findViewById(R.id.city);
        tvTemperature = (TextView) view.findViewById(temperature);
        tvWaterTemper = (TextView) view.findViewById(R.id.water_temper);
        ivPulse = (ImageView) view.findViewById(R.id.iv_pulse);
        btnManualIp = (Button) view.findViewById(R.id.btn_manual_ip);
        btnAutoSearch = (Button) view.findViewById(R.id.btn_auto_search);
        searchLedListView = (MyListView) view.findViewById(R.id.lv_device_list);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.state);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(getString(R.string.loading));
        scrollView.smoothScrollTo(0, 0);
    }

    private void initEvents() {
        btnManualIp.setOnClickListener(this);
        btnAutoSearch.setOnClickListener(this);
        searchLedListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            AlertDialog dialog1 = null;
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                View layout = LayoutInflater.from(context).inflate(R.layout.dialog_edit_name, null);
                final EditText etName = (EditText) layout.findViewById(R.id.et_input_name);
                Button btnCancel = (Button) layout.findViewById(R.id.btn_cancel_edit_name);
                Button btnConfirm = (Button) layout.findViewById(R.id.btn_confirm_edit_name);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(layout).setCancelable(false);
                dialog1 = builder.create();
                dialog1.show();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.cancel();
                    }
                });
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                        //save
                        String name = etName.getText().toString().trim();
                        if(!TextUtils.isEmpty(name)) {
                            updateListViewItem(name);
                        }
                    }
                });
                return true;
            }
        });

        searchLedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LedListItemData data = (LedListItemData) searchLedListView.getAdapter().getItem(position);
                if(data != null) {
                    boolean b = data.isChecked();
                    data.setChecked(!b);
                    ledSearchAdapter.notifyDataSetChanged();

                    //TODO:状态存入数据库中
                }
            }
        });
    }

    /**
     * 更新LED板ip昵称
     * @param name
     */
    private void updateListViewItem(String name) {
        //修改数据库中对应ip地址的昵称 table(ip, name)


    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系
        //option.setScanSpan(0);
        //int span=1000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        mLocationClient.requestLocation();
    }


    private void initData() {
        ledSearchAdapter = new LedSearchAdapter(context, list, handler);
        ledSearchAdapter.notifyDataSetChanged();
        searchLedListView.setAdapter(ledSearchAdapter);
        if(list != null && list.size() != 0) {
            searchLedListView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_manual_ip:
                manualEditIp();
                break;

            case R.id.btn_auto_search:

                break;
        }
    }

    private void manualEditIp() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_ip, null);
        EditText etInputIp = (EditText) view.findViewById(R.id.et_input_ip);
        Button btnCancelIpConfig = (Button) view.findViewById(R.id.btn_cancel_ip_config);
        Button btnIpConfig = (Button) view.findViewById(R.id.btn_ip_confirm_config);
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setCancelable(false);
        dialog = builder.setView(view).create();
        dialog.show();

        btnCancelIpConfig.setOnClickListener(ipOnClickListener);
        btnIpConfig.setOnClickListener(ipOnClickListener);
    }


    View.OnClickListener ipOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel_ip_config:
                    dialog.cancel();
                    break;
                case R.id.btn_ip_confirm_config:
                    dialog.dismiss();
                    progressDialog.show();
                    searchLedList();
                    break;
            }
        }
    };

    /**
     * 手动设置Ip搜索LED
     */
    private void searchLedList() {
        //TODO:
        //成功：
        handler.sendEmptyMessageDelayed(IP_CONFIG_SUCCESS, 1000);
        //搜索10秒
        //handler.sendEmptyMesssage(2);
    }

    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e(TAG, "type=" + location.getLocType());
            //Receive Location
            String city = location.getCity();
            Log.e(TAG, "city=" + city);
            if(TextUtils.isEmpty(city)) {
                city = getString(R.string.location_fail_tip);
            }
            city = city.substring(0, city.indexOf(getString(R.string.citychar)));
            Log.d(TAG, "city=" + city);
            getWeatherInfo(city);
            tvCity.setText(city);
        }
    }

    private void getWeatherInfo(String cityName) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(Constant.HF_WEATHER_URL)
                    .append("?city=")
                    .append(URLEncoder.encode(cityName, "UTF-8"))
                    .append("&key=")
                    .append(Constant.HF_KEY);
            String url = sb.toString();
            Log.d(TAG, "url=" + url);
            // 1 创建RequestQueue对象
            mRequestQueue = Volley.newRequestQueue(context);
            // 2 创建StringRequest对象
            mStringRequest = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            StringBuffer sbf = new StringBuffer(response);
                            sbf.deleteCharAt(11);
                            sbf.deleteCharAt(15);
                            sbf.delete(22, 26);
                            WeatherInfo weatherInfo = GsonImpl.get().toObject(sbf.toString(), WeatherInfo.class);
                            Log.d(TAG, "weather=" + sbf.toString());
                            WeatherInfo.HeWeatherdataserviceBean weatherdataserviceBean = weatherInfo.getHeWeatherdataservice().get(0);
                            if(weatherdataserviceBean != null) {
                                String status = weatherdataserviceBean.getStatus();
                                if(status.equals("ok")) {
                                    WeatherInfo.HeWeatherdataserviceBean.NowBean nowBean = weatherdataserviceBean.getNow();
                                    //String weather = nowBean.getCond().getTxt();
                                    String imgCode = nowBean.getCond().getCode();
                                    String imgName = "w_" + imgCode + ".png";
                                    ivWeather.setImageResource(getImageIdByName(imgName));
                                    String temperature = nowBean.getTmp();
                                    tvWeather.setText(getStringByName("s" + imgCode));
                                    tvTemperature.setText(temperature+"℃");
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "请求错误:" + error.toString());
                }
            });
            // 3 将StringRequest添加到RequestQueue
            mRequestQueue.add(mStringRequest);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.toString());
        }
    }


    /**
     * 根据图片名称获取R.java中对应的id
     *
     * @param name
     * @return
     */
    public static int getImageIdByName (String name) {
        int value = 0;
        if (!TextUtils.isEmpty(name)) {
            if (name.indexOf(".") != -1) {
                name = name.substring(0, name.indexOf("."));
            }
            Class<R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        return value;
    }

    /**
     * 根据字符串名字获取string.xml中的字符串
     * @param name
     * @return
     */
    public String getStringByName(String name) {
        // string.xml内配置的ID
        if(getActivity() != null){
            int stringID = getActivity().getResources().getIdentifier(name,// string.xml内配置的名字
                    "string", context.getPackageName());
            //string.xml内配置的具体内容
            return getString(stringID);
        }
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

}
