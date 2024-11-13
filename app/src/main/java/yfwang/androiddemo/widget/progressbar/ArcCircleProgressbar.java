package yfwang.androiddemo.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yfwang.androiddemo.R;
import yfwang.androiddemo.utils.DensityUtils;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/27 15:35
 */
public class ArcCircleProgressbar extends ProgressBar {
    //默认进度条背景颜色
    private final int DEFAULT_BG_COLOR = getResources().getColor(R.color.common_color_c13_ececec);
    // 默认进度条颜色是蓝色
    private final int DEFAULT_PROGRESS_COLOR = getResources().getColor(R.color.common_color_30white);
    // 默认字体颜色
    private final int DEFAULT_TEXT_COLOR = getResources().getColor(R.color.common_color_c1_0971ce);
    // 默认字体大小 26sp
    private final int DEFAULT_TEXT_SIZE = 16;
    private int progressbarBackgroundColor;
    private int textColor;
    private int progressbarColor;

    private Context mContext;

    private final int DEFAULT_STROKE_WIDTH = 18;
    private Paint paint;
    private RectF rectF;
    private RectF rect;
    private Paint mTextPaint;
    private Paint mBackgroundPaint;

    private List<Double> numbers;
    private List<Point> points;
    private double total;


    public ArcCircleProgressbar(Context context) {
        this(context, null);
    }

    public ArcCircleProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcCircleProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressbar);
        progressbarBackgroundColor = typedArray.getColor(R.styleable.CircleProgressbar_progressbarBackgroundColor, DEFAULT_BG_COLOR);
        progressbarColor = typedArray.getColor(R.styleable.CircleProgressbar_progressbarColor, DEFAULT_PROGRESS_COLOR);
        textColor = typedArray.getColor(R.styleable.CircleProgressbar_textColor, DEFAULT_TEXT_COLOR);
        typedArray.recycle();
        initData();

    }

    private void initData() {

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setFlags(Paint.DITHER_FLAG);
        mBackgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mBackgroundPaint.setColor(DEFAULT_PROGRESS_COLOR);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH));

        paint = new Paint();
        paint.setFlags(Paint.DITHER_FLAG);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);//抗锯齿

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(DEFAULT_BG_COLOR);
        paint.setStrokeWidth(DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int radius = 0;
            // 第一象限
            if (x >= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((y - getMeasuredHeight() / 2) * 1.0f
                        / (x - getMeasuredWidth() / 2)) * 180 / Math.PI);
            }
            // 第二象限
            if (x <= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((getMeasuredWidth() / 2 - x)
                        / (y - getMeasuredHeight() / 2))
                        * 180 / Math.PI + 90);
            }
            // 第三象限
            if (x <= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((getMeasuredHeight() / 2 - y)
                        / (getMeasuredWidth() / 2 - x))
                        * 180 / Math.PI + 180);
            }
            // 第四象限
            if (x >= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((x - getMeasuredWidth() / 2)
                        / (getMeasuredHeight() / 2 - y))
                        * 180 / Math.PI + 270);
            }
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                if (point.x <= radius && point.y >= radius) {
                     Toast.makeText(mContext, "点击了" + point,
                     Toast.LENGTH_SHORT)
                     .show();
                    invalidate();
                    return true;
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
    public void setNumbers(List<Double> numbers) {
        this.numbers.clear();
        this.numbers.addAll(numbers);
        points = new ArrayList<Point>();
        for (Double item : numbers) {
            total += item;
            Point point = new Point();
            points.add(point);
        }
        invalidate();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rectF == null) {
            rectF = new RectF(DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH), DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH), getMeasuredWidth() - DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH), getMeasuredHeight() - DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH));
        }

        canvas.drawArc(rectF, -90f, 360f, false, mBackgroundPaint);

        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(rectF, -90, sweepAngle, false, paint);
        
    }
}
