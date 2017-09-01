package yfwang.androiddemo.DesignPattern.observable;

import java.util.Observable;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : yfwang
 * Date       : 2017/9/1 15:01
 */
public class DevTechFrontier extends Observable {
     public void postNewPublication(String content){

         //标示状态或者内容发生变化
         setChanged();
         //通知所有观察者
         notifyObservers(content);
     }



}
