package yfwang.androiddemo.DesignPattern.AbstractFactory;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/8/22 13:35
 */
public class ImportEngine implements IEngine {
    @Override
    public void engine() {
        System.out.println("进口发动机");
    }
}
