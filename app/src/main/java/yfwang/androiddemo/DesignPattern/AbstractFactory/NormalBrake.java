package yfwang.androiddemo.DesignPattern.AbstractFactory;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/22 13:46
 */
public class NormalBrake implements IBrake {
    @Override
    public void brake() {
        System.out.println("普通制动");
    }
}
