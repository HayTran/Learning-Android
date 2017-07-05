package com.a20170208.tranvanhay.appat.DashBoardFragment;

/**
 * Created by Van Hay on 02-Jun-17.
 */

public class CustomMenu {
    int id;
    String name;
    int image;

    public CustomMenu(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", image=" + image +
                '}';
    }
}
