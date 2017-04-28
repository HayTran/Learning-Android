package com.example.vanhay.inclass20170428;

import java.io.Serializable;

/**
 * Created by Van Hay on 4/28/2017.
 */

public class FlagItem implements Serializable {
    String name;
    int resource;

    public FlagItem(String name, int resource) {
        this.name = name;
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }
}
