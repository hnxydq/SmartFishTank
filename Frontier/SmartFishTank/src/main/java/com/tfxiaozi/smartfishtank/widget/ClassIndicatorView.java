package com.tfxiaozi.smartfishtank.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by dongqiang on 2016/10/16.
 */

public class ClassIndicatorView extends View {

    protected int mBorderColor = 0xFFd3d6da;

    protected int mContentColor = 0XFFFC00D1;

    protected int mTextColor = 0xFFFC00D1;

    private Paint linePaint, contentPaint, textPaint;

    public ClassIndicatorView(Context context) {
        this(context, null);
    }

    public ClassIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //obtainStyledAttributes(attrs);
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

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }

}
