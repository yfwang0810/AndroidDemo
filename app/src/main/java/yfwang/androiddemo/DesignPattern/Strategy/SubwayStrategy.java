package yfwang.androiddemo.DesignPattern.Strategy;

/**
 * Description: 地铁价格计算策略
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/24 15:51
 */
public class SubwayStrategy implements CalculateStrategy {
    /**
     * 6公里内三元 6~12公里4元；12~22公里5元;22~32公里6元
     *
     * @param km 距离
     * @return
     */

    @Override
    public int calculatePrice(int km) {
        if (km <= 6) {
            return 3;
        } else if (km > 6 && km <= 12) {
            return 4;
        } else if (km > 12 && km <= 22) {
            return 5;
        } else if (km > 22 && km <= 32) {
            return 6;
        }
        //其他为7
        return 7;
    }
}
