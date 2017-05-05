package yfwang.androiddemo.global;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import yfwang.androiddemo.utils.SharedpreferencesUtil;


/**
 * Description: 全局Application
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/4/8 10:59
 */
public class MobileApplication extends Application {

    private static MobileApplication mobileApplication;

    public static MobileApplication getInstance() {
        return mobileApplication;
    }

    private ArrayList<Activity> mList;

    @Override
    public void onCreate() {
        super.onCreate();
        mobileApplication = this;
        mList = new ArrayList<>();
        initImageLoader();
    }

    /**
     * 添加一个activity到列表中<br/>
     * add Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    /**
     * 从列表中删除一个activity<br/>
     * remove Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        try {
            mList.remove(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否已经运行该activity<br/>
     *
     * @param activity
     * @return
     */
    public boolean containActivity(Class activity) {
        for (Activity act : mList) {
            if (act.getClass() == activity) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取已经运行的Activity<br/>
     *
     * @param activity
     * @return
     */
    public Activity getActivity(Class activity) {
        for (Activity act : mList) {
            if (act.getClass() == activity) {
                return act;
            }
        }
        return null;
    }

    /**
     * 关闭list内的每一个activity<br/>
     * close all activity
     */
    public void closeAllActivity() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
            mList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void reLogin() {

        SharedpreferencesUtil.clear(MobileApplication.getInstance());
        closeAllActivity();
        Intent intent = MobileApplication.getInstance().getPackageManager()
                .getLaunchIntentForPackage(MobileApplication.getInstance().getPackageName());
        MobileApplication.getInstance().startActivity(intent);

    }

    private void initImageLoader() {
        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions displayimageOptions = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();

        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).
                defaultDisplayImageOptions(displayimageOptions).build();
        ImageLoader.getInstance().init(config);
    }

}
