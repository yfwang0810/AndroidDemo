package yfwang.androiddemo.activity.TimePickerDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import yfwang.androiddemo.R;
import yfwang.androiddemo.adapter.timepicker.GalleryAdapter;
import yfwang.androiddemo.utils.ToastUtil;

/**
 * Description: 选择时间段控件
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/5/22 10:15
 */
public class TimePickerActivity extends Activity {

    public Button mConfirm;
    public TextView mTime;
    public Gallery mGallery;
    private String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private List<String> mData;
    LayoutInflater mInflater = null;
    private GalleryAdapter adapter;
    int selectPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepicker);
        mConfirm = (Button) findViewById(R.id.btn_confirm);
        mTime = (TextView) findViewById(R.id.tv_time);
        mGallery = (Gallery) findViewById(R.id.gallery);


        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(this);
        mData = new ArrayList<>();
        Collections.addAll(mData, data);
        adapter = new GalleryAdapter(mInflater, this);
        selectPosition = 2;
        adapter.setData(selectPosition, mData);
        mGallery.setAdapter(adapter);
        mGallery.setSelection(2);
        mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectPosition = position;
                adapter.setData(selectPosition, mData);
                if (selectPosition == 0) {
                    mTime.setText(data[selectPosition]);
                } else {
                    mTime.setText("≥" + data[selectPosition] + "小时");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mConfirm = (Button) findViewById(R.id.btn_confirm);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(TimePickerActivity.this, mData.get(selectPosition));
            }
        });
    }
}
