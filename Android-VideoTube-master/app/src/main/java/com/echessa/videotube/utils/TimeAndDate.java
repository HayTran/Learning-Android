package com.echessa.videotube.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hay Tran on 20-Aug-17.
 */

public class TimeAndDate {
    public static String convert(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS--dd@MM@yyyy");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
        return format.format(date) + "";
    }
}
