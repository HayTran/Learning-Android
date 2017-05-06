package com.example.vanhay.firebasefunctiontest;

/**
 * Created by Van Hay on 04-May-17.
 */

public class Person {
    String name;
    int birth;
    String sex;

    public Person() {
    }

    public Person(String name, int birth, String sex) {
        this.name = name;
        this.birth = birth;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birth=" + birth +
                ", sex='" + sex + '\'' +
                '}';
    }
}
