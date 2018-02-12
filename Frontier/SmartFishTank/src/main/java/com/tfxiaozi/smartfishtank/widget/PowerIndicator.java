package com.tfxiaozi.smartfishtank.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.tfxiaozi.smartfishtank.R;

/**
 * Created by dongqiang on 2016/10/6.
 */

public class PowerIndicator extends View {

    private final String TAG = PowerIndicator.class.getSimpleName();

    protected int mBorderColor = 0xFFd3d6da;

    protected int mContentColor = 0XFFFC00D1;

    protected int mTextColor = 0xFFFC00D1;

    private Paint linePaint, contentPaint, textPaint;

    private int maxLevel = 6;

    private int currentLevel = 0;

    private String text;

    public PowerIndicator(Context context) {
        this(context, null);
    }

    public PowerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttributes(attrs);
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
        TypedArray attributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.PowerIndicator);
        mBorderColor = attributes.getColor(R.styleable.PowerIndicator_indicator_border_color, Color.parseColor("#dedede"));
        mContentColor = attributes.getColor(R.styleable.PowerIndicator_indicator_content_color, Color.parseColor("#049c8d"));
        mTextColor = attributes.getColor(R.styleable.PowerIndicator_indicator_text_color, Color.parseColor("#049c8d"));
        attributes.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int h = getHeight();
        int w = getWidth();

        Path path = new Path();
        path.moveTo(0, h-50);
        path.lineTo(w, 0);
        path.lineTo(w, h-50);
        path.close();
        canvas.drawPath(path, linePaint);

        //draw separate line
        float[] xpos = new float[maxLevel];
        float[] ypos = new float[maxLevel];
        float unitw = w/maxLevel;
        float tan = (float) (h-50)/w;

        for(int i=0; i<maxLevel; i++) {
            xpos[i] = unitw * i;
            ypos[i] = tan * xpos[i];
        }

        path = new Path();
        path.moveTo(0, h-50);
        path.lineTo(xpos[currentLevel], h-50);
        path.lineTo(xpos[currentLevel], h-50-ypos[currentLevel]);
        path.close();
        canvas.drawPath(path, contentPaint);

        for(int i=0; i<maxLevel; i++) {
            Log.d(TAG, "x1=" + xpos[i] + ", y1=" + h + ", x2=" + xpos[i] + ", y2=" + (h - ypos[i]));
            canvas.drawLine(xpos[i], h-50, xpos[i], h -50 - ypos[i], linePaint);
        }

        canvas.drawText("功率: " + text + "w", w/2 - 20, h-25, textPaint);
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
}
