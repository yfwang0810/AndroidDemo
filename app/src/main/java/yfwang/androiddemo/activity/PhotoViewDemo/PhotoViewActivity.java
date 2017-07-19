package yfwang.androiddemo.activity.PhotoViewDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import yfwang.androiddemo.R;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/28 16:08
 */
public class PhotoViewActivity extends Activity implements View.OnClickListener {

    private TextView mSinglePic;
    private TextView mListPic;
    private TextView mEditPic;
    private TextView mUploadPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        initView();
    }

    private void initView() {

        mSinglePic = (TextView) findViewById(R.id.tv_singlepic);
        mListPic = (TextView) findViewById(R.id.tv_listpic);

        mSinglePic.setOnClickListener(this);
        mListPic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_singlepic:
                startActivity(new Intent(PhotoViewActivity.this, SinglePicActivity.class));

                break;
            case R.id.tv_listpic:
                startActivity(new Intent(PhotoViewActivity.this, ListPicActivity.class));
                break;

        }


    }
}
