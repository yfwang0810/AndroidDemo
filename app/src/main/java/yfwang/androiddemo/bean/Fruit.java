package yfwang.androiddemo.bean;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Author     : yafei.wang
 * Date       : 2017/3/8 15:28
 */

public class Fruit {
    private String name;
    private int imageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
