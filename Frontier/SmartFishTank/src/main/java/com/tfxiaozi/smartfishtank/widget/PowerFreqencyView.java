package com.tfxiaozi.smartfishtank.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.tfxiaozi.smartfishtank.R;

/**
 * Created by dongqiang on 2016/10/19.
 */

public class PowerFreqencyView extends View {

    private final String TAG = PowerFreqencyView.class.getSimpleName();

    protected int mBorderColor = 0xFFd3d6da;

    protected int mContentColor = 0XFFFC00D1;

    protected int mTextColor = 0xFFFC00D1;

    private Paint linePaint, contentPaint, textPaint;

    private int maxLevel = 10;

    private int currentLevel = 6;

    private String text;

    public PowerFreqencyView(Context context) {
        this(context, null);
    }

    public PowerFreqencyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerFreqencyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttributes(attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(mBorderColor);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);

        contentPaint = new Paint();
        contentPaint.setColor(mContentColor);
        contentPaint.setAntiAlias(true);
        contentPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(mTextColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(sp2px(10));
    }

    private void obtainStyledAttributes(AttributeSet attributeSet) {
        TypedArray attributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.PowerFreqencyView);
        mBorderColor = attributes.getColor(R.styleable.PowerFreqencyView_pfv_border_color, Color.parseColor("#dedede"));
        mContentColor = attributes.getColor(R.styleable.PowerFreqencyView_pfv_content_color, Color.parseColor("#049c8d"));
        attributes.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int h = getHeight();
        int w = getWidth();
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(w, 0);
        path.lineTo(w, h);
        path.lineTo(0, h);
        path.close();
        canvas.drawPath(path, linePaint);

        //draw separate line
        float[] xpos = new float[maxLevel];

        float unitw = w/maxLevel;
        for(int i=0; i<maxLevel; i++) {
            xpos[i] = unitw * i;
        }

        path = new Path();
        path.moveTo(0, 0);
        path.lineTo(xpos[currentLevel], 0);
        path.lineTo(xpos[currentLevel], h);
        path.lineTo(0, h);
        path.close();
        canvas.drawPath(path, contentPaint);

        for(int i=0; i<maxLevel; i++) {
            canvas.drawLine(xpos[i], 0, xpos[i], h, linePaint);
        }
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        this.invalidate();
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        this.invalidate();
    }

    public void setText(String text) {
        this.text = text;
        this.invalidate();
    }

    public void setBorderColor(int color) {
        linePaint.setColor(color);
        this.invalidate();
    }

    public void setContentColor(int color) {
        contentPaint.setColor(color);
        this.invalidate();
    }
}
