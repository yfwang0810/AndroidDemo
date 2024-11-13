package yfwang.androiddemo.activity.Dagger2Demo.presenter;

import javax.inject.Inject;

import yfwang.androiddemo.activity.Dagger2Demo.view.MainContract;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/7/25 14:01
 */
public class MainPresenter {
    private MainContract.View mView;
    @Inject
    MainPresenter(MainContract.View view){
        mView = view;
    }

   public void loadData(){
       mView.updateUI();

   }


}
