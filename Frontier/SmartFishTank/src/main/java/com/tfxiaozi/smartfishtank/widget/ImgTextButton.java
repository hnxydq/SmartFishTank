package com.tfxiaozi.smartfishtank.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;

/**
 * Created by dongqiang on 2016/10/18.
 */

public class ImgTextButton extends RelativeLayout {

    private Context mContext;
    private ImageView mImgView;
    private TextView mTextView;

    public ImgTextButton(Context context) {
        this(context, null);
    }

    public ImgTextButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImgTextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.imgtext_btn, this, true);
        mContext = context;
        mImgView = (ImageView)findViewById(R.id.img);
        mTextView = (TextView)findViewById(R.id.text);
    }

    /*设置图片接口*/
    public void setImageResource(int resId){
        mImgView.setImageResource(resId);
    }

    /*设置文字接口*/
    public void setText(String str){
        mTextView.setText(str);
    }
    /*设置文字大小*/
    public void setTextSize(float size){
        mTextView.setTextSize(size);
    }
}
