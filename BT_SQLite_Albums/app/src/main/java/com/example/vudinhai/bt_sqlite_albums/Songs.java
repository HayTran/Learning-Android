package com.example.vudinhai.bt_sqlite_albums;

/**
 * Created by vudinhai on 5/26/17.
 */

public class Songs {
    int id;
    String name;
    int idAlbums;

    public Songs() {
    }

    public Songs(int id, String name, int idAlbums) {
        this.id = id;
        this.name = name;
        this.idAlbums = idAlbums;
    }

    public Songs(int id, String name) {
        this.id = id;
        this.name = name;
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

    public int getIdAlbums() {
        return idAlbums;
    }

    public void setIdAlbums(int idAlbums) {
        this.idAlbums = idAlbums;
    }

    @Override
    public String toString() {
        return name;
    }
}
