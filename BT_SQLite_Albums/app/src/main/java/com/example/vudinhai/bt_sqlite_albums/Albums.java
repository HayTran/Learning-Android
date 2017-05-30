package com.example.vudinhai.bt_sqlite_albums;

import java.io.Serializable;

/**
 * Created by vudinhai on 5/26/17.
 */

public class Albums implements Serializable{
    int id;
    String name;
    int numSong;
    byte[] image;



    public Albums() {
    }

    public Albums(int id, String name,  byte[] image, int numSong) {
        this.id = id;
        this.name = name;
        this.numSong = numSong;
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

    public int getNumSong() {
        return numSong;
    }

    public void setNumSong(int numSong) {
        this.numSong = numSong;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}
