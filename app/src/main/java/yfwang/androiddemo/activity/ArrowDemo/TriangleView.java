package yfwang.androiddemo.activity.ArrowDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/23 16:39
 */
public class TriangleView extends RelativeLayout {

    public TriangleView(Context context) {
        this(context,null);
    }

    public TriangleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
