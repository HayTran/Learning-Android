package com.example.haytran.inclass20170417;

/**
 * Created by Hay Tran on 17/04/2017.
 */

public class FlagItems {
    String name;
    int flag;

    public FlagItems() {
    }

    public FlagItems(String name, int flag) {
        this.name = name;
        this.flag = flag;
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
