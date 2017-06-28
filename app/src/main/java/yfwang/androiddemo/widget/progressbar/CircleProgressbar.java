package yfwang.androiddemo.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import yfwang.androiddemo.R;
import yfwang.androiddemo.utils.DensityUtils;

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
    private final int DEFAULT_PROGRESS_COLOR = getResources().getColor(R.color.common_color_c1_0971ce);
    // 默认字体颜色
    private final int DEFAULT_TEXT_COLOR = getResources().getColor(R.color.common_color_c1_0971ce);
    // 默认字体大小 26sp
    private final int DEFAULT_TEXT_SIZE = 16;
    private int progressbarBackgroundColor;
    private int textColor;
    private int progressbarColor;
    private Integer textSize;
    private Context mContext;

    private final int DEFAULT_STROKE_WIDTH = 5;
    private Paint paint;
    private RectF rectF;
    private Paint mTextPaint;
    private Paint mBackgroundPaint;

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
        textColor = typedArray.getColor(R.styleable.CircleProgressbar_textColor, DEFAULT_TEXT_COLOR);
        textSize = typedArray.getInteger(R.styleable.CircleProgressbar_textSize, DEFAULT_TEXT_SIZE);
        typedArray.recycle();
        initData();

    }

    private void initData() {
        paint = new Paint();
        paint.setFlags(Paint.DITHER_FLAG);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);//抗锯齿

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(DEFAULT_PROGRESS_COLOR);
        paint.setStrokeWidth(DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH));

        //进度边缘变圆
        paint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mTextPaint.setColor(DEFAULT_TEXT_COLOR);
        mTextPaint.setTextSize(textSize);


        mBackgroundPaint = new Paint();
        mBackgroundPaint.setFlags(Paint.DITHER_FLAG);
        mBackgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);//抗锯齿

        mBackgroundPaint.setStyle(Paint.Style.STROKE);

        mBackgroundPaint.setStrokeWidth(DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH));
    }



    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rectF == null) {
            rectF = new RectF(DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH), DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH), getMeasuredWidth() - DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH), getMeasuredHeight() - DensityUtils.dip2px(mContext, DEFAULT_STROKE_WIDTH));
        }
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(rectF, -90, sweepAngle, false, paint);
//        mBackgroundPaint.setColor(DEFAULT_BG_COLOR);
//        canvas.drawArc(rectF, -90f, 360f, false, mBackgroundPaint);

        int progress = (int) (getProgress() * 1.0f / getMax() * 100);
        String processText = String.valueOf(progress) + "%";
        mTextPaint.setTextSize(DensityUtils.sp2px(mContext, DEFAULT_TEXT_SIZE));
        float textWidth = mTextPaint.measureText(processText);
        canvas.drawText(processText, getMeasuredWidth() / 2-textWidth/2, getMeasuredHeight()/2 , mTextPaint);
    }
}
