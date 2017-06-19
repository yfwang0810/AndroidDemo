package yfwang.androiddemo.eventbean;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Author     : yfwang
 * Date       : 2016/10/27 19:07
 */

public class DeleteEvent {

    private String data;

    public DeleteEvent(String data) {
        this.data = data;
    }


    public String getMsg() {
        return data;
    }

    public void setMsg(String msg) {
        this.data = data;
    }
}
