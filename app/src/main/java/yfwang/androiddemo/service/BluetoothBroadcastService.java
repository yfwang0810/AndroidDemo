package yfwang.androiddemo.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import de.greenrobot.event.EventBus;
import yfwang.androiddemo.eventbean.BroadcastEvent;

/**
 * Description: 蓝牙打印机服务
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/21 11:23
 */
public class BluetoothBroadcastService extends Service {
    private static final String TAG = "yfwang";
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private int timeoutMillis = 3000;
    private boolean connectAble = false;

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
        //检查是否支持BLE4.0
        if (!isSupportBle()) {
            stopSelf();
        }
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        assert bluetoothManager != null;
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothAdapter.enable();
        mBluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
        if (mBluetoothLeAdvertiser == null) {
            Toast.makeText(this, "the device not support peripheral", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "the device not support peripheral");
            stopSelf();
        }

    }


    public void onEvent(final Object event) {
        if (event instanceof BroadcastEvent){
            add(((BroadcastEvent) event).getBytes());
        }
    }
    /**
     * 广播的一些基本设置
     **/
    private AdvertiseSettings createAdvSettings(boolean connectAble, int timeoutMillis) {
        AdvertiseSettings.Builder builder = new AdvertiseSettings.Builder();
        //设置广播的模式, 功耗相关
        builder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
        builder.setConnectable(connectAble);
        builder.setTimeout(timeoutMillis);
        builder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
        return builder.build();
    }

    /**
     * 开始广播
     */
       public void startAdvertise(boolean connectAble, int timeoutMillis, byte[] manufacturerData) {
        Log.e(TAG, "startAdvertising");
        if (mBluetoothLeAdvertiser != null) {
            AdvertiseData advData = createAdvertiseData(manufacturerData);
            mBluetoothLeAdvertiser.startAdvertising(createAdvSettings(connectAble, timeoutMillis), advData, mAdvertiseCallback);
//            stopAdvertise();
        }
//        handler.sendEmptyMessageDelayed(0, timeoutMillis);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            stopAdvertise();
            return false;
        }
    });

    public void stopAdvertise() {
        Log.e(TAG, "----------stopAdvertise-----------");
        if (mBluetoothLeAdvertiser != null) {
            mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
        }
    }

    /**
     * 广播数据
     *
     * @return AdvertiseData
     */
    private AdvertiseData createAdvertiseData(byte[] manufacturerData) {
        AdvertiseData.Builder mDataBuilder = new AdvertiseData.Builder();
        int manufacturerId = 0x1413;
        byte[] data = new byte[manufacturerData.length + 5];
        System.arraycopy(manufacturerData, 0, data, 1, manufacturerData.length);
        mDataBuilder.addManufacturerData(manufacturerId, data);
        AdvertiseData mAdvertiseData = mDataBuilder.build();
      /*  if (mAdvertiseData == null) {
            Log.e(TAG, "mAdvertiseSettings == null");
        }*/
        Log.e(TAG, "AdvData====" + Arrays.toString(data));
        return mAdvertiseData;
    }

    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
           /* if (settingsInEffect != null) {
                Log.d(TAG, "onStartSuccess TxPowerLv=" + settingsInEffect.getTxPowerLevel() + " mode=" + settingsInEffect.getMode()
                        + " timeout=" + settingsInEffect.getTimeout());
            } else {
                Log.e(TAG, "onStartSuccess, settingInEffect is null");
            }
            Log.e(TAG, "onStartSuccess settingsInEffect" + settingsInEffect);*/
        }

        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            stopAdvertise();
            Log.e(TAG, "onStartFailure errorCode" + errorCode);
            if (errorCode == ADVERTISE_FAILED_DATA_TOO_LARGE) {
                Log.e(TAG, "Failed to start advertising as the advertise data to be broadcasted is larger than 31 bytes.");
            } else if (errorCode == ADVERTISE_FAILED_TOO_MANY_ADVERTISERS) {
                Log.e(TAG, "Failed to start advertising because no advertising instance is available.");
            } else if (errorCode == ADVERTISE_FAILED_ALREADY_STARTED) {
                Log.e(TAG, "Failed to start advertising as the advertising is already started");
            } else if (errorCode == ADVERTISE_FAILED_INTERNAL_ERROR) {
                Log.e(TAG, "Operation failed due to an internal error");
            } else if (errorCode == ADVERTISE_FAILED_FEATURE_UNSUPPORTED) {
                Log.e(TAG, "This feature is not supported on this platform");
            }
        }
    };

    /**
     * 判断是否支持ble
     */
    private boolean isSupportBle() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * broadcastData  queue
     */
    private ArrayList<byte[]> mQueue;

    public synchronized void add(byte[] bytes) {
        if (null == mQueue) {
            mQueue = new ArrayList<>();
        }
        if (null != bytes) {
            mQueue.add(bytes);
        }
        broadcast();
    }

    /**
     * broadcast queue
     */
    public synchronized void broadcast() {
        try {
            if (null == mQueue || mQueue.size() <= 0) {
                return;
            }
            while (mQueue.size() > 0) {
                startAdvertise(connectAble,timeoutMillis,mQueue.get(0));
                mQueue.remove(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAdvertise();
        EventBus.getDefault().unregister(this);
    }

}
