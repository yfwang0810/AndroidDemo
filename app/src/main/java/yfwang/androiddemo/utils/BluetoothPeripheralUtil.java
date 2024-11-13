package yfwang.androiddemo.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import yfwang.androiddemo.global.MobileApplication;

import static android.content.Context.BLUETOOTH_SERVICE;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2018/12/28 10:12
 */
public class BluetoothPeripheralUtil {
    private static final String TAG = "BluetoothPeripheralUtil";

    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;

    private int timeoutMillis = 3000;
    private boolean connectAble = false;
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothPeripheralUtil(Context context) {
        mContext = context;
        init();
    }

    public static BluetoothPeripheralUtil getInstance() {
        return AdvUtilsInstance.INSTANCE;
    }

    private static class AdvUtilsInstance {
        private static final BluetoothPeripheralUtil INSTANCE = new BluetoothPeripheralUtil(MobileApplication.getInstance());
    }

    private void init() {
        BluetoothManager mBluetoothManager = (BluetoothManager) mContext.getSystemService(BLUETOOTH_SERVICE);
        assert mBluetoothManager != null;
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
            mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
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
        AdvertiseSettings mAdvertiseSettings = builder.build();
        if (mAdvertiseSettings == null) {
            Log.e("main", "mAdvertiseSettings == null");
        }
        return mAdvertiseSettings;
    }

    /**
     * 开始广播
     */
    public void startAdvertise(byte[] manufacturerData) {
        stopAdvertise();
        startAdvertise(connectAble, timeoutMillis, manufacturerData);
    }

    public void startAdvertise(boolean connectAble, int timeoutMillis, byte[] manufacturerData) {
        Log.e(TAG, "startAdvertising");
        if (mBluetoothLeAdvertiser != null) {
            AdvertiseData advData = createAdvertiseData(manufacturerData);
            mBluetoothLeAdvertiser.startAdvertising(createAdvSettings(connectAble, timeoutMillis), advData, mAdvertiseCallback);
        }
        handler.sendEmptyMessageDelayed(0, timeoutMillis);
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
        SystemClock.sleep(200);
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
        if (mAdvertiseData == null) {
            Log.e(TAG, "mAdvertiseSettings == null");
        }
        Log.e(TAG, "AdvData====" + readBufferMsg(data));
        return mAdvertiseData;
    }

    public static String readBufferMsg(byte[] bytes) {
        StringBuffer msg = new StringBuffer();
        for (int i = 6; i < bytes.length; i++) {
            if (bytes[i] == 13) {
                // 碰到回车,条形码解析完毕
                // 碰到回车,条形码解析完毕
                break;
            } else {
                // 当没有碰到回车的时候,一直累加
                msg.append((char) bytes[i]);
            }
        }
        return msg.toString();
    }

    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            if (settingsInEffect != null) {
                Log.d(TAG, "onStartSuccess TxPowerLv=" + settingsInEffect.getTxPowerLevel() + " mode=" + settingsInEffect.getMode()
                        + " timeout=" + settingsInEffect.getTimeout());
            } else {
                Log.e(TAG, "onStartSuccess, settingInEffect is null");
            }
            Log.e(TAG, "onStartSuccess settingsInEffect" + settingsInEffect);
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

}
