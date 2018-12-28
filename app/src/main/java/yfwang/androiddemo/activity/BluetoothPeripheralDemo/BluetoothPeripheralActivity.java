package yfwang.androiddemo.activity.BluetoothPeripheralDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;

import de.greenrobot.event.EventBus;
import yfwang.androiddemo.R;
import yfwang.androiddemo.eventbean.BroadcastEvent;
import yfwang.androiddemo.service.BluetoothBroadcastService;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2018/12/28 9:42
 */
public class BluetoothPeripheralActivity extends Activity {

    private Intent intent;
    private CardView mSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_test);
        intent = new Intent(this, BluetoothBroadcastService.class);
        mSend = (CardView) findViewById(R.id.btn_send);
        startService(intent);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new BroadcastEvent(new byte[]{1,2}));
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (intent!=null){
            stopService(intent);
        }
    }


}
