package com.tfxiaozi.smartfishtank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.activity.dialog.TimePickDialog;
import com.tfxiaozi.smartfishtank.db.DbManager;
import com.tfxiaozi.smartfishtank.model.TimmingData;
import com.tfxiaozi.smartfishtank.utils.TimeUtils;
import com.tfxiaozi.smartfishtank.widget.EditText_;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class TimingSettingAdapter extends BaseAdapter {

    private static final String TAG = TimingSettingAdapter.class.getSimpleName();
    private Context context;
    private List<TimmingData> list;
    private TimmingDataChangedListener changedListener ;
    private boolean isDeleteAble = false ;
    private boolean isCanEdit= true ;


    public void setCanEdit(boolean canEdit) {
        isCanEdit = canEdit;
    }

    public TimingSettingAdapter(Context context, List<TimmingData> list) {
        this.context = context;
        this.list = list;
    }


    public void setChangedListener(TimmingDataChangedListener changedListener) {
        this.changedListener = changedListener;
    }

    public void setDeleteAble(boolean deleteAble) {
        isDeleteAble = deleteAble;
    }

    public boolean isDeleteAble() {
        return isDeleteAble;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final TimmingData data = (TimmingData) getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.timming_item_layout, null);
            viewHolder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
            viewHolder.et1 = (EditText_) convertView.findViewById(R.id.et1);
            viewHolder.et2 = (EditText_) convertView.findViewById(R.id.et2);
            viewHolder.et3 = (EditText_) convertView.findViewById(R.id.et3);
            viewHolder.et4 = (EditText_) convertView.findViewById(R.id.et4);
            viewHolder.et5 = (EditText_) convertView.findViewById(R.id.et5);
            viewHolder.et6 = (EditText_) convertView.findViewById(R.id.et6);
            viewHolder.et7 = (EditText_) convertView.findViewById(R.id.et7);
            viewHolder.ets.add(viewHolder.et1);
            viewHolder.ets.add(viewHolder.et2);
            viewHolder.ets.add(viewHolder.et3);
            viewHolder.ets.add(viewHolder.et4);
            viewHolder.ets.add(viewHolder.et5);
            viewHolder.ets.add(viewHolder.et6);
            viewHolder.ets.add(viewHolder.et7);
            viewHolder.delete_btn =(Button) convertView.findViewById(R.id.delete_btn);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.time_tv.setText(data.getTime());
        if(isDeleteAble()){
            viewHolder.delete_btn.setVisibility(View.VISIBLE);
        }else{
            viewHolder.delete_btn.setVisibility(View.GONE);
        }

        for(EditText_ e:viewHolder.ets){
            if(isCanEdit){
                e.setEnabled(true);
                e.setFocusable(true);
                e.setFocusableInTouchMode(true);
            }else{
                e.setEnabled(false);
                e.setFocusable(false);
                e.setFocusableInTouchMode(false);
            }
            e.setTextColor(context.getResources().getColor(R.color.black));
        }

        if (data.getDatas() != null) {
            for (int i = 0; i < data.getDatas().length; i++) {
                final int j = i ;
                viewHolder.ets.get(i).setText(data.getDatas()[i] + "");
                viewHolder.ets.get(i).setTextChanged(new EditText_.EditTextChangedListener() {
                    @Override
                    public void onNumberChanged(int num) {
                        data.getDatas()[j] = num;
                        DbManager.getInstance(context).getDaoSession().getTimmingdbDataDao().update(data.toTimmingdbData());
                        if(changedListener != null){
                            changedListener.onDataChanged();
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        }

        viewHolder.time_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickDialog dialog = new TimePickDialog(){
                    @Override
                    public boolean isVerifySuccess(String result) {

                        for(int i=0;i<list.size();i++){
                            if(TimeUtils.parseDateTime(list.get(i).getTime()) == TimeUtils.parseDateTime(result)){
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override
                    public String verifyFailedTips() {
                        return context.getString(R.string.add_timing_line_warn);
                    }
                };

                dialog.show(context);
                dialog.setListener(new TimePickDialog.TimeChangedListener() {
                    @Override
                    public void onTimeChanged(String time) {
                        Log.i("onTimeChanged", "time:" + time);
                        viewHolder.time_tv.setText(time);
                        data.setTime(time);
                        DbManager.getInstance(context).getDaoSession().getTimmingdbDataDao().update(data.toTimmingdbData());
                        if(changedListener != null){
                            changedListener.onDataChanged();
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        });

        viewHolder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbManager.getInstance(context).getDaoSession().getTimmingdbDataDao().delete(list.remove(position).toTimmingdbData());
                if(changedListener != null){
                    notifyDataSetChanged();
                    changedListener.onDataChanged();
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView time_tv;
        ArrayList<EditText_> ets = new ArrayList<>();
        EditText_ et1;
        EditText_ et2;
        EditText_ et3;
        EditText_ et4;
        EditText_ et5;
        EditText_ et6;
        EditText_ et7;
        Button delete_btn;
    }

    public interface  TimmingDataChangedListener {
        void onDataChanged();
    }

}

