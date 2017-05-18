package yfwang.androiddemo.activity.BlueToothDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import yfwang.androiddemo.R;
import yfwang.androiddemo.eventbean.BluetoothStateEvent;
import yfwang.androiddemo.utils.BluetoothConstant;
import yfwang.androiddemo.utils.DataHolder;
import yfwang.androiddemo.utils.PictureUtil;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2016/12/3 15:45
 */

public class BluetoothStateActivity extends AppCompatActivity implements View.OnClickListener {
    private String userID;
    private TextView mConnect;
    private ImageView mImageCode;
    private TextView mChange;
    private TextView mTvBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lanya_connect);
        mConnect = (TextView) findViewById(R.id.tv_lanya_connect);

        mImageCode = (ImageView) findViewById(R.id.lanya_tv_imageCode);

        mChange = (TextView) findViewById(R.id.tv_lanya_change);

        mTvBack = (TextView) findViewById(R.id.lanya_tv_left);

        mChange.setOnClickListener(this);

        mTvBack.setOnClickListener(this);

        //绘制条码页面
        userID = getIntent().getStringExtra("userID");
        PictureUtil.createPic("$$" + userID, mImageCode);

        //获取 蓝牙连接状态，更新页面

        if (BluetoothConstant.BLUETOOTH_CONNECT_SUCCESS_FLAG.equals(DataHolder.get("bluetoothFlag"))
                && (BluetoothConstant.BLUETOOTH_CONNECT_SUCCESS.equals(DataHolder.get("isBluetoothFail")))) {
            mConnect.setText("蓝牙设备连接成功");
            mImageCode.setImageResource(R.drawable.lanyaconnectok);
            mChange.setText("切换蓝牙设备");
            mChange.setVisibility(View.VISIBLE);
        } else {
            mConnect.setText("请扫描条码,连接扫码器");
            PictureUtil.createPic("$$" + userID, mImageCode);
        }

    }

    public void onEvent(BluetoothStateEvent event) {
        if ("connecting".equals(event.getConnectState())) {
            mConnect.setText("正在连接设备");
        } else if ("connected".equals(event.getConnectState())) {
            mConnect.setText("设备连接成功");
        } else if ("default".equals(event.getConnectState())) {
            mConnect.setText("请扫描条形码连接设备");
        }

        if ("visible".equals(event.getChangeState())) {
            mChange.setVisibility(View.VISIBLE);

        } else if ("gone".equals(event.getChangeState())) {
            mChange.setVisibility(View.GONE);
        }
        if ("unConnect".equals(event.getImageCode())) {

            PictureUtil.createPic("$$" + userID, mImageCode);

        } else if ("lanyaconnectok".equals(event.getImageCode())) {
            mImageCode.setImageResource(R.drawable.lanyaconnectok);

        }
        if ("lanyaconnectok".equals(event.getImageCode()) && "connected".equals(event.getConnectState())) {
            this.finish();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_lanya_change:
                BluetoothStateEvent bluetoothStateEvent = new BluetoothStateEvent();
                bluetoothStateEvent.setChangeDevice(true);
                EventBus.getDefault().post(bluetoothStateEvent);
                break;
            case R.id.lanya_tv_left:
                this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
