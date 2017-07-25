package yfwang.androiddemo.activity.Dagger2Demo.view;

import dagger.Component;
import yfwang.androiddemo.activity.Dagger2Demo.Dagger2Activity;
import yfwang.androiddemo.activity.Dagger2Demo.module.MainModule;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/7/25 14:30
 */
@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(Dagger2Activity activity);

}
