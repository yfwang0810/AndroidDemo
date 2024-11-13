package yfwang.androiddemo.eventbean;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2018/12/28 11:52
 */
public class BroadcastEvent {
    private byte[] bytes;

    public BroadcastEvent(byte[] bytes) {
        this.bytes = bytes;
    }
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
