package yfwang.androiddemo.DesignPattern.observable;

import java.util.Observable;
import java.util.Observer;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/9/1 14:22
 */
public class Coder implements Observer {
    public String name;

    public Coder(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(name + ",  老子上天了!");
    }


}
