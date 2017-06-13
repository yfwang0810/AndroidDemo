package yfwang.androiddemo.mvp.model;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/6 16:00
 */
public interface IUser {
    String getName();

    String getPasswd();

    int checkUserValidity(String name, String passwd);
}
