package yfwang.androiddemo.bean;

import android.app.Activity;

/**
 * Description: demo Bean
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/5/8 14:21
 */
public class DemoInfo {
    private  int title;
    private  int desc;
    private  Class<? extends Activity> demoClass;

    public DemoInfo(int title, int desc,
                    Class<? extends Activity> demoClass) {
        this.title = title;
        this.desc = desc;
        this.demoClass = demoClass;
    }


    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }

    public Class<? extends Activity> getDemoClass() {
        return demoClass;
    }

    public void setDemoClass(Class<? extends Activity> demoClass) {
        this.demoClass = demoClass;
    }


}
