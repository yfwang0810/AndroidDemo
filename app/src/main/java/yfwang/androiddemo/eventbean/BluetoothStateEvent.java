package yfwang.androiddemo.eventbean;
/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Author     : yfwang
 * Date       : 2016/10/27 19:07
 */
public class BluetoothStateEvent {

    private String connectState;
    private String imageCode;
    private String changeState;
    private boolean changeDevice;


    public boolean getChangeDevice() {
        return changeDevice;
    }

    public void setChangeDevice(boolean changeDevice) {
        this.changeDevice = changeDevice;
    }


    public String getConnectState() {
        return connectState;
    }

    public void setConnectState(String connectState) {
        this.connectState = connectState;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getChangeState() {
        return changeState;
    }

    public void setChangeState(String changeState) {
        this.changeState = changeState;
    }
}
