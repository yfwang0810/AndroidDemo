package yfwang.androiddemo.activity.DesignPattern.Builder;

/**
 * Description: 具体computer类
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/8/18 10:04
 */
public class MacBook extends Computer {

    protected MacBook(){

    }

    @Override
    public void setOS() {
        mOS = "Mac OS X 10.10";
    }
}
