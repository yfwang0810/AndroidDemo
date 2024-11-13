package yfwang.androiddemo.DesignPattern.AbstractFactory;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/22 13:31
 */
public class NormalTire implements Itire {
    @Override
    public void tire() {
        System.out.println("普通轮胎");
    }
}
