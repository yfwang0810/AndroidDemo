package yfwang.androiddemo.activity.Dagger2Demo.module;

import dagger.Module;
import dagger.Provides;
import yfwang.androiddemo.activity.Dagger2Demo.view.MainContract;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/7/25 14:26
 */
@Module
public class MainModule {

    private final MainContract.View mView;

    public MainModule(MainContract.View mView) {
        this.mView = mView;
    }

    @Provides
    MainContract.View provideMainView() {
        return mView;
    }
}
