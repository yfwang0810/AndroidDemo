package yfwang.androiddemo.DesignPattern.Factory;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/21 19:51
 */
public abstract class AudiCar {
    /**
     * 汽车的抽象产品类
     *
     * 定义汽车的一个行为方法 车启动后开走
     */
    public abstract void drive();

    /**
     *  定义汽车的一个行为方法 自动导航
     */
    public abstract void selfNavigation();


}
