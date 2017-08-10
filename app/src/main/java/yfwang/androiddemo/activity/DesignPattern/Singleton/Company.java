package yfwang.androiddemo.activity.DesignPattern.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/8/10 15:50
 */
//公司类
public class Company {
    private List<Staff> allStaffs = new ArrayList<>();

    public void addStaff(Staff per) {
        allStaffs.add(per);
    }

    public void showAllStaffs() {
        for (Staff per : allStaffs) {
            System.out.println("Obj : " + per.toString());
        }
    }

}
