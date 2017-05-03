package com.a20170208.tranvanhay.respberry3;

import android.os.Handler;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

/**
 * Created by Tran Van Hay on 3/27/2017.
 */

public class TimeAnDate {
    private static final String TAG = TimeAnDate.class.getSimpleName();
    public static String currentTimeOffline = "";
    // Instance for a handler
    private Handler mHandler = new Handler();
    private Runnable fetchCurrentTime = new Runnable() {
        @Override
        public void run() {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss --- dd/MM/yyyy");
            format.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
            new Firebase().sendTimestampToFirebase(format.format(date));
            currentTimeOffline = format.format(date) + "";
            /**
             *  Check socket server whether stop or not
             */
            try {
                if (OperatingActivity.socketServerThread.get() == 0){
                    OperatingActivity.socketServerThread.execute();
                    Log.d(TAG,"****************************************************************");
                    Log.d(TAG,"RESET Socket Server");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            mHandler.postDelayed(fetchCurrentTime,1000);

        }
    };
    public void showCurrentTime(){
        mHandler.post(fetchCurrentTime);
    }
}
