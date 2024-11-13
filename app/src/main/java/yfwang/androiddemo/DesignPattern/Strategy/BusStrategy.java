package yfwang.androiddemo.DesignPattern.Strategy;

/**
 * Description: 公交价格计算策略
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/24 15:30
 */
public class BusStrategy implements CalculateStrategy {

    /**
     * 十公里之内一元钱，超过十公里每加一元可以乘五公里
     */
    @Override
    public int calculatePrice(int km) {
        //超过十公里的距离
        int extraTotal = km - 10;
        //超过的距离是五公里的倍数
        int extraFactor = extraTotal / 5;
        //超过的距离对5公里取余
        int fraction = extraTotal % 5;

        //价格计算
        int price = 1 + extraFactor;


        return fraction > 0 ? ++price : price;
    }
}
