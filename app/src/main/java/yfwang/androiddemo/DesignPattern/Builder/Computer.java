package yfwang.androiddemo.DesignPattern.Builder;

/**
 * Description: 计算机抽象类
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/8/18 9:52
 */
public abstract class Computer {
    protected String mBorad;
    protected String mDisplay;
    protected String mOS;

    protected Computer() {

    }

    //设置核心数
    public void setBorad(String borad) {
        mBorad = borad;
    }

    //设置操作系统
    public abstract void setOS();


    //设置显示

    public void setDisplay(String display){
        mDisplay = display;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "mBorad='" + mBorad + '\'' +
                ", mDisplay='" + mDisplay + '\'' +
                ", mOS='" + mOS + '\'' +
                '}';
    }
}
