package yfwang.androiddemo.utils;

public class Common {


    private static Common instance;


    public static Common getInstance() {
        synchronized (Common.class) {
            if (instance == null) {
                instance = new Common();

            }
        }

        return instance;
    }



    /**
     * 获取、保存蓝牙连接状态
     */
    private boolean isBluetoothConnected;

    public void setBluetoothState(boolean isConnected) {
        isBluetoothConnected = isConnected;
    }

    public boolean getBluetoothState() {
        return isBluetoothConnected;
    }


}