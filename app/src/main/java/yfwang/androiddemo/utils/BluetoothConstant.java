package yfwang.androiddemo.utils;

import java.util.UUID;
/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Author     : yfwang
 * Date       : 2016/10/27 19:07
 */
public class BluetoothConstant {


    public static final int MSG_CONNECT_OK = 1;

    public static final int MSG_TYPE_FIND_DEVICE = 2;

    public static final int MSG_CONNECT_FAIL = 3;

    public static final int MSG_CONNECTING = 4;

    public static final int MSG_READ_BUFF = 5;

    public static final int MSG_CHANGE_DEVICE = 6;

    public static final int MSG_DISCONNECT_DEVICE = 7;

    public static final UUID Service_UUID = UUID.fromString("6e40fff0-b5a3-f393-e0a9-e50e24dcca9e");// 服务ID

    public static final UUID Chara_UUID = UUID.fromString("6e40fff2-b5a3-f393-e0a9-e50e24dcca9e");// 通知特征

    public static final UUID WRITE_UUID = UUID.fromString("6e40fff1-b5a3-f393-e0a9-e50e24dcca9e");// 写特征

    public static final UUID DES_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");//通过特征描述

    public static final String BLUETOOTH_ACTION = "jason.broadcast.action";

    public static final String BLUETOOTH_CONNECT_SUCCESS = "0";

    public static final String BLUETOOTH_CONNECT_SUCCESS_FLAG = "1";

    public static final String BLUETOOTH_CONNECT_ERROR = "1";

    public static final String BLUETOOTH_CONNECT_ERROR_FLAG = "0";

    public static final String BLUETOOTH_IS_AUTO_CONNECT = "1";
}
