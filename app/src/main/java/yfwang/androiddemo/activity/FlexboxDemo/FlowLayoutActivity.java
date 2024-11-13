package yfwang.androiddemo.activity.FlexboxDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.flexbox.FlexboxLayout;

import yfwang.androiddemo.R;

/**
 * Description: 流式布局
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/10/9 11:04
 */
public class FlowLayoutActivity extends Activity {

    private FlexboxLayout mLaber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);
        mLaber = (FlexboxLayout) findViewById(R.id.fl_laber);

    }
}
