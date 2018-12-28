package yfwang.androiddemo.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.widget.Toast;

import java.util.List;

import de.greenrobot.event.EventBus;
import yfwang.androiddemo.activity.BlueToothDemo.BluetoothStateActivity;
import yfwang.androiddemo.eventbean.BluetoothStateEvent;
import yfwang.androiddemo.eventbean.MSGServiceEvent;
import yfwang.androiddemo.eventbean.StopServiceEvent;
import yfwang.androiddemo.scanner.BluetoothLeScannerCompat;
import yfwang.androiddemo.scanner.ScanCallback;
import yfwang.androiddemo.scanner.ScanRecord;
import yfwang.androiddemo.scanner.ScanResult;
import yfwang.androiddemo.utils.BluetoothConstant;
import yfwang.androiddemo.utils.BluetoothUtil;
import yfwang.androiddemo.utils.Common;
import yfwang.androiddemo.utils.DataHolder;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2016/12/2 19:51
 */

public class BluetoothService extends Service {
    private BluetoothUtil bluetoothUtil;
    private BluetoothLeScannerCompat scanner;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private boolean isConnect = false;
    private static final long SCAN_PERIOD = 3000;
    private BluetoothDevice scan_code_device;
    private String device_address;
    private String broadData;
    private BluetoothGattService currBtService;
    public static BluetoothGattCharacteristic writeBtCharacteristic;
    private BluetoothGattCharacteristic currBtCharacteristic;
    private String userID;

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
        EventBus.getDefault().register(this);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    public void onEvent(BluetoothStateEvent event) {
        if (event.getChangeDevice())
            if (BluetoothConstant.BLUETOOTH_CONNECT_SUCCESS.equals(DataHolder.get("isBluetoothFail"))) {
                if (BluetoothConstant.BLUETOOTH_CONNECT_SUCCESS.equals(DataHolder.get("isBluetoothFail"))) {
                    Message msg = Message.obtain();
                    msg.what = BluetoothConstant.MSG_CHANGE_DEVICE;
                    handler.sendMessage(msg);
                }
            }
    }

    public void onEvent(StopServiceEvent event) {
        if (event.getMsg()) {
            if (BluetoothConstant.BLUETOOTH_CONNECT_SUCCESS.equals(DataHolder.get("isBluetoothFail"))) {
                if (BluetoothConstant.BLUETOOTH_CONNECT_SUCCESS.equals(DataHolder.get("isBluetoothFail"))) {
                    Message msg = Message.obtain();
                    msg.what = BluetoothConstant.MSG_DISCONNECT_DEVICE;
                    handler.sendMessage(msg);
                }
            }
            Common.getInstance().setBluetoothState(false);
            stopSelf();
        }
    }

    private void initData() {
        bluetoothUtil = new BluetoothUtil(this);
        if (Build.VERSION.SDK_INT >= 21) {
            scanner = BluetoothLeScannerCompat.getScanner();
        }
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

//        userID = sp.getValue(Constant.sp_UserID);
        userID = "188008";


    }


    public void review() {

        //检查是否支持BLE4.0
        if (!judgeBuletooh()) {
            Toast.makeText(this, "您的设备不支持ble4.0", Toast.LENGTH_SHORT).show();
            //activity finish
        }
        if (Build.VERSION.SDK_INT < 21) {
            //检查是否支持蓝牙
            if (bluetoothAdapter == null) {
                Toast.makeText(this, "您的设备不支持bluetooth", Toast.LENGTH_SHORT).show();
                //activity finish
                return;
            }
        }
        //开启蓝牙
        bluetoothAdapter.enable();
        if (bluetoothUtil == null) {
            bluetoothUtil = new BluetoothUtil(this);
        }
        if (!bluetoothUtil.initialize()) {
            Toast.makeText(this, "初始化MyBluetoothLe失败", Toast.LENGTH_SHORT).show();
            //activity finish
        }
        scanDevice(true);
        //发现BLE终端回调
        bluetoothUtil.setOnServiceDiscoverListener(onServiceDiscove);
        //收到BLE数据交互回调
        bluetoothUtil.setOnDataAvailableListener(onDataAvailable);
        //连接BLE设备回调
        bluetoothUtil.setOnConnectListener(onConnect);
        //断开BLE设备回调
        bluetoothUtil.setOnDisconnectListener(onDisconnect);
        //正在连接BLE设备回调
        bluetoothUtil.setOnConnectingListener(onConnecting);
        //设置写入描述信息
        bluetoothUtil.setDescriptorWrite(onDescriptorWrite);
        if (!Common.getInstance().getBluetoothState()) {
            Intent intent = new Intent();
            intent.setClass(this, BluetoothStateActivity.class);
            intent.putExtra("userID", userID);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    /**
     * 设备是否支持BLE4.0
     *
     * @time 2016/6/17 0017 10:59
     */
    public boolean judgeBuletooh() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否扫描设备
     *
     * @time 2016/6/17 0017 11:03
     */
    public void scanDevice(boolean enable) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (enable) {
                if (isConnect) {
                    scanner.stopScan(scanCallback);
                } else {
                    handler.postDelayed(runnable, SCAN_PERIOD);
                }
            } else {
                scanner.stopScan(scanCallback);
            }
        } else {
            if (enable) {
                if (isConnect) {
                    bluetoothAdapter.stopLeScan(leScanCallback);
                } else {
                    handler.postDelayed(runnable, SCAN_PERIOD);
                }
            } else {
                bluetoothAdapter.stopLeScan(leScanCallback);
            }
        }
    }

    private BluetoothUtil.OnServiceDiscoverListener onServiceDiscove = new BluetoothUtil.OnServiceDiscoverListener() {

        @Override
        public void onServiceDiscover(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                currBtService = gatt.getService(BluetoothConstant.Service_UUID);
                if (currBtService == null) {
                    return;
                }
                writeBtCharacteristic = currBtService.getCharacteristic(BluetoothConstant.WRITE_UUID);
                if (writeBtCharacteristic == null) {
                    return;
                }
                currBtCharacteristic = currBtService.getCharacteristic(BluetoothConstant.Chara_UUID);
                if (currBtCharacteristic == null) {
                    return;
                }
                bluetoothUtil.setCharacteristicNotification(currBtCharacteristic, true);
            }
        }
    };
    private BluetoothUtil.OnDescriptorWriteListener onDescriptorWrite = new BluetoothUtil.OnDescriptorWriteListener() {
        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                bluetoothUtil.setWriteCharacteristicNotification("0101", writeBtCharacteristic);
            } else if (BluetoothConstant.BLUETOOTH_IS_AUTO_CONNECT.equals(DataHolder.get("isAutoConnect"))) {
                bluetoothUtil.setWriteCharacteristicNotification("0102", writeBtCharacteristic);
            }
        }
    };

    private BluetoothUtil.OnConnectListener onConnect = new BluetoothUtil.OnConnectListener() {
        @Override
        public void onConnect(BluetoothGatt gatt) {
            Message msg = Message.obtain();
            msg.what = BluetoothConstant.MSG_CONNECT_OK;
            handler.sendMessage(msg);
            gatt.discoverServices();
        }
    };

    private BluetoothUtil.OnDisconnectListener onDisconnect = new BluetoothUtil.OnDisconnectListener() {
        @Override
        public void onDisconnect(BluetoothGatt gatt) {
            Message msg = Message.obtain();
            msg.what = BluetoothConstant.MSG_CONNECT_FAIL;
            handler.sendMessage(msg);
        }
    };

    private BluetoothUtil.OnConnectingListener onConnecting = new BluetoothUtil.OnConnectingListener() {
        @Override
        public void onConnecting(BluetoothGatt gatt) {
            Message msg = Message.obtain();
            msg.what = BluetoothConstant.MSG_CONNECTING;
            handler.sendMessage(msg);
        }
    };

    private BluetoothUtil.OnDataAvailableListener onDataAvailable = new BluetoothUtil.OnDataAvailableListener() {
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if ("1".equals(DataHolder.get("isAutoConnect"))) {
                scan_code_device = null;
               /* if (bluetoothAdapter != null)
                    bluetoothAdapter.disable();*/
                DataHolder.put("isAutoConnect", "0");
                DataHolder.put("bluetoothFlag", "0");
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] buffer = new byte[1024]; // buffer store for the stream
            int bytes; // bytes returned from read()
            // 获取扫描数据(十六进制字节数组)
            final byte[] data = characteristic.getValue();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < data.length; i++) {
                sb.append(data[i]);
            }
            // 接收到数据后做应答操作,截取发送过来的条码信息前四个字节作为应答
            bluetoothUtil.setWriteCharacteristicNotification(BluetoothUtil.getResponseBufferMsg(data), writeBtCharacteristic);
            buffer[0] = 0;
            bytes = data.length;
            Message msg = Message.obtain();
            msg.what = BluetoothConstant.MSG_READ_BUFF;
            msg.arg1 = bytes;
            msg.obj = data;
            handler.sendMessage(msg);
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (bluetoothAdapter.isEnabled()) {
                if (Build.VERSION.SDK_INT >= 21) {

                    scanner.stopScan(scanCallback);
                    bluetoothAdapter.enable();
                    scanner.startScan(scanCallback);
                } else {
                    bluetoothAdapter.stopLeScan(leScanCallback);
                    bluetoothAdapter.startLeScan(leScanCallback);
                }
            }
            handler.postDelayed(this, SCAN_PERIOD);
        }
    };

    private BluetoothStateEvent bluetoothStateEvent = new BluetoothStateEvent();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothConstant.MSG_TYPE_FIND_DEVICE:
                    scan_code_device = (BluetoothDevice) msg.obj;
                    Toast.makeText(BluetoothService.this, "正在连接", Toast.LENGTH_SHORT).show();
                    Common.getInstance().setBluetoothState(false);
                    bluetoothStateEvent.setChangeState("gone");
                    bluetoothStateEvent.setImageCode("unConnect");
                    bluetoothStateEvent.setConnectState("connecting");
                    EventBus.getDefault().post(bluetoothStateEvent);

                    device_address = scan_code_device.getAddress();
                    isConnect = bluetoothUtil.connectDevice(device_address);
                    break;
                case BluetoothConstant.MSG_CONNECT_OK:
                    DataHolder.put("bluetoothFlag", BluetoothConstant.BLUETOOTH_CONNECT_SUCCESS_FLAG);
                    DataHolder.put("isBluetoothFail", BluetoothConstant.BLUETOOTH_CONNECT_SUCCESS);
                    Common.getInstance().setBluetoothState(true);
                    Toast.makeText(BluetoothService.this,"connect success", Toast.LENGTH_SHORT).show();

                    bluetoothStateEvent.setConnectState("connected");
                    bluetoothStateEvent.setChangeState("visible");
                    bluetoothStateEvent.setImageCode("lanyaconnectok");

                    EventBus.getDefault().post(bluetoothStateEvent);

                    break;
                case BluetoothConstant.MSG_CONNECT_FAIL:
                    DataHolder.put("isBluetoothFail", BluetoothConstant.BLUETOOTH_CONNECT_ERROR);
                    DataHolder.put("bluetoothFlag", BluetoothConstant.BLUETOOTH_CONNECT_ERROR_FLAG);
                    Common.getInstance().setBluetoothState(false);
                    isConnect = false;

                    bluetoothStateEvent.setConnectState("default");
                    bluetoothStateEvent.setChangeState("gone");
                    bluetoothStateEvent.setImageCode("unConnect");
                    EventBus.getDefault().post(bluetoothStateEvent);

                    scanDevice(true);
                    break;
                case BluetoothConstant.MSG_CHANGE_DEVICE:
                    DataHolder.put("isAutoConnect", BluetoothConstant.BLUETOOTH_IS_AUTO_CONNECT);
                    DataHolder.put("bluetoothFlag", "0");

                    Common.getInstance().setBluetoothState(false);
                    bluetoothStateEvent.setConnectState("default");
                    bluetoothStateEvent.setChangeState("gone");
                    bluetoothStateEvent.setImageCode("unConnect");
                    EventBus.getDefault().post(bluetoothStateEvent);

                    bluetoothUtil.setWriteCharacteristicNotification("0102", writeBtCharacteristic);
                    isConnect = false;
                    Toast.makeText(BluetoothService.this, "更换扫码设备", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothConstant.MSG_DISCONNECT_DEVICE:
                    DataHolder.put("isAutoConnect", "0");
                    DataHolder.put("bluetoothFlag", "0");

                    Common.getInstance().setBluetoothState(false);
                    bluetoothStateEvent.setConnectState("default");
                    bluetoothStateEvent.setChangeState("gone");
                    bluetoothStateEvent.setImageCode("unConnect");
                    EventBus.getDefault().post(bluetoothStateEvent);

                    bluetoothUtil.setWriteCharacteristicNotification("0102", writeBtCharacteristic);
                    isConnect = false;

                    break;
                case BluetoothConstant.MSG_READ_BUFF:
                    boolean isBroadcast = false;
                    scanDevice(true);
                    String data = BluetoothUtil.readBufferMsg((byte[]) msg.obj);
                    byte[] array = (byte[]) msg.obj;
                    if (array[2] == 2) {
                        // 有连包的时候,且为第一个包的时候,第一个包的数据需要全部解析
                        if (array[3] == 1) {
                            broadData = data;
                        }
                        if (array[3] == 2) {
                            // 有连包的情况下第二次解析完后广播
                            broadData += data;
                            data = broadData;
                            isBroadcast = true;
                            broadData = "";
                        }
                    } else {
                        // 无连包的时候,一次解析就可以广播
                        isBroadcast = true;
                    }
                    if (isBroadcast) {
                        Intent intent = new Intent(BluetoothConstant.BLUETOOTH_ACTION);
                        intent.putExtra("data", data);
                        if (!BluetoothUtil.isBackground(BluetoothService.this)) {
                            EventBus.getDefault().post(new MSGServiceEvent(data));
                            sendBroadcast(intent);
                        }
                    }
                    break;
            }
        }
    };

    /**
     * 搜索蓝牙  API 21以下版本
     */
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            StringBuffer sb = new StringBuffer();
            // 当字符数组中包含连续的0时如(100234),自动把0补齐
            for (int i = 0; i < userID.length() / 2; i++) {
                // 个位数前面补0
                if (scanRecord[22 + i] >= 0 && scanRecord[22 + i] < 16) {
                    sb.append(0);
                    sb.append(Integer.toHexString(scanRecord[22 + i] & 0xFF));
                } else {
                    sb.append(Integer.toHexString(scanRecord[22 + i] & 0xFF));
                }

            }

            if (userID.equals(sb.toString()) || (device.getName() != null && device.getName().startsWith("BARCODE") && Math.abs(rssi) > 50)) {
                // 当成功定义广播包后,提醒用户
                // 如果当前搜索到的设备广播包中带有界面上的二维码信息
                scanDevice(false);
                handler.removeCallbacks(runnable);
                Message msg = Message.obtain();
                msg.what = BluetoothConstant.MSG_TYPE_FIND_DEVICE;
                msg.obj = device;
                handler.sendMessage(msg);
            }

        }
    };

    /**
     * 搜索蓝牙  API 21(包含)以上版本
     */
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            StringBuffer sb = new StringBuffer();
            ScanRecord scanRecord = result.getScanRecord();
            SparseArray<byte[]> manufacturerSpecificData =
                    scanRecord.getManufacturerSpecificData();
            for (int i = 0; i < manufacturerSpecificData.size(); i++) {
                int keyAt = manufacturerSpecificData.keyAt(i);
                byte[] bytes = manufacturerSpecificData.get(keyAt);
                for (int j = 0; j < bytes.length; j++) {
                    if (bytes[j] >= 0 && bytes[j] < 16) {
                        sb.append(0);
                        sb.append(Integer.toHexString(bytes[j] & 0xFF));
                    } else {
                        sb.append(Integer.toHexString(bytes[j] & 0xFF));
                    }
                }
            }
            if (userID.equals(sb.toString()) ) {
                // 当成功定义广播包后,提醒用户
                // 如果当前搜索到的设备广播包中带有界面上的二维码信息
                scanDevice(false);
                handler.removeCallbacks(runnable);
                Message msg = Message.obtain();
                msg.what = BluetoothConstant.MSG_TYPE_FIND_DEVICE;
                msg.obj = result.getDevice();
                handler.sendMessage(msg);

            }

        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        scanDevice(false);
        handler.removeCallbacks(runnable);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        review();
        return START_STICKY;
    }
}
