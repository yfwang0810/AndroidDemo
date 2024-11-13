package yfwang.androiddemo.DesignPattern.Builder;

/**
 * Description: 具体的builder类
 * Copyright  : Copyright (c) 2016
 * Author     : yfwang
 * Date       : 2017/8/18 10:41
 */
public class MacbookBuilder extends Builder {
    private Computer mComputer = new MacBook();

    @Override
    public void buildBoard(String board) {
        mComputer.setBorad(board);
    }

    @Override
    public void buildDisplay(String display) {
        mComputer.setDisplay(display);
    }

    @Override
    public void buildOS() {
        mComputer.setOS();
    }

    @Override
    public Computer create() {
        return mComputer;
    }
}
