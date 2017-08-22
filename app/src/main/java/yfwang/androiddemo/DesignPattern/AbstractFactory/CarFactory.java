package yfwang.androiddemo.DesignPattern.AbstractFactory;

/**
 * Description: 抽象工厂模式
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/22 10:29
 */
public abstract class CarFactory {
    /**
     * 生产轮胎
     */
    public abstract Itire createTire();

    /**
     * 生产发动机
     */
    public abstract IEngine createEngine();
    /**
     * 生产制动系统
     */
    public abstract IBrake createBrake();

}

