package yfwang.androiddemo.DesignPattern.Factory;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/21 20:06
 */
public class AudiCarFactory extends AudiFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T extends AudiCar> T createAudiCar(Class<T> clz) {
        AudiCar car = null;
        try {
            car = (AudiCar) Class.forName(clz.getName()).newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) car;
    }
}
