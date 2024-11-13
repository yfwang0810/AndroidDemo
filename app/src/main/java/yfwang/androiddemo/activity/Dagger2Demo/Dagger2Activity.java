package yfwang.androiddemo.activity.Dagger2Demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import javax.inject.Inject;

import yfwang.androiddemo.R;
import yfwang.androiddemo.activity.Dagger2Demo.module.MainModule;
import yfwang.androiddemo.activity.Dagger2Demo.presenter.MainPresenter;
import yfwang.androiddemo.activity.Dagger2Demo.view.DaggerMainComponent;
import yfwang.androiddemo.activity.Dagger2Demo.view.MainContract;

/**
 * Description: dagger2
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/6/6 11:15
 * <p>
 * 此处demo摘自http://www.jianshu.com/p/39d1df6c877d
 */
public class Dagger2Activity extends Activity implements MainContract.View {
    @Inject
    MainPresenter mMainPresenter;
    TextView mTips;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
        mTips = (TextView) findViewById(R.id.tv_tips);

        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
        mMainPresenter.loadData();
    }

    @Override
    public void updateUI() {
        mTips.setText("更新成功!");
    }
}
