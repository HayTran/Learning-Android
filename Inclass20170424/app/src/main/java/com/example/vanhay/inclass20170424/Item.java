package com.example.vanhay.inclass20170424;

/**
 * Created by Van Hay on 4/24/2017.
 */

public class Item {
    String name;
    int flag;

    public Item(String name, int flag) {
        this.name = name;
        this.flag = flag;
    }

    public Item() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
