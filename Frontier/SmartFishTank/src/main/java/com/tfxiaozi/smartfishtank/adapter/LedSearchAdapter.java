package com.tfxiaozi.smartfishtank.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.constants.Constant;
import com.tfxiaozi.smartfishtank.model.LedListItemData;

import java.util.List;

/**
 * Created by dongqiang on 2016/10/14.
 */

public class LedSearchAdapter extends BaseAdapter {

    private static final String TAG = LedSearchAdapter.class.getSimpleName();
    private Context context;
    private List<LedListItemData> list;
    private ViewHolder viewHolder;
    private Handler handler;

    public LedSearchAdapter(Context context, List<LedListItemData> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
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

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.led_list_item, null);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.led_cb);
            viewHolder.bulb = (ImageView) convertView.findViewById(R.id.iv_bulb);
            viewHolder.ipAddress = (TextView) convertView.findViewById(R.id.tv_ip_address);
            viewHolder.ledName = (TextView) convertView.findViewById(R.id.tv_led_name);
            viewHolder.ledDelete = (ImageView) convertView.findViewById(R.id.iv_led_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LedListItemData itemData = list.get(position);
        boolean checked = itemData.isChecked();
        if(checked) {
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }

        if(viewHolder.checkBox.isChecked()) {
            viewHolder.bulb.setImageResource(R.mipmap.icon_bulb);
        } else {
            viewHolder.bulb.setImageResource(R.mipmap.icon_bulb_gray);
        }

        final String ip = itemData.getIp();
        if(!TextUtils.isEmpty(ip)) {
            viewHolder.ipAddress.setText(ip);
        }
        String ledName = itemData.getName();
        if(!TextUtils.isEmpty(ledName)) {
            viewHolder.ledName.setText(ledName);
        }

        /*viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewHolder.checkBox.setChecked(!isChecked);
                notifyDataSetChanged();
            }
        });*/

        viewHolder.ledDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                msg.what = Constant.LED_DELETE;
                msg.obj = ip;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });

        return convertView;
    }
}

class ViewHolder {
    CheckBox checkBox;
    ImageView bulb;
    TextView ipAddress;
    TextView ledName;
    ImageView ledDelete;
}
