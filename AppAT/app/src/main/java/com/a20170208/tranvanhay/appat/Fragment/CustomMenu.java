package com.a20170208.tranvanhay.appat.Fragment;

/**
 * Created by Van Hay on 02-Jun-17.
 */

public class CustomMenu {
    String name;
    int image;

    public CustomMenu(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CustomMenu{" +
                "name='" + name + '\'' +
                ", image=" + image +
                '}';
    }
}
