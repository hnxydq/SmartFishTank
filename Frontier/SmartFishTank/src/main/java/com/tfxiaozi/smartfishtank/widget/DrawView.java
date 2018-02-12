package com.tfxiaozi.smartfishtank.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dongqiang on 2016/10/6.
 */

public class DrawView extends View {

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);

        canvas.drawText("画圆", 20, 20, paint);
        canvas.drawCircle(30, 30, 5, paint);
        canvas.drawCircle(60, 60, 8, paint);

        paint.setColor(Color.RED);
        canvas.drawLine(70, 0, 80, 80, paint);

        /*paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(5, 120, 100, 250, paint);*/

        /*canvas.drawText("画扇形和椭圆:", 10, 150, paint);
        *//* 设置渐变色 这个正方形的颜色是改变的 *//*
        Shader shader = new LinearGradient(0, 0, 100, 100, new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                Color.LTGRAY}, null, Shader.TileMode.REPEAT);
        paint.setShader(shader);
        RectF oval = new RectF(130, 100, 200, 240);
        canvas.drawArc(oval, 200, 130, true, paint);*/

        canvas.drawText("画三角形", 250, 0, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        Path path = new Path();
        path.moveTo(100, 400);
        path.lineTo(400, 400);
        path.lineTo(400, 300);
        path.close();
        canvas.drawPath(path, paint);
        canvas.drawLine(150, 400, 150, 383.3f, paint);
        canvas.drawLine(200, 400, 200, 366.6f, paint);
        canvas.drawLine(250, 400, 250, 350f, paint);
        canvas.drawLine(300, 400, 300, 333.3f, paint);
        canvas.drawLine(350, 400, 350, 316.6f, paint);
        path = new Path();
        path.moveTo(100, 400);
        path.lineTo(200, 400);
        path.lineTo(200, 367f);
        path.close();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);

    }
}
