package yfwang.androiddemo.bean;

import android.app.Activity;

/**
 * Description: demo Bean
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/5/8 14:21
 */
public class DemoInfo {
    private  String title;
    private  String desc;
    private  Class<? extends Activity> demoClass;

    public DemoInfo(String title, String desc,
                    Class<? extends Activity> demoClass) {
        this.title = title;
        this.desc = desc;
        this.demoClass = demoClass;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Class<? extends Activity> getDemoClass() {
        return demoClass;
    }

    public void setDemoClass(Class<? extends Activity> demoClass) {
        this.demoClass = demoClass;
    }


}
