package com.tfxiaozi.smartfishtank.activity.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.BaseActivity;
import com.tfxiaozi.smartfishtank.adapter.TimingSettingAdapter;
import com.tfxiaozi.smartfishtank.db.DbManager;
import com.tfxiaozi.smartfishtank.db.TimmingdbData;
import com.tfxiaozi.smartfishtank.model.TimmingData;
import com.tfxiaozi.smartfishtank.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TimingSettingActivity extends BaseActivity implements TimingSettingAdapter.TimmingDataChangedListener, View.OnClickListener {

    private final String TAG = TimingSettingActivity.class.getSimpleName();
    private static final int MSG_SETUP_GRAPH = 0;
    public final static String EXTRA__FROM_FALG = "extra_from_flag";
    public final static int FROM_EDIT = 0X1;
    public final static int FROM_DISPLAY = 0X2;
    private int currentFromFlag = FROM_EDIT;

    private TextView tvMainTitle;
    private ImageView ivBack;
    private ImageView ivAddLine;
    private ImageView ivDeleteLine;
    private ImageView ivPreview;
    private TimingSettingAdapter adapter;
    private ListView timingListview;
    private GraphView graph;
    private Button btnT51, btnT52, btnT53;
    private View vT511, vT512, vT521, vT522, vT531, vT532;
    private List<TimmingData> datas = new ArrayList<>();
    private ArrayList<LineGraphSeries<DataPoint>> series = new ArrayList<>();
    private DbManager dbManager;
    private boolean isEditMode = false;


    private final int[] COLORS = {Color.parseColor("#B22222")
            , Color.parseColor("#EEE685"), Color.parseColor("#E066FF"), Color.parseColor("#CDCD00")
            , Color.parseColor("#CD6889"), Color.parseColor("#C6E2FF"), Color.parseColor("#BCEE68")};

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SETUP_GRAPH:
                    setupGraph();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timming_setting_layout);
        dbManager = DbManager.getInstance(this);
        currentFromFlag = getIntent().getIntExtra(EXTRA__FROM_FALG, FROM_EDIT);
        loadData();
        initView();
        initEvents();
        setupData();
        setupGraph();
    }

    private void loadData() {
        List<TimmingdbData> ds = dbManager.getDaoSession().getTimmingdbDataDao().loadAll();
        Log.i(TAG, "DTS SIZE 1 :" + ds.size());
        for (TimmingdbData td : ds) {
            Log.i(TAG, "ID:" + td.getId());
            datas.add(td.toTimmingData());
        }
    }

    private void initView() {
        tvMainTitle = (TextView) findViewById(R.id.title_main_text);
        tvMainTitle.setText(getString(R.string.timing_lable));
        tvMainTitle.setVisibility(View.VISIBLE);
        ivBack = (ImageView) findViewById(R.id.title_back);
        ivBack.setVisibility(View.VISIBLE);
        timingListview = (ListView) findViewById(R.id.timming_listView);
        LinearLayout headView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.timming_listview_head_layout, null);
        int childCount = headView.getChildCount();
        for (int i = 1; i < childCount; i++) {
            headView.getChildAt(i).setBackgroundColor(COLORS[i - 1]);
        }

        timingListview.addHeaderView(headView);
        ivAddLine = (ImageView) findViewById(R.id.iv_add_line);
        ivDeleteLine = (ImageView) findViewById(R.id.iv_delete_line);
        ivPreview = (ImageView) findViewById(R.id.iv_preview);
        btnT51 = (Button) findViewById(R.id.btn_t5_1);
        btnT52 = (Button) findViewById(R.id.btn_t5_2);
        btnT53 = (Button) findViewById(R.id.btn_t5_3);
        vT511 = findViewById(R.id.t5_1_1);
        vT512 = findViewById(R.id.t5_1_2);
        vT521 = findViewById(R.id.t5_2_1);
        vT522 = findViewById(R.id.t5_2_2);
        vT531 = findViewById(R.id.t5_3_1);
        vT532 = findViewById(R.id.t5_3_2);
        graph = (GraphView) findViewById(R.id.graph);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this, new SimpleDateFormat("HH:mm")));
        graph.getGridLabelRenderer().setNumHorizontalLabels(5); // only 4 because of the space
        graph.getGridLabelRenderer().setNumVerticalLabels(5);
        graph.getViewport().setMinX(TimeUtils.parseDateTime("00:00"));
        graph.getViewport().setMaxX(TimeUtils.parseDateTime("23:59"));
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(100);
        graph.getViewport().setMinY(0);
        graph.getGridLabelRenderer().setHumanRounding(false);

        if (currentFromFlag == FROM_EDIT) {
            ivAddLine.setVisibility(View.VISIBLE);
            ivDeleteLine.setVisibility(View.VISIBLE);
        } else if (currentFromFlag == FROM_DISPLAY) {
            ivAddLine.setVisibility(View.INVISIBLE);
            ivDeleteLine.setVisibility(View.INVISIBLE);
        }
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        ivAddLine.setOnClickListener(this);
        ivDeleteLine.setOnClickListener(this);
        ivPreview.setOnClickListener(this);
        btnT51.setOnClickListener(this);
        btnT52.setOnClickListener(this);
        btnT53.setOnClickListener(this);
    }

    private void setupGraph() {
        Collections.sort(datas);
        adapter.notifyDataSetChanged();
        series.clear();
        graph.removeAllSeries();
        for (int i = 0; i < 7; i++) {
            LineGraphSeries<DataPoint> s = new LineGraphSeries<DataPoint>();
            s.setDataPointsRadius(10);
            s.setDrawDataPoints(true);
            s.setColor(COLORS[i]);
            series.add(s);
        }

        for (int z = datas.size() - 1; z > 0; z--) {
            TimmingData d = datas.get(z);
            int[] datas = d.getDatas();
            Log.i(TAG, "setupGraph DATAS:" + datas);
            for (int i = 0; i < 7; i++) {
                series.get(i).appendData(new DataPoint(TimeUtils.parseDateTime(d.getTime()), datas[i]), true, 100);
            }
        }

        for (int j = 0; j < 7; j++) {
            graph.addSeries(series.get(j));
        }
    }

    private void setupData() {
        adapter = new TimingSettingAdapter(this, datas);
        if (currentFromFlag == FROM_EDIT) {
            adapter.setCanEdit(true);
        } else {
            adapter.setCanEdit(false);
        }
        adapter.setChangedListener(this);
        timingListview.setAdapter(adapter);
    }

    @Override
    public void onDataChanged() {
        handler.sendEmptyMessage(MSG_SETUP_GRAPH);
    }

    private void addData() {
        int[] d = {0, 0, 0, 0, 0, 0, 0};
        TimmingData td = new TimmingData(generateDefaultTime(), d);
        Long id = dbManager.getDaoSession().getTimmingdbDataDao().insert(td.toTimmingdbData());
        td.setId(id);
        datas.add(td);
        onDataChanged();
    }


    long div = 60000;
    private String generateDefaultTime() {
        String time = "";
        Date d = new Date();
        int miu = d.getMinutes() + 1;
        int hour = d.getHours();
        time = hour + ":" + miu;
        long current = TimeUtils.parseDateTime(time);
        Log.i(TAG,"generateDefaultTime current:"+current);
        if(isExitTime(current)){
            return iterationTime(current);
        }
        return time;
    }

    private boolean isExitTime(long time){
        for(int i=0;i<datas.size();i++){
            if(TimeUtils.parseDateTime(datas.get(i).getTime()) == time){
                Log.i(TAG,"iterationTime exit");
                return true;
            }
        }
        Log.i(TAG,"iterationTime not exit");
        return false;
    }

    private String iterationTime(long current){
        String t = TimeUtils.formatDate(current);
        if(isExitTime(current)){
            return iterationTime(current+div);
        }
        return t;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.title_back:
                finish();
                break;

            case R.id.iv_add_line:
                addData();
                break;

            case R.id.iv_delete_line:
                isEditMode = !isEditMode;
                if (isEditMode) {
                    ivDeleteLine.setImageResource(R.drawable.btn_done_line_selector);
                } else {
                    ivDeleteLine.setImageResource(R.drawable.btn_dec_line_selector);
                }
                adapter.setDeleteAble(isEditMode);
                adapter.notifyDataSetChanged();
                break;

            case R.id.iv_preview:

                break;

            case R.id.btn_t5_1:
                vT511.setBackgroundColor(Color.YELLOW);
                vT512.setBackgroundColor(Color.YELLOW);
                break;

            case R.id.btn_t5_2:
                vT521.setBackgroundColor(Color.YELLOW);
                vT522.setBackgroundColor(Color.YELLOW);
                break;

            case R.id.btn_t5_3:
                vT531.setBackgroundColor(Color.YELLOW);
                vT532.setBackgroundColor(Color.YELLOW);
                break;
        }
    }
}
