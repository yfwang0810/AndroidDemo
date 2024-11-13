package yfwang.androiddemo.activity.ShareElementDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.Window;
import android.widget.ImageView;

import yfwang.androiddemo.R;


/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yafei.wang
 * Date       : 2017/3/8 15:39
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_second);
        ImageView imageView=(ImageView)findViewById(R.id.iv);
        ViewCompat.setTransitionName(imageView, "image");
    }
}
