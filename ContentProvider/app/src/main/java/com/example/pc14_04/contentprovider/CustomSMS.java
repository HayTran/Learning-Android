package com.example.pc14_04.contentprovider;

/**
 * Created by PC14-04 on 5/29/2017.
 */

public class CustomSMS {
    String number;
    String content;

    public CustomSMS(String number, String content) {

        this.number = number;
        this.content = content;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CustomSMS{" +
                "number='" + number + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
