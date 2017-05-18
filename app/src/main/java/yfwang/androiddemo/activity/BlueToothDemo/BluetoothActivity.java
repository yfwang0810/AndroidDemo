package yfwang.androiddemo.activity.BlueToothDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import yfwang.androiddemo.R;
import yfwang.androiddemo.eventbean.MSGServiceEvent;
import yfwang.androiddemo.service.BluetoothService;
import yfwang.androiddemo.utils.Common;

public class BluetoothActivity extends AppCompatActivity {

    private TextView msg;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bluetooth);
        boolean blueThoothState = Common.getInstance().getBluetoothState();
        if (!blueThoothState) {
            intent = new Intent(this, BluetoothService.class);
            startService(intent);
        }
        msg = (TextView) findViewById(R.id.msg);
        EventBus.getDefault().register(this);
    }

    public void onEvent(MSGServiceEvent event) {
        msg.setText("扫描数据:"+event.getMsg());
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
