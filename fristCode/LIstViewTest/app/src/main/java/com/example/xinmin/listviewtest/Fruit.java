package com.example.xinmin.listviewtest;

/**
 * Created by xinmin on 20/01/2017.
 */

public class Fruit {
    private String name;
    private int imageId;

    public Fruit(String name, int id) {
        this.name = name;
        this.imageId = id;
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
