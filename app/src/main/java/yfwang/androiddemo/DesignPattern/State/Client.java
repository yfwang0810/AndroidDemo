package yfwang.androiddemo.DesignPattern.State;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/30 16:27
 */
public class Client {
    public static void main(String args[]){
        TvController tvController = new TvController();

        //设置开机状态
        tvController.powerOn();

        //下一频道
        tvController.nextChannel();
        //调高音量
        tvController.turnUp();

        //设置关机状态
        tvController.powerOff();

    }


}
