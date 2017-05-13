package com.example.vanhay.googlemap_test;

/**
 * Created by Van Hay on 02-May-17.
 */

import android.os.Handler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Tran Van Hay on 3/27/2017.
 */

public class TimeAndDate {
    private static final String TAG = TimeAndDate.class.getSimpleName();
    public String contextTime;
    public TimeAndDate(String contextTime) {
        this.contextTime = contextTime;
    }

    public static String currentTimeOffline = "";
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    // Instance for a handler
    private Handler mHandler = new Handler();
    private Runnable fetchCurrentTime = new Runnable() {
        @Override
        public void run() {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss --- dd/MM/yyyy");
            format.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
            mData.child("At Current").child(contextTime).setValue(format.format(date));
            currentTimeOffline = format.format(date) + "";
            mHandler.postDelayed(fetchCurrentTime,1000);
        }
    };
    public void showCurrentTime(){
        mHandler.post(fetchCurrentTime);
    }
}

