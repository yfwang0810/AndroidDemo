package yfwang.androiddemo.mvp.view;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/6 15:35
 */
public interface ILoginView {
     void onClearText();
     void onLoginResult(Boolean result, int code);
     void onSetProgressBarVisibility(int visibility);
}
