package yfwang.androiddemo.activity.DesignPattern.Singleton;

/**
 * Description: 懒汉模式
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/8/10 16:45
 */
public class Singleton {


    private static Singleton instance;

    private Singleton() {

    }

    public static synchronized Singleton getInstance(){
        if (instance==null){
            instance = new Singleton();
        }
        return instance;
    }



}
