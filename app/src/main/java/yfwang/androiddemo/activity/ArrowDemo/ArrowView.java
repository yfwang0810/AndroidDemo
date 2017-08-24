package yfwang.androiddemo.activity.ArrowDemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import yfwang.androiddemo.R;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/23 10:25
 */
public class ArrowView extends View {

    /**
     * 箭头方向
     */
    private static final int LEFT_TO_RIGHT = -1; //从左到右
    private static final int RIGHT_TO_LEFT = 0;  //从右到左 
    private static final int LEFT_TO_RIGHT_DOWN = 1;          // 从左到右下
    private static final int LEFT_TO_RIGHT_UP = 2;          // 从左到右上
    private static final int RIGHT_TO_LEFT_DOWN = 3;          // 从右到左下
    private static final int RIGHT_TO_LEFT_UP = 4;          // 从右到左上

    private Canvas mCanvas;
    private Paint mPaint;
    private double arrowLength;
    private double arrowHelfHeight;
    private int direction;

    public ArrowView(Context context) {
        this(context, null);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ArrowView);
        direction = ta.getInt(R.styleable.ArrowView_gravity, LEFT_TO_RIGHT_DOWN);
        ta.recycle();
        mPaint = new Paint();
        setPaintDefaultStyle();
        setArrow(24, 10);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        this.mCanvas = canvas;
        if (direction == LEFT_TO_RIGHT) {
            drawAL(0, getHeight()/2,getWidth()-(int) arrowHelfHeight,getHeight()/2);
        } else if (direction == RIGHT_TO_LEFT) {
            drawAL(getWidth(), getHeight()/2,(int) arrowHelfHeight, getHeight()/2);
        } else if (direction == LEFT_TO_RIGHT_DOWN) {
            drawAL(0, 0, getWidth() - (int) arrowHelfHeight, getHeight() - (int) arrowHelfHeight);
        } else if (direction == LEFT_TO_RIGHT_UP) {
            drawAL(0, getHeight(), getWidth() - (int) arrowHelfHeight, (int) arrowHelfHeight);
        } else if (direction == RIGHT_TO_LEFT_DOWN) {
            drawAL(getWidth(), getHeight(), (int) arrowHelfHeight, (int) arrowHelfHeight);
        } else if (direction == RIGHT_TO_LEFT_UP) {
            drawAL(getWidth(), 0, (int) arrowHelfHeight, getHeight()-(int) arrowHelfHeight);
        }

    }

    /**
     * 设置画笔默认样式
     */
    public void setPaintDefaultStyle() {
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
    }

    /**
     * 设置箭头大小
     *
     * @param arrowLength 箭头高度
     * @param arrowHelfHeight 底边的一半
     */
    public void setArrow(double arrowLength, double arrowHelfHeight) {
        this.arrowLength = arrowLength;
        this.arrowHelfHeight = arrowHelfHeight;
    }

    /**
     * 画箭头
     *
     * @param sx
     * @param sy
     * @param ex
     * @param ey
     */
    public void drawAL(int sx, int sy, int ex, int ey) {
        int x3 = 0;
        int y3 = 0;
        int x4 = 0;
        int y4 = 0;
        double awrad = Math.atan(arrowHelfHeight / arrowLength); // 箭头角度
        double arrow_len = Math.sqrt(arrowHelfHeight * arrowHelfHeight + arrowLength * arrowLength); // 箭头的长度
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arrow_len);
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arrow_len);
        double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
        double y_3 = ey - arrXY_1[1];
        double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
        double y_4 = ey - arrXY_2[1];
        x3 = (int) x_3;
        y3 = (int) y_3;
        x4 = (int) x_4;
        y4 = (int) y_4;
        // 画线
        mCanvas.drawLine(sx, sy, ex, ey, mPaint);
        Path triangle = new Path();

        triangle.moveTo(ex, ey);
        triangle.lineTo(x3, y3);
        triangle.lineTo(x4, y4);

        triangle.close();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCanvas.drawPath(triangle, mPaint);

    }


    // 计算

    /**
     * @param px      x分量
     * @param py      y分量
     * @param ang     旋转角
     * @param isChLen 是否改变长度
     * @param newLen  新长度
     * @return
     */
    public double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {
        double mathstr[] = new double[2];
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }


}
