package yfwang.androiddemo.activity.DesignPattern.Builder;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/8/21 9:24
 */
public class Test {
    public static void main(String args[]){
        Builder builder = new MacbookBuilder();
        Director director = new Director(builder);
        director.construct("I7 7700","三星显示器");
        System.out.println("Computer Info : "+ builder.create().toString());
    }


}
