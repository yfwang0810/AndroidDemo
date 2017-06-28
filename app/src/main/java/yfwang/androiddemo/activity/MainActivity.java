package yfwang.androiddemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yfwang.androiddemo.R;
import yfwang.androiddemo.activity.BezierDemo.ShoppingCartBezierActivity;
import yfwang.androiddemo.activity.BlueToothDemo.BluetoothActivity;
import yfwang.androiddemo.activity.DraglayoutDemo.DraglayoutActivity;
import yfwang.androiddemo.activity.KeyboardDemo.KeyboardActivity;
import yfwang.androiddemo.activity.MapDemo.MapActivity;
import yfwang.androiddemo.activity.MaterialDesignDemo.DrawerLayoutActivity;
import yfwang.androiddemo.activity.PayKeyboardDemo.PayKeyboardActivity;
import yfwang.androiddemo.activity.ProgressbarDemo.ProgressbarActivity;
import yfwang.androiddemo.activity.ShareElementDemo.FirstActivity;
import yfwang.androiddemo.activity.TimePickerDemo.TimePickerActivity;
import yfwang.androiddemo.adapter.ListAdapter;
import yfwang.androiddemo.bean.DemoInfo;
import yfwang.androiddemo.mvp.view.LoginActivity;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;
    //demo 列表
    private static final DemoInfo[] DEMOS = {
            new DemoInfo("Design Demo", "MaterialDesign特效展示", DrawerLayoutActivity.class),
            new DemoInfo("ShareElement Demo", "共享元素效果展示", FirstActivity.class),
            new DemoInfo("Draglayout Demo", "可拽动的Item效果展示", DraglayoutActivity.class),
            new DemoInfo(" Bluetooth Demo", "蓝牙4.0,扫描条码(定制版)", BluetoothActivity.class),
            new DemoInfo(" Bezier Demo", "购物车贝塞尔曲线效果展示", ShoppingCartBezierActivity.class),
            new DemoInfo(" Gallery Demo", "选择时间段(类似照片墙效果)", TimePickerActivity.class),
            new DemoInfo(" MVP Demo", "MVP模式", LoginActivity.class),
            new DemoInfo("Keyboard Demo", "自定义键盘效果展示(原生API)", KeyboardActivity.class),
            new DemoInfo("Keyboard Demo", "自定义键盘效果展示(GridView实现)", PayKeyboardActivity.class),
            new DemoInfo("Map Demo", "高德地图定位(需手动开启定位权限)", MapActivity.class),
            new DemoInfo("CircleProgressbar Demo", "自定义progressbar", ProgressbarActivity.class),

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        List<DemoInfo> list = new ArrayList<>();
        Collections.addAll(list, DEMOS);
        ListAdapter adapter = new ListAdapter(list);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

    }


}
