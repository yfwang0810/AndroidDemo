package yfwang.androiddemo.DesignPattern.observable;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/9/1 15:14
 */
public class Test {

    public static void main(String[] args) {
        //被观察的角色
        DevTechFrontier devTechFontier = new DevTechFrontier();
        //观察者
        Coder mrsimple = new Coder("mr.simple");
        Coder coder1 = new Coder("coder-1");
        Coder coder2 = new Coder("coder-2");
        Coder coder3 = new Coder("coder-3");



        //将观察者注册到可观察的观察者列表中

        devTechFontier.addObserver(mrsimple);
        devTechFontier.addObserver(coder1);
        devTechFontier.addObserver(coder2);
        devTechFontier.addObserver(coder3);

        //发布消息
        devTechFontier.postNewPublication("汝甚吊,何不上天呼?");


    }
}
