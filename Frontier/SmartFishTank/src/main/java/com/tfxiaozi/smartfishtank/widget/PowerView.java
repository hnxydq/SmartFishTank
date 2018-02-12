package com.tfxiaozi.smartfishtank.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hao on 16/10/6.
 */
public class PowerView extends View{

    private final String TAG = "PowerView";
    private Paint linePaint ;
    private Paint fillPaint ;
    private int maxLevel = 5;
    private final int STROKE_WIDTH = 5 ;
    private  int currentLevel = 0 ;

    public PowerView(Context context) {
        super(context);
        init();
    }

    public PowerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PowerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);

        linePaint.setStrokeWidth(STROKE_WIDTH);

        fillPaint = new Paint();
        fillPaint.setColor(Color.BLUE);
        fillPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float h =  getHeight();
        float w = getWidth();


        float[] xs = new float[maxLevel];
        float[] ys = new float[maxLevel];
        float unitW = w/ maxLevel;
        float tan = h/w ;
        for(int i=0;i< maxLevel;i++){
            float x1 = unitW*i;
            float y1 = x1*tan;
            xs[i] = x1;
            ys[i] = y1;

        }

        Path fillTrianglePath = new Path();
        fillTrianglePath.moveTo(0,h);
        fillTrianglePath.lineTo(xs[currentLevel], h - ys[currentLevel]);
        fillTrianglePath.lineTo(xs[currentLevel], h);
        fillTrianglePath.close();
        canvas.drawPath(fillTrianglePath, fillPaint);

        for(int i=0;i< maxLevel;i++){
            canvas.drawLine(xs[i], h - ys[i],xs[i], h,linePaint);
        }


        Path trianglePath = new Path();
        trianglePath.moveTo(0, h - STROKE_WIDTH);
        trianglePath.lineTo(w - STROKE_WIDTH, 0 + STROKE_WIDTH);
        trianglePath.lineTo(w-STROKE_WIDTH, h-STROKE_WIDTH);
        trianglePath.lineTo(0 + STROKE_WIDTH, h - STROKE_WIDTH);
        trianglePath.close();
        canvas.drawPath(trianglePath,linePaint);


        super.onDraw(canvas);
    }

    public void  setCurrentLevel(int level){
        if(level<0 || level>maxLevel){
            return;
        }
        currentLevel = level ;
        invalidate();
    }


}
