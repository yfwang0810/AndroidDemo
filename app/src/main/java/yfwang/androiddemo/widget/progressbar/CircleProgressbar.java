package yfwang.androiddemo.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yfwang.androiddemo.R;
import yfwang.androiddemo.utils.DensityUtils;
import yfwang.androiddemo.widget.popupwindow.EasyPopupWindow;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/27 15:35
 */
public class CircleProgressbar extends ProgressBar {
    //默认进度条背景颜色
    private final int DEFAULT_BG_COLOR = getResources().getColor(R.color.common_color_c13_ececec);
    // 默认进度条颜色是蓝色
    private final int DEFAULT_PROGRESS_COLOR = getResources().getColor(R.color.common_color_30white);

    private Context mContext;

    private final int DEFAULT_STROKE_WIDTH = 18;
    private Paint paint;
    private RectF rectF;
    private int progressbarBackgroundColor;
    private int progressbarColor;
    private boolean isClick;

    private Paint mBackgroundPaint;


    private List<Point> points;
    private int strokeWidth;
    private RectF bound1;
    private RectF bound2;
    private Path path1;
    private Path path2;
    private float left;
    private float top;
    private float right;
    private float bottom;


    public CircleProgressbar(Context context) {
        this(context, null);
    }

    public CircleProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressbar);
        progressbarBackgroundColor = typedArray.getColor(R.styleable.CircleProgressbar_progressbarBackgroundColor, DEFAULT_BG_COLOR);
        progressbarColor = typedArray.getColor(R.styleable.CircleProgressbar_progressbarColor, DEFAULT_PROGRESS_COLOR);
        isClick = typedArray.getBoolean(R.styleable.CircleProgressbar_isClick, false);
        strokeWidth = typedArray.getInteger(R.styleable.CircleProgressbar_strokeWidth, DEFAULT_STROKE_WIDTH);

        typedArray.recycle();
        initData();

    }

    private void initData() {

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setFlags(Paint.DITHER_FLAG);
        mBackgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mBackgroundPaint.setColor(progressbarColor);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(DensityUtils.dip2px(mContext, strokeWidth));

        paint = new Paint();
        paint.setFlags(Paint.DITHER_FLAG);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);//抗锯齿

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(progressbarBackgroundColor);
        paint.setStrokeWidth(DensityUtils.dip2px(mContext, strokeWidth));

        points = new ArrayList<>();

        Point point1 = new Point();
        Point point2 = new Point();

        bound1 = new RectF();
        bound2 = new RectF();
        path1 = new Path();
        path2 = new Path();


        points.add(point1);
        points.add(point2);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float width = (float) w;
        float height = (float) h;
        left = width / 4f;
        top = width / 4f;
        right = width - left;
        bottom = width - top;
    }

    private String mArrowTo = "";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int radius = 0;
            // 第一象限
            if (x >= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((x - getMeasuredWidth() / 2)
                        / (getMeasuredHeight() / 2 - y))
                        * 180 / Math.PI - 90);
                mArrowTo = "left";
            }
            // 第二象限
            if (x >= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((y - getMeasuredHeight() / 2) * 1.0f
                        / (x - getMeasuredWidth() / 2)) * 180 / Math.PI);
                mArrowTo = "left";
            }
            // 第三象限
            if (x <= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((getMeasuredWidth() / 2 - x)
                        / (y - getMeasuredHeight() / 2))
                        * 180 / Math.PI + 90);
                mArrowTo = "right";
            }
            // 第四象限
            if (x <= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((getMeasuredHeight() / 2 - y)
                        / (getMeasuredWidth() / 2 - x))
                        * 180 / Math.PI + 180);
                mArrowTo = "right";
            }
            if (isClick) {
                for (int i = 0; i < points.size(); i++) {
                    Point point = points.get(i);
                    if (point.x <= radius && point.y >= radius) {
                        Toast.makeText(mContext, "点击了" + point,
                                Toast.LENGTH_SHORT)
                                .show();
                        int[] location = new int[2];
                        CircleProgressbar.this.getLocationInWindow(location);


                        if ("left".equals(mArrowTo)) {
                            View bubbleView = View.inflate(mContext, R.layout.view_bubble_pop_left, null);
                            TextView tvContent = (TextView) bubbleView.findViewById(R.id.tv_content);
                            tvContent.setText("left");
                            EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(mContext)
                                    .setView(bubbleView)
                                    .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .setOutsideTouchHide(true)
                                    .create();

                            //将path当前的坐标赋值给bounds
                            path1.computeBounds(bound1, true);


                            easyPopupWindow.showAtLocation(CircleProgressbar.this, Gravity.LEFT, (int) bound1.left + location[0]+ CircleProgressbar.this.getWidth(),  location[1]+ CircleProgressbar.this.getWidth()+(int) bound1.top);
//                          PopUpUtils.showBubbleLeft(CircleProgressbar.this, mContext, "left");
                        } else if ("right".equals(mArrowTo)) {
                            View bubbleView = View.inflate(mContext, R.layout.view_bubble_pop_right, null);
                            TextView tvContent = (TextView) bubbleView.findViewById(R.id.tv_content);
                            tvContent.setText("right");
                            EasyPopupWindow easyPopupWindow = new EasyPopupWindow.Builder(mContext)
                                    .setView(bubbleView)
                                    .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .setOutsideTouchHide(true)
                                    .create();
                            path2.computeBounds(bound2, true);
                            easyPopupWindow.showAtLocation(CircleProgressbar.this, Gravity.RIGHT, (int) bound2.left+ location[0]+ CircleProgressbar.this.getWidth(), location[1]+ CircleProgressbar.this.getWidth()+(int) bound2.top);
//                          PopUpUtils.showBubbleRight(CircleProgressbar.this, mContext, "right");
                        }
                        return true;
                    }
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rectF == null) {
            rectF = new RectF(DensityUtils.dip2px(mContext, strokeWidth), DensityUtils.dip2px(mContext, strokeWidth), getMeasuredWidth() - DensityUtils.dip2px(mContext, strokeWidth), getMeasuredHeight() - DensityUtils.dip2px(mContext, strokeWidth));
        }
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(rectF, -90, sweepAngle, false, paint);
        path1.arcTo(left, top, right, bottom, -90 + sweepAngle / 2, 0f, false);
        points.get(0).x = (int) -90f;
        points.get(0).y = (int) (-90f + sweepAngle);
        canvas.drawArc(rectF, sweepAngle - 90, (100 - getProgress()) * 1.0f / getMax() * 360, false, mBackgroundPaint);
        path2.addArc(rectF, sweepAngle - 90, (100 - getProgress()) * 1.0f / getMax() * 360);
        path1.arcTo(left, top, right, bottom, sweepAngle - 90 + (100 - getProgress()) * 1.0f / getMax() * 360 / 2, 0f, false);
        points.get(1).x = (int) sweepAngle - 90;
        points.get(1).y = (int) (sweepAngle - 90 + (100 - getProgress()) * 1.0f / getMax() * 360);
    }
}
