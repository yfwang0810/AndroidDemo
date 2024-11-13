package yfwang.androiddemo.DesignPattern.Factory;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/21 20:23
 */
public class AudiQ7 extends AudiCar {
    @Override
    public void drive() {
        System.out.println("Q7 启动了！");
    }

    @Override
    public void selfNavigation() {
        System.out.println("Q7 开始自动导航了！");
    }
}
