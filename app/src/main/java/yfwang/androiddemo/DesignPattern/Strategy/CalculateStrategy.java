package yfwang.androiddemo.DesignPattern.Strategy;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/24 15:22
 */
public interface CalculateStrategy {
    /**
     * 按距离计算价格
     * @param km 距离
     * @return 价格
     * */
    int calculatePrice(int km);


}
