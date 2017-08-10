package yfwang.androiddemo.activity.DesignPattern.Singleton;

/**
 * Description: 饿汉模式
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/8/10 14:39
 */

//CEO 饿汉单例模式
public  class CEO extends Staff {

    private static final CEO mCeo = new CEO();

    //私有构造函数
    private CEO() {

    }
    //共有的静态函数，对外暴露获取单例对象的接口
    public static CEO getCEO() {
        return mCeo;
    }

    @Override
    public void work() {
        super.work();
        //管理VP
    }
}
