package com.example.vanhay.sqlitestudy;

/**
 * Created by Van Hay on 22-May-17.
 */

public class Employee {
    int id;
    String name;
    int gender;

    public Employee(int id, String name, int gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
