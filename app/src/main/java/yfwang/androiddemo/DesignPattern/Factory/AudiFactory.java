package yfwang.androiddemo.DesignPattern.Factory;

/**
 * Description: 工厂模式
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/21 19:48
 */
public abstract class AudiFactory {
    public abstract <T extends AudiCar> T createAudiCar(Class<T> clz);


}
