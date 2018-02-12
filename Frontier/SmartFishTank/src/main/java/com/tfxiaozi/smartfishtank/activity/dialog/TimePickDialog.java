package com.tfxiaozi.smartfishtank.activity.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.utils.ToastUtils;

import java.util.Calendar;

public class TimePickDialog {

    private TimeChangedListener listener;

    public void show(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_time, null);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(Calendar.MINUTE);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("setOnClickListener","onClick listener:"+listener);
                StringBuffer sb = new StringBuffer();
                sb.append(timePicker.getCurrentHour() < 10 ? "0" + timePicker.getCurrentHour() : timePicker.getCurrentHour())
                        .append(":").append(timePicker.getCurrentMinute() < 10 ? "0" + timePicker.getCurrentMinute() : timePicker.getCurrentMinute());

                if (listener != null && isVerifySuccess(sb.toString())) {
                    listener.onTimeChanged(sb.toString());
                    dialog.cancel();
                }else{
                    ToastUtils.showShort(context,verifyFailedTips());
                }


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public boolean isVerifySuccess(String result){
        return true ;
    }

    public String verifyFailedTips(){
        return "验证失败";
    }

    public void setListener(TimeChangedListener listener) {
        this.listener = listener;
    }

    public interface TimeChangedListener {
        void onTimeChanged(String time);
    }
}
