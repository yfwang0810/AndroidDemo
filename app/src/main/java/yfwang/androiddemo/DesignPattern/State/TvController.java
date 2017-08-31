package yfwang.androiddemo.DesignPattern.State;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/30 11:30
 */
public class TvController implements PowerController {
    TvState mTvState;

    public void setTvState(TvState mTvState) {
        this.mTvState = mTvState;
    }

    public void nextChannel() {
        mTvState.nextChannel();
    }

    public void prevChannel() {
        mTvState.prevChannel();
    }

    public void turnUp() {
        mTvState.turnUp();

    }

    public void turnDown() {
        mTvState.turnDown();

    }

    @Override
    public void powerOn() {
        System.out.println("开机了");
        setTvState(new PowerOnState());
    }

    @Override
    public void powerOff() {
        System.out.println("关机了");
        setTvState(new PowerOffState());
    }
}
