package yfwang.androiddemo.utils;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Description: 蓝牙扫描操作类
 * Copyright  : Copyright (c) 2015
 * Author     : yfwang
 * Date       : 2016/10/29 9:13
 */

public class BluetoothUtil {
    public BluetoothUtil(Context context) {
        this.context = context;
    }

    private OnConnectListener onConnectListener;
    private OnDisconnectListener onDisconnectListener;
    private OnConnectingListener onConnectingListener;
    private OnDescriptorWriteListener onDescriptorWriteListener;

    private OnServiceDiscoverListener onServiceDiscoverListener;

    private OnDataAvailableListener onDataAvailableListener;

    private Context context;

    private BluetoothManager bluetoothManager;

    private BluetoothAdapter bluetoothAdapter;

    private String bluetoothDeviceAddress;

    public static BluetoothGatt bluetoothGatt;


    public interface OnDescriptorWriteListener {
        void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status);
    }

    public interface OnConnectListener {
        void onConnect(BluetoothGatt gatt);
    }

    public interface OnDisconnectListener {
        void onDisconnect(BluetoothGatt gatt);
    }

    public interface OnConnectingListener {
        void onConnecting(BluetoothGatt gatt);
    }


    public interface OnServiceDiscoverListener {
        void onServiceDiscover(BluetoothGatt gatt, int status);
    }

    public interface OnDataAvailableListener {
        void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);

        void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);

        void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic);
    }


    public void setDescriptorWrite(OnDescriptorWriteListener onDescriptorWriteListener) {
        this.onDescriptorWriteListener = onDescriptorWriteListener;
    }

    public void setOnConnectListener(OnConnectListener onConnectListener) {
        this.onConnectListener = onConnectListener;
    }

    public void setOnConnectingListener(OnConnectingListener onConnectingListener) {
        this.onConnectingListener = onConnectingListener;
    }

    public void setOnDisconnectListener(OnDisconnectListener onDisconnectListener) {
        this.onDisconnectListener = onDisconnectListener;
    }

    public void setOnServiceDiscoverListener(OnServiceDiscoverListener onServiceDiscoverListener) {
        this.onServiceDiscoverListener = onServiceDiscoverListener;
    }

    public void setOnDataAvailableListener(OnDataAvailableListener onDataAvailableListener) {
        this.onDataAvailableListener = onDataAvailableListener;
    }


    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                if (onConnectListener != null) {
                    onConnectListener.onConnect(gatt);
                    Log.i("info", "bluetooth is connected");
                }
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                if (onDisconnectListener != null) {
                    onDisconnectListener.onDisconnect(gatt);
                    Log.i("info", "bluetooth is disconnected");
                }
            } else if (newState == BluetoothProfile.STATE_CONNECTING) {
                if (onConnectingListener != null) {
                    onConnectingListener.onConnecting(gatt);
                }
            }


        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS && onServiceDiscoverListener != null) {
                onServiceDiscoverListener.onServiceDiscover(gatt, status);
            }

        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            if (onDataAvailableListener != null) {
                onDataAvailableListener.onCharacteristicRead(gatt, characteristic, status);
            }

        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            if (onDataAvailableListener != null) {
                super.onCharacteristicWrite(gatt, characteristic, status);
                onDataAvailableListener.onCharacteristicWrite(gatt, characteristic, status);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            if (onDataAvailableListener != null) {
                onDataAvailableListener.onCharacteristicChanged(gatt, characteristic);
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            if (onDescriptorWriteListener != null) {
                onDescriptorWriteListener.onDescriptorWrite(gatt, descriptor, status);
            }
        }
    };

    /**
     * 设置设备特种通知
     *
     * @time 2016/6/17 0017 10:45
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enable) {
        if (bluetoothAdapter == null || bluetoothGatt == null) {
            return;
        }
        bluetoothGatt.setCharacteristicNotification(characteristic, enable);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(BluetoothConstant.DES_UUID);
        descriptor.setValue((BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE));
        bluetoothGatt.writeDescriptor(descriptor);
    }


    /**
     * 初始化蓝牙适配器
     *
     * @time 2016/6/17 0017 10:14
     */
    public boolean initialize() {
        if (bluetoothManager == null) {
            bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            if (bluetoothManager == null) {
                return false;
            }
        }
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            return false;
        }
        return true;
    }

    /**
     * 连接设备
     *
     * @time 2016/6/17 0017 10:29
     */
    public boolean connectDevice(String address) {
        if (bluetoothAdapter == null || address == null) {
            return false;
        }
        final BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            return false;
        }
        if (bluetoothGatt != null) {
            bluetoothGatt.close();
        }
        bluetoothGatt = device.connectGatt(context, false, gattCallback);
        bluetoothDeviceAddress = address;
        return true;
    }

    //截取返回数据的前四个字节作为应答
    public void setWriteCharacteristicNotification(String msg, BluetoothGattCharacteristic characteristic) {

        if (bluetoothAdapter == null || bluetoothGatt == null || characteristic == null) {
            return;
        }
        Log.e("info", "发送指令" + msg + "解析回来的值的长度:" + change(msg).length);
        characteristic.setValue(change(msg));
        bluetoothGatt.writeCharacteristic(characteristic);
    }

    public static byte[] change(String inputStr) {
        byte[] result = new byte[inputStr.length() / 2];
        for (int i = 0; i < inputStr.length() / 2; ++i)
            result[i] = (byte) (Integer.parseInt(inputStr.substring(i * 2, i * 2 + 2), 16) & 0xff);
        return result;
    }

    public static String getResponseBufferMsg(byte[] bytes) {
        StringBuffer msg = new StringBuffer();

        for (int i = 0; i < 4; i++) {
            if (bytes[i] == 13) {
                // 碰到回车,条形码解析完毕
                break;
            } else {
                // 当没有碰到回车的时候,一直累加
                if ((bytes[i] + "").length() == 1) {
                    msg.append("0" + bytes[i]);
                } else {
                    msg.append(bytes[i] + "");
                }
            }
        }
        return msg.toString();
    }

    //  获取条形码信息
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

    /**
     * 判断是否后台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

}
