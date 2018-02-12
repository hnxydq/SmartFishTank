package com.tfxiaozi.smartfishtank.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dongqiang on 2016/10/21.
 */

public class ColorSeekBar extends View {

    private final String TAG="ColorSeekBar";
    private final Paint paint = new Paint();
    private float sLeft, sTop, sRight, sBottom;
    private float sWidth,sHeight;
    private LinearGradient linearGradient;
    private float x,y;
    private float mRadius;
    private float progress;
    protected OnStateChangeListener onStateChangeListener;
    private int startColor= Color.BLACK;
    private int middleColor = Color.GRAY;
    private int endColor=Color.WHITE;
    private int thumbColor=Color.BLACK;
    private int thumbBorderColor=Color.WHITE;
    private int colorArray[]={startColor, middleColor, endColor};


    public ColorSeekBar(Context context) {
        this(context, null);
    }

    public ColorSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setColor(int startColor,int middleColor, int endColor,int thumbColor,int thumbBorderColor){
        this.startColor= startColor;
        this.middleColor = middleColor;
        this.endColor= endColor;
        this.thumbColor= thumbColor;
        this.thumbBorderColor= thumbBorderColor;
        colorArray[0] = startColor;
        colorArray[1] = middleColor;
        colorArray[2] = endColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec*2);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = (float) h/2;
        sLeft = 0; // 背景左的坐标
        sTop = h*0.25f;//top位置
        sRight = w; // 背景的宽的全部
        sBottom = h*0.75f; // 背景底部
        sWidth = sRight - sLeft; // 背景的宽度
        sHeight = sBottom - sTop; // 背景的高度
        x = mRadius;//圆心的x坐标
        y = (float) h/2;//圆心y坐标
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawCircle(canvas);
        paint.reset();
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.x = event.getX();
        progress= x/sWidth*100;
        switch(event.getAction()) {
            case 0://ACTION_DOWN
                Log.i(TAG, "onTouchEvent: x: "+x+" y: "+y +" max : "+event.getSize()+" "+" "+sWidth);
                break;
            case 1://ACTION_UP
                if (onStateChangeListener!=null){
                    onStateChangeListener.onStopTrackingTouch(progress);
                }
                break;
            case 2://ACTION_MOVE

                if (onStateChangeListener!=null){
                    onStateChangeListener.OnStateChangeListener(progress);
                }
                this.invalidate();
                break;
        }
        return true;
    }

    private void drawCircle(Canvas canvas){
        Paint thumbPaint = new Paint();
        x = x < mRadius ? mRadius : x;//判断thumb边界
        x = x > sWidth-mRadius ? sWidth-mRadius : x;
        thumbPaint.setAntiAlias(true);
        thumbPaint.setStyle(Style.FILL);
        thumbPaint.setColor(thumbColor);
        canvas.drawCircle(x, y, mRadius, thumbPaint);
        thumbPaint.setStyle(Style.STROKE);
        thumbPaint.setColor(thumbBorderColor);
        thumbPaint.setStrokeWidth(2);
        canvas.drawCircle(x, y, mRadius, thumbPaint);
    }

    private void drawBackground(Canvas canvas){
        RectF rectBlackBg = new RectF(sLeft, sTop, sRight, sBottom);
        linearGradient=new LinearGradient(sLeft,sTop,sWidth,sHeight,colorArray,null, Shader.TileMode.MIRROR);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        //设置渲染器
        paint.setShader(linearGradient);
        canvas.drawRoundRect(rectBlackBg, sWidth/2, sWidth/2, paint);
    }

    public interface OnStateChangeListener{
        void OnStateChangeListener(float progress);
        void onStopTrackingTouch(float progress);
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener){
        this.onStateChangeListener=onStateChangeListener;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
