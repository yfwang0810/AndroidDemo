package yfwang.androiddemo.DesignPattern.Singleton;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/8/10 16:13
 */
public class SingletonTest {
    public static void main(String[] args) {
        Company cp = new Company();
        CEO ceo1 = CEO.getCEO();
        CEO ceo2 = CEO.getCEO();
        cp.addStaff(ceo1);
        cp.addStaff(ceo2);

        VP vp1 = new VP();
        VP vp2 = new VP();

        Staff staff1 = new Staff();
        Staff staff2 = new Staff();
        Staff staff3 = new Staff();

        cp.addStaff(vp1);
        cp.addStaff(vp2);

        cp.addStaff(staff1);
        cp.addStaff(staff2);
        cp.addStaff(staff3);

        cp.showAllStaffs();

    }


}
