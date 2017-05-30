package com.example.pc14_04.contentprovider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by PC14-04 on 5/29/2017.
 */

public class CustomHistory {
    String phoneNumber;
    String type;
    String time;

    public CustomHistory(String phoneNumber, String type, String time) {
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.time = time;
        this.convertTime();
    }
    private void convertTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss ---- dd/MM/yyyy");
        Date resultdate = new Date(Long.valueOf(this.time));
        this.time = simpleDateFormat.format(resultdate);
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CustomHistory{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
