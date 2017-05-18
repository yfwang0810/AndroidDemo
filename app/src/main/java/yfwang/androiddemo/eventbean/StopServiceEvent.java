package yfwang.androiddemo.eventbean;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Author     : yfwang
 * Date       : 2016/10/27 19:07
 */

public class StopServiceEvent {

    private boolean isStop;

    public StopServiceEvent(boolean isStop) {
        this.isStop = isStop;
    }


    public boolean getMsg() {
        return isStop;
    }

    public void setMsg(String msg) {
        this.isStop = isStop;
    }
}
