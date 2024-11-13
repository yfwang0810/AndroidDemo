package yfwang.androiddemo.DesignPattern.Builder;

/**
 * Description: Director类，负责构造Computer类
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/8/18 13:32
 */
public class Director {
    Builder mBuilder = null;

    /**
     *
     * @param builder
     */
    public Director(Builder builder){
        mBuilder = builder;
    }

    /**
     * 构建对象
     */
    public  void construct(String board , String display){
        mBuilder.buildBoard(board);
        mBuilder.buildDisplay(display);
        mBuilder.buildOS();

    }



}
