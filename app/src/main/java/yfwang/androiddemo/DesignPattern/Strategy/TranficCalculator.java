package yfwang.androiddemo.DesignPattern.Strategy;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/24 16:14
 */
public class TranficCalculator {
    private CalculateStrategy mStrategy;

    public static void main(String[] args) {
        TranficCalculator calculator = new TranficCalculator();

        calculator.setStratrgy(new BusStrategy());
        System.out.println("公交车乘16公里的价格:" + calculator.calculatePrice(16));

    }

    private void setStratrgy(CalculateStrategy mStrategy) {
        this.mStrategy = mStrategy;
    }


    private int calculatePrice(int km) {

        return mStrategy.calculatePrice(km);
    }

}
