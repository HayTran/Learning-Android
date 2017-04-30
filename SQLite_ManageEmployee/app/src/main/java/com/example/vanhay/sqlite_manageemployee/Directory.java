package com.example.vanhay.sqlite_manageemployee;

/**
 * Created by Van Hay on 4/30/2017.
 */

public class Directory {
    int ID;
    String name;
    int phoneNumber;
    String note;

    public Directory(int ID, String name, int phoneNumber, String note) {
        this.ID = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.note = note;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
